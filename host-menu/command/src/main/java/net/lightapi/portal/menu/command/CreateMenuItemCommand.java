package net.lightapi.portal.menu.command;

public class CreateMenuItemCommand implements MenuItemCommand {
    String data;

    public CreateMenuItemCommand(String data) {
        this.data = data;
    }

    public String getMenuItem() {
        return data;
    }

}
