package com.networknt.portal.usermanagement.auth.command.user;


import com.networknt.portal.usermanagement.common.domain.UserDto;

public class CreateUserCommand implements UserCommand {

    private UserDto user;

    public CreateUserCommand(UserDto user) {
        this.user = user;
    }

    public UserDto getUser() {
        return user;
    }
}
