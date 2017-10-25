package net.lightapi.portal.config;

public class ArangoConfig {
    String host;
    int port;
    String user;
    String password;
    String menuDBName;
    String formDBName;

    public ArangoConfig() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMenuDBName() {
        return menuDBName;
    }

    public void setMenuDBName(String menuDBName) {
        this.menuDBName = menuDBName;
    }

    public String getFormDBName() {
        return formDBName;
    }

    public void setFormDBName(String formDBName) {
        this.formDBName = formDBName;
    }
}
