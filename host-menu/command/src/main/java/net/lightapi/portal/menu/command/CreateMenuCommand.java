package net.lightapi.portal.menu.command;

import net.lightapi.portal.menu.common.model.Menu;

public class CreateMenuCommand implements MenuCommand {

    Menu menu;

    public CreateMenuCommand(Menu menu) {
        this.menu = menu;
    }

    public Menu getMenu() {
        return menu;
    }
}
