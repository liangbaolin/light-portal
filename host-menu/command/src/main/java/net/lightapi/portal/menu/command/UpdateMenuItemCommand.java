package net.lightapi.portal.menu.command;

import net.lightapi.portal.menu.common.model.MenuItem;

public class UpdateMenuItemCommand implements MenuItemCommand {
    String id;
    MenuItem menuItem;

    public UpdateMenuItemCommand(String id, MenuItem menuItem) {
        this.id = id;
        this.menuItem = menuItem;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public String getId() { return id; }

}
