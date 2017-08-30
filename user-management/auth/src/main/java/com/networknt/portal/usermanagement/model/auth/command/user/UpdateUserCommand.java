package com.networknt.portal.usermanagement.model.auth.command.user;


import com.networknt.portal.usermanagement.model.common.domain.UserDto;

public class UpdateUserCommand implements UserCommand {

    private UserDto user;
    private long id;


    public UpdateUserCommand(long id, UserDto user) {
        this.id = id;
        this.user = user;
    }

    public UserDto getUser() {
        return user;
    }

    public long getId() {
        return id;
    }
}
