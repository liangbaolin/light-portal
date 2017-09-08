package com.networknt.portal.usermanagement.model.auth.command.user;


import com.networknt.eventuate.common.Event;
import com.networknt.eventuate.common.EventUtil;
import com.networknt.eventuate.common.ReflectiveMutableCommandProcessingAggregate;

import com.networknt.portal.usermanagement.model.common.domain.UserDto;
import com.networknt.portal.usermanagement.model.common.event.UserConfirmedEvent;
import com.networknt.portal.usermanagement.model.common.event.UserDeletedEvent;
import com.networknt.portal.usermanagement.model.common.event.UserSignUpEvent;
import com.networknt.portal.usermanagement.model.common.event.UserUpdatedEvent;

import java.util.Collections;
import java.util.List;


public class UserAggregate extends ReflectiveMutableCommandProcessingAggregate<UserAggregate, UserCommand> {

    private UserDto user;

    private String userId;

    private String tokenId;

    private boolean deleted;

    public List<Event> process(CreateUserCommand cmd) {
        if (this.deleted) {
            return Collections.emptyList();
        }
        return EventUtil.events(new UserSignUpEvent(cmd.getUserDto()));
    }

    public List<Event> process(UpdateUserCommand cmd) {
        if (this.deleted) {
            return Collections.emptyList();
        }
        return EventUtil.events(new UserUpdatedEvent(cmd.getId(), cmd.getUserDto()));
    }


    public List<Event> process(DeleteUserCommand cmd) {
        if (this.deleted) {
            return Collections.emptyList();
        }
        return EventUtil.events(new UserDeletedEvent(cmd.getUserId()));
    }


    public List<Event> process(UserActionCommand cmd) {
        if (this.deleted) {
            return Collections.emptyList();
        }
        return EventUtil.events(new UserConfirmedEvent( cmd.getId(), cmd.getTokenId()));
    }




    public void apply(UserSignUpEvent event) {
        this.user = event.getUserDto();
    }

    public void apply(UserUpdatedEvent event) {
        this.userId = event.getId();
        this.user = event.getUserDto();
    }

    public void apply(UserDeletedEvent event) {
        this.deleted = true;
        this.userId = event.getUserId();
    }

    public void apply(UserConfirmedEvent event) {
        this.userId = event.getId();
        this.tokenId = event.getTokenId();
    }

    public UserDto getUser() {
        return user;
    }

}


