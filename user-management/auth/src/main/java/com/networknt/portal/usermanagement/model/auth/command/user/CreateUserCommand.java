package com.networknt.portal.usermanagement.model.auth.command.user;


import com.networknt.portal.usermanagement.model.common.domain.UserDto;

public class CreateUserCommand implements UserCommand {

    private UserDto user;

    public CreateUserCommand(UserDto user) {
        this.user = user;
    }

    public UserDto getUserDto() {
        return user;
    }
}
