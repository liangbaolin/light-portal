package net.lightapi.portal.menu.common.event;

import java.util.Map;

public class MenuUpdatedEvent implements MenuEvent {
    String data;
    String id;
    public MenuUpdatedEvent() {
    }

    public MenuUpdatedEvent(String id, String data) {
        this.id = id;
        this.data = data;
    }

    public String getMenu() {
        return data;
    }

    public void setMenu(String data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }
}
