package net.lightapi.portal.menu.domain;

import com.networknt.eventuate.common.Event;
import com.networknt.eventuate.common.EventUtil;
import com.networknt.eventuate.common.ReflectiveMutableCommandProcessingAggregate;
import net.lightapi.portal.menu.command.*;
import net.lightapi.portal.menu.common.event.*;

import java.util.Collections;
import java.util.List;

public class MenuItemAggregate extends ReflectiveMutableCommandProcessingAggregate<MenuItemAggregate, MenuItemCommand> {

    String itemId;
    String data;
    boolean deleted;

    public List<Event> process(CreateMenuItemCommand cmd) {
        if(this.deleted) {
            return Collections.emptyList();
        }
        return EventUtil.events(new MenuItemCreatedEvent(cmd.getMenuItem()));
    }

    public List<Event> process(UpdateMenuItemCommand cmd) {
        if(this.deleted) {
            return Collections.emptyList();
        }
        return EventUtil.events(new MenuItemUpdatedEvent(cmd.getMenuItemId(), cmd.getMenuItem()));
    }

    public List<Event> process(DeleteMenuItemCommand cmd) {
        if(this.deleted) {
            return Collections.emptyList();
        }
        return EventUtil.events(new MenuItemDeletedEvent());
    }


    public void apply(MenuItemCreatedEvent event) {
        this.data = event.getMenuItem();
    }

    public void apply(MenuItemUpdatedEvent event) {
        this.itemId = event.getItemId();
        this.data = event.getMenuItem();
    }

    public void apply(MenuItemDeletedEvent event) {
        this.deleted = true;
    }

    public String getMenuItem() {
        return data;
    }
}
