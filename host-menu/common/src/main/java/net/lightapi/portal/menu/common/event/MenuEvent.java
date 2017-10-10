package net.lightapi.portal.menu.common.event;

import com.networknt.eventuate.common.Event;
import com.networknt.eventuate.common.EventEntity;

@EventEntity(entity = "net.lightapi.portal.menu.domain.MenuAggregate")
public interface MenuEvent extends Event {
}
