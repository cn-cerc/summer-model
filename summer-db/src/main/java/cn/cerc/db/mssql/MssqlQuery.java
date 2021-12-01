package cn.cerc.db.mssql;

import cn.cerc.core.DataSetGson;
import cn.cerc.core.SqlText;
import cn.cerc.core.Utils;
import cn.cerc.db.core.IHandle;
import cn.cerc.db.core.SqlQuery;

public class MssqlQuery extends SqlQuery implements IHandle {
    private static final long serialVersionUID = -3510548502879617750L;
    private MssqlServer server = null;

    public MssqlQuery() {
        this(null);
    }

    public MssqlQuery(IHandle handle) {
        super(handle);
        this.sql().setServerType(SqlText.SERVERTYPE_MSSQL);
    }

    @Override
    public MssqlServer server() {
        if (server == null)
            server = (MssqlServer) getSession().getProperty(MssqlServer.SessionId);
        return server;
    }

    public void setServer(MssqlServer server) {
        this.server = server;
    }

    @Override
    public String json() {
        return new DataSetGson<>(this).encode();
    }

    @Override
    public MssqlQuery setJson(String json) {
        this.clear();
        if (!Utils.isEmpty(json))
            new DataSetGson<>(this).decode(json);
        return this;
    }

}
