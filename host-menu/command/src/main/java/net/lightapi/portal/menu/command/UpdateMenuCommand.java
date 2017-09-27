package net.lightapi.portal.menu.command;

public class UpdateMenuCommand implements MenuCommand {
    String host;
    String data;

    public UpdateMenuCommand(String host, String data) {
        this.host = host;
        this.data = data;
    }

    public String getMenu() {
        return data;
    }

    public String getHost() { return host; }
}
