package net.lightapi.portal.menu.common.event;

public class MenuCreatedEvent implements MenuEvent {
    String data;

    public MenuCreatedEvent() {
    }

    public MenuCreatedEvent(String data) {
        this.data = data;
    }

    public String getMenu() {
        return data;
    }

    public void setMenu(String data) {
        this.data = data;
    }
}
