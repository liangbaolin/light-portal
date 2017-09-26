package net.lightapi.portal.menu.common.event;

import net.lightapi.portal.menu.common.model.Menu;

public class MenuCreatedEvent implements MenuEvent {
    Menu menu;

    public MenuCreatedEvent() {
    }

    public MenuCreatedEvent(Menu menu) {
        this.menu = menu;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
