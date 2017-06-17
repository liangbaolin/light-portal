package com.networknt.portal.usermanagement.common.event;

import com.networknt.eventuate.common.Event;
import com.networknt.eventuate.common.EventEntity;

/**
 * Created by gavin on 2017-06-12.
 */
@EventEntity(entity = "com.networknt.eventuate.todolist.domain.TodoAggregate")
public interface UserEvent<T> extends Event{

}



