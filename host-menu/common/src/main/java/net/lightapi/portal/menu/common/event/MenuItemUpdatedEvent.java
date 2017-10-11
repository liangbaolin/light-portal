package net.lightapi.portal.menu.common.event;

public class MenuItemUpdatedEvent implements MenuItemEvent {
    String data;
    String itemId;

    public MenuItemUpdatedEvent() {
    }

    public MenuItemUpdatedEvent(String itemId, String data) {
        this.data = data;
    }

    public String getMenuItem() {
        return data;
    }

    public void setMenuItem(String data) {
        this.data = data;
    }

    public String getItemId() {
        return itemId;
    }
}
