package net.lightapi.portal.menu.command;

public class UpdateMenuItemCommand implements MenuItemCommand {
    String menuItemId;
    String data;

    public UpdateMenuItemCommand(String menuItemId, String data) {
        this.menuItemId = menuItemId;
        this.data = data;
    }

    public String getMenuItem() {
        return data;
    }

    public String getMenuItemId() { return menuItemId; }

}
