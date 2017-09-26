package net.lightapi.portal.menu.common.event;

import net.lightapi.portal.menu.common.model.MenuItem;

public class MenuItemCreatedEvent implements MenuItemEvent {
    MenuItem menuItem;

    public MenuItemCreatedEvent() {
    }

    public MenuItemCreatedEvent(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }
}
