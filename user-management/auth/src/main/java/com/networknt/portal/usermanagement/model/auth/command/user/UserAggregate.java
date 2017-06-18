package com.networknt.portal.usermanagement.model.auth.command.user;


import com.networknt.eventuate.common.Event;
import com.networknt.eventuate.common.EventUtil;
import com.networknt.eventuate.common.ReflectiveMutableCommandProcessingAggregate;

import com.networknt.portal.usermanagement.model.common.domain.UserDto;
import com.networknt.portal.usermanagement.model.common.event.UserActionEvent;
import com.networknt.portal.usermanagement.model.common.event.UserDeleteEvent;
import com.networknt.portal.usermanagement.model.common.event.UserSignUpEvent;
import com.networknt.portal.usermanagement.model.common.event.UserUpdateEvent;

import java.util.Collections;
import java.util.List;


public class UserAggregate extends ReflectiveMutableCommandProcessingAggregate<UserAggregate, UserCommand> {

    private UserDto user;

    private long deleteUserId;

    private String tokenId;

    private boolean deleted;

    public List<Event> process(CreateUserCommand cmd) {
        if (this.deleted) {
            return Collections.emptyList();
        }
        return EventUtil.events(new UserSignUpEvent(cmd.getUser()));
    }

    public List<Event> process(UpdateUserCommand cmd) {
        if (this.deleted) {
            return Collections.emptyList();
        }
        return EventUtil.events(new UserUpdateEvent(cmd.getUser()));
    }


    public List<Event> process(DeleteUserCommand cmd) {
        if (this.deleted) {
            return Collections.emptyList();
        }
        return EventUtil.events(new UserDeleteEvent(cmd.getUserId()));
    }


    public List<Event> process(UserActionCommand cmd) {
        if (this.deleted) {
            return Collections.emptyList();
        }
        return EventUtil.events(new UserActionEvent(cmd.getUser(), cmd.getTokenId()));
    }




    public void apply(UserSignUpEvent event) {
        this.user = event.getUserDetail();
    }

    public void apply(UserUpdateEvent event) {
        this.user = event.getUserDetail();
    }

    public void apply(UserDeleteEvent event) {
        this.deleted = true;
        this.deleteUserId = event.getUserDeletedId();
    }

    public void apply(UserActionEvent event) {
        this.user = event.getUserDetail();
        this.tokenId = event.getTokenId();
    }

    public UserDto getUser() {
        return user;
    }

}


