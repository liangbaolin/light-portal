package net.lightapi.portal.menu.domain;


import com.networknt.eventuate.common.Event;
import com.networknt.eventuate.common.EventUtil;
import com.networknt.eventuate.common.ReflectiveMutableCommandProcessingAggregate;
import net.lightapi.portal.menu.command.CreateMenuCommand;
import net.lightapi.portal.menu.command.DeleteMenuCommand;
import net.lightapi.portal.menu.command.MenuCommand;
import net.lightapi.portal.menu.command.UpdateMenuCommand;
import net.lightapi.portal.menu.common.event.MenuCreatedEvent;
import net.lightapi.portal.menu.common.event.MenuDeletedEvent;
import net.lightapi.portal.menu.common.event.MenuUpdatedEvent;
import net.lightapi.portal.menu.common.model.Menu;

import java.util.Collections;
import java.util.List;


public class MenuAggregate extends ReflectiveMutableCommandProcessingAggregate<MenuAggregate, MenuCommand> {

    Menu menu;
    boolean deleted;

    public List<Event> process(CreateMenuCommand cmd) {
        if(this.deleted) {
            return Collections.emptyList();
        }
        return EventUtil.events(new MenuCreatedEvent(cmd.getMenu()));
    }

    public List<Event> process(UpdateMenuCommand cmd) {
        if(this.deleted) {
            return Collections.emptyList();
        }
        return EventUtil.events(new MenuUpdatedEvent(cmd.getMenu()));
    }

    public List<Event> process(DeleteMenuCommand cmd) {
        if(this.deleted) {
            return Collections.emptyList();
        }
        return EventUtil.events(new MenuDeletedEvent());
    }


    public void apply(MenuCreatedEvent event) {
        this.menu = event.getMenu();
    }

    public void apply(MenuUpdatedEvent event) {
        this.menu = event.getMenu();
    }

    public void apply(MenuDeletedEvent event) {
        this.deleted = true;
    }

    public Menu getMenu() {
        return menu;
    }

}


