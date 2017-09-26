package net.lightapi.portal.menu.command;

import net.lightapi.portal.menu.common.model.MenuItem;

public class CreateMenuItemCommand implements MenuItemCommand {
    MenuItem menuItem;

    public CreateMenuItemCommand(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

}
