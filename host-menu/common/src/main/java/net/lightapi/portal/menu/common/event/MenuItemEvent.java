package net.lightapi.portal.menu.common.event;

import com.networknt.eventuate.common.Event;
import com.networknt.eventuate.common.EventEntity;

@EventEntity(entity = "com.networknt.portal.menu.MenuItemAggregate")
public interface MenuItemEvent extends Event {
}
