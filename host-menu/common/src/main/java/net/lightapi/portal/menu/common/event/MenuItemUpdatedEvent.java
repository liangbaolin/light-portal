package net.lightapi.portal.menu.common.event;

import net.lightapi.portal.menu.common.model.MenuItem;

public class MenuItemUpdatedEvent implements MenuItemEvent {
    MenuItem menuItem;

    public MenuItemUpdatedEvent() {
    }

    public MenuItemUpdatedEvent(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }
}
