package net.lightapi.portal.menu.common.event;

public class MenuItemUpdatedEvent implements MenuItemEvent {
    String data;

    public MenuItemUpdatedEvent() {
    }

    public MenuItemUpdatedEvent(String data) {
        this.data = data;
    }

    public String getMenuItem() {
        return data;
    }

    public void setMenuItem(String data) {
        this.data = data;
    }
}
