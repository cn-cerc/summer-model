package cn.cerc.db.sqlite;

import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import cn.cerc.core.Datetime;
import cn.cerc.core.Describe;
import cn.cerc.core.Utils;
import cn.cerc.db.core.IHandle;
import cn.cerc.db.core.ISqlDatabase;

public class SqliteDatabase implements ISqlDatabase {
    public static final String DefaultOID = "id_";
    private Class<?> clazz;

    public SqliteDatabase(IHandle handle, Class<?> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public final String table() {
        return Utils.findTable(this.clazz);
    }

    @Override
    public final String oid() {
        return Utils.findOid(clazz, DefaultOID);
    }

    @Override
    public boolean createTable(boolean overwrite) {
        SqliteServer server = new SqliteServer();
        return server.createTable(getCreateSql(), overwrite);
    }

    public String getCreateSql() {
        StringBuffer sb = new StringBuffer();
        sb.append("create table ").append(table()).append("(");
        int count = 0;
        for (Field field : clazz.getDeclaredFields()) {
            if (count++ > 0)
                sb.append(",");
            sb.append("\n");
            sb.append(field.getName()).append(" ");
            writeDataType(sb, field);
        }
        sb.append("\n);");
        Table table = clazz.getDeclaredAnnotation(Table.class);
        if (table != null && table.indexes().length > 0) {
            sb.append("\n");
            Index[] indexs = table.indexes();
            count = 0;
            for (Index index : indexs) {
                sb.append("create");
                if (index.unique()) {
                    sb.append(" unique");
                }
                sb.append(" index ").append(index.name()).append(" on ").append(table()).append("(")
                        .append(index.columnList()).append(");\n");
            }
        }
        return sb.toString();
    }

    private void writeDataType(StringBuffer sb, Field field) {
        Column column = field.getDeclaredAnnotation(Column.class);
        if (field.getType() == String.class) {
            int size = 255;
            if (column != null)
                size = column.length();
            if (!Utils.isEmpty(column.columnDefinition()) && "text".equals(column.columnDefinition())) {
                sb.append("text");
            } else {
                sb.append("varchar(").append(size).append(")");
            }
        } else if (field.getType().isEnum() || field.getType() == boolean.class) {
            sb.append("int");
        } else if (Datetime.class.isAssignableFrom(field.getType())) {
            sb.append("datetime");
        } else if (field.getType() == int.class) {
            sb.append("integer");
        } else if (field.getType() == double.class) {
            int precision = 18;
            int scale = 4;
            if (column != null) {
                precision = column.precision();
                scale = column.scale();
            }
            sb.append("decimal(").append(precision).append(",").append(scale).append(")");
        } else {
            throw new RuntimeException("不支持的类型：" + field.getType().getName());
        }
        Id id = field.getDeclaredAnnotation(Id.class);
        if (id != null) {
            sb.append(" primary key");
        }
        GeneratedValue gen = field.getDeclaredAnnotation(GeneratedValue.class);
        if (gen != null) {
            sb.append(" autoincrement");
        }
        if ((column != null) && (!column.nullable()))
            sb.append(" not null");
        else if (id != null)
            sb.append(" not null");
        Describe des = field.getDeclaredAnnotation(Describe.class);
        if (des != null && !Utils.isEmpty(des.def())) {
            sb.append(" default ").append(des.def());
        }
    }

}
