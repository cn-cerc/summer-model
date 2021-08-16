package cn.cerc.db.mssql;

import cn.cerc.core.SqlText;
import cn.cerc.db.core.IHandle;
import cn.cerc.db.core.SqlQuery;

public class MssqlQuery extends SqlQuery implements IHandle {
    private static final long serialVersionUID = -3510548502879617750L;
    transient private MssqlServer server = null;

    public MssqlQuery() {
        super();
    }

    public MssqlQuery(IHandle handle) {
        super(handle);
        this.getSqlText().setServerType(SqlText.SERVERTYPE_MSSQL);
    }

    @Override
    public MssqlServer getServer() {
        if (server == null)
            server = (MssqlServer) getSession().getProperty(MssqlServer.SessionId);
        return server;
    }

    public void setServer(MssqlServer server) {
        this.server = server;
    }
}
