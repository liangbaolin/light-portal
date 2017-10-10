package net.lightapi.portal.menu.command;

public class CreateMenuCommand implements MenuCommand {

    String data;

    public CreateMenuCommand(String data) {
        this.data = data;
    }

    public String getMenu() {
        return data;
    }
}
