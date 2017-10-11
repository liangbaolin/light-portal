package net.lightapi.portal.menu.command;

public class UpdateMenuCommand implements MenuCommand {
    String id;
    String data;

    public UpdateMenuCommand(String id, String data) {
        this.id = id;
        this.data = data;
    }

    public String getMenu() {
        return data;
    }

    public String getId() { return id; }
}
