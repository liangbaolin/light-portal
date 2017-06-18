package com.networknt.portal.usermanagement.model.common.event;

import com.networknt.eventuate.common.Event;
import com.networknt.eventuate.common.EventEntity;

/**
 * Created by gavin on 2017-06-12.
 */
@EventEntity(entity = "com.networknt.portal.usermanagement.auth.command.user.UserAggregate")
public interface UserEvent<T> extends Event{

}



