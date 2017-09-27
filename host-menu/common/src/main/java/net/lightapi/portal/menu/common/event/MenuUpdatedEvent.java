package net.lightapi.portal.menu.common.event;

import java.util.Map;

public class MenuUpdatedEvent implements MenuEvent {
    String data;

    public MenuUpdatedEvent() {
    }

    public MenuUpdatedEvent(String data) {
        this.data = data;
    }

    public String getMenu() {
        return data;
    }

    public void setMenu(String data) {
        this.data = data;
    }
}
