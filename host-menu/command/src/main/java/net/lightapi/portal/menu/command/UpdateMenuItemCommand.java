package net.lightapi.portal.menu.command;

import net.lightapi.portal.menu.common.model.MenuItem;

public class UpdateMenuItemCommand implements MenuItemCommand {
    String menuItemId;
    MenuItem menuItem;

    public UpdateMenuItemCommand(String menuItemId, MenuItem menuItem) {
        this.menuItemId = menuItemId;
        this.menuItem = menuItem;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public String getMenuItemId() { return menuItemId; }

}
