package net.lightapi.portal.menu.command;

import net.lightapi.portal.menu.common.model.Menu;

public class UpdateMenuCommand implements MenuCommand {
    String host;
    Menu menu;

    public UpdateMenuCommand(String host, Menu menu) {
        this.host = host;
        this.menu = menu;
    }

    public Menu getMenu() {
        return menu;
    }

    public String getHost() { return host; }
}
