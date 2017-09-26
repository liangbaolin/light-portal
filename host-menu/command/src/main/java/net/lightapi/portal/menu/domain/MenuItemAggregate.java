package net.lightapi.portal.menu.domain;

import com.networknt.eventuate.common.ReflectiveMutableCommandProcessingAggregate;
import net.lightapi.portal.menu.command.MenuItemCommand;
import net.lightapi.portal.menu.common.model.MenuItem;

public class MenuItemAggregate extends ReflectiveMutableCommandProcessingAggregate<MenuItemAggregate, MenuItemCommand> {
    MenuItem menuItem;

}
