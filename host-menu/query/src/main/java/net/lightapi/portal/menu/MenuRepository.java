package net.lightapi.portal.menu;

import java.io.IOException;

public interface MenuRepository {

    void setDataSource(Object dataSource);

    Object getDataSource();

    String getMenu();

    String getMenuByHost(String host);

    String getMenuItem();

    void createMenu(String entityId, String data);

    void updateMenu(String entityId, String data);

    void removeMenu(String entityId);

    void createMenuItem(String entityId, String data);

    void updateMenuItem(String entityId, String data);

    void removeMenuItem(String entityId);
}
