package cn.cerc.core;

import java.util.Set;

public interface ISession extends AutoCloseable {
    public static final String TOKEN = "sid"; // session id
    public static final String EDITION = "edition";
    public static final String CORP_NO = "corp_no";
    public static final String USER_CODE = "user_code";
    public static final String USER_NAME = "user_name";
    public static final String LANGUAGE_ID = "language_id";
    public static final String CLIENT_DEVICE = "device"; // client device
    public static final String LOGIN_SERVER = "login_server";
    public static final String REQUEST = "request";

    // 自定义参数，注：若key=null则返回实现接口的对象本身
    Object getProperty(String key);

    // 设置自定义参数
    void setProperty(String key, Object value);

    // 从数据库根据token载入所有环境
    void loadToken(String token);

    default String getToken() {
        return (String) getProperty(TOKEN);
    }

    default String getEdition() {
        return (String) getProperty(EDITION);
    }

    default String getCorpNo() {
        return (String) getProperty(CORP_NO);
    }

    default String getUserCode() {
        return (String) getProperty(USER_CODE);
    }

    default String getUserName() {
        return (String) getProperty(USER_NAME);
    }

    default String getLanguageId() {
        return (String) getProperty(LANGUAGE_ID);
    }

    default String getClientDevice() {
        return (String) getProperty(CLIENT_DEVICE);
    }

    default String getLoginServer() {
        return (String) getProperty(LOGIN_SERVER);
    }

    // 返回当前是否为已登入状态
    boolean logon();

    // 关闭开启的资源
    @Override
    void close();

    /**
     * 警告：若授权码清单中包含了 Permission.ADMIN，则意味着拥有所有权限！
     * 
     * @return 返回当前用户已取得的授权码清单，若返回null则表示不判断
     */
    default Set<String> getPermissions() {
        return null;
    }
}
