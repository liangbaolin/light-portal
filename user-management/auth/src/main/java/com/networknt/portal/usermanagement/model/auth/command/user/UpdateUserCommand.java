package com.networknt.portal.usermanagement.model.auth.command.user;


import com.networknt.portal.usermanagement.model.common.domain.UserDto;

public class UpdateUserCommand implements UserCommand {

    private UserDto user;
    private String id;


    public UpdateUserCommand(String id, UserDto user) {
        this.id = id;
        this.user = user;
    }

    public UserDto getUser() {
        return user;
    }

    public String getId() {
        return id;
    }
}
