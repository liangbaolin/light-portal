package net.lightapi.portal.menu.common.event;

public class MenuItemCreatedEvent implements MenuItemEvent {
    String data;

    public MenuItemCreatedEvent() {
    }

    public MenuItemCreatedEvent(String data) {
        this.data = data;
    }

    public String getMenuItem() {
        return data;
    }

    public void setMenuItem(String data) {
        this.data = data;
    }
}
