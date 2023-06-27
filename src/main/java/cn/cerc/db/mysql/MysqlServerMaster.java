package cn.cerc.db.mysql;

import java.sql.Connection;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MysqlServerMaster extends MysqlServer {
    // IHandle中识别码
    public static final String SessionId = "sqlSession";
    private static HikariDataSource dataSource;

    static {
        MysqlConfig config = MysqlConfig.getMaster();
        dataSource = config.createDataSource();
    }

    @Override
    public Connection createConnection() {
        return MysqlServer.getPoolConnection(dataSource);
    }

    @Override
    public final boolean isPool() {
        return true;
    }

    @Override
    public String getHost() {
        return MysqlConfig.getMaster().site();
    }

    @Override
    public String getDatabase() {
        return MysqlConfig.getMaster().database();
    }

    public static void openPool() {

    }

    public static void closePool() {
        if (dataSource != null) {
            dataSource.close();
            dataSource = null;
        }
    }

}
