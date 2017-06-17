package com.networknt.portal.usermanagement.auth.command.user;


import com.networknt.eventuate.common.Event;
import com.networknt.eventuate.common.EventUtil;
import com.networknt.eventuate.common.ReflectiveMutableCommandProcessingAggregate;

import com.networknt.portal.usermanagement.common.domain.UserDto;
import com.networknt.portal.usermanagement.common.event.UserSignUpEvent;

import java.util.Collections;
import java.util.List;


public class UserAggregate extends ReflectiveMutableCommandProcessingAggregate<UserAggregate, UserCommand> {

    private UserDto user;

    private boolean deleted;

    public List<Event> process(CreateUserCommand cmd) {
        if (this.deleted) {
            return Collections.emptyList();
        }
        return EventUtil.events(new UserSignUpEvent(cmd.getUser()));
    }




    public void apply(UserSignUpEvent event) {
        this.user = event.getUserDetail();
    }



    public UserDto getUser() {
        return user;
    }

}


