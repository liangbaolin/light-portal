package net.lightapi.portal.menu.command;

import net.lightapi.portal.menu.common.model.Menu;

public class UpdateMenuCommand implements MenuCommand {
    String id;
    Menu menu;

    public UpdateMenuCommand(String id, Menu menu) {
        this.id = id;
        this.menu = menu;
    }

    public Menu getMenu() {
        return menu;
    }

    public String getId() { return id; }
}
