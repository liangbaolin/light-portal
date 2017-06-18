package com.networknt.portal.usermanagement.model.auth.command.user;


import com.networknt.portal.usermanagement.model.common.domain.UserDto;

public class UpdateUserCommand implements UserCommand {

    private UserDto user;

    public UpdateUserCommand(UserDto user) {
        this.user = user;
    }

    public UserDto getUser() {
        return user;
    }
}
