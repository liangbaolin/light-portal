package com.networknt.portal.usermanagement.model.auth.command.user;


import com.networknt.portal.usermanagement.model.common.domain.UserDto;

public class UpdateUserCommand implements UserCommand {

    private UserDto userDto;
    private String id;


    public UpdateUserCommand(String id, UserDto userDto) {
        this.id = id;
        this.userDto = userDto;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public String getId() {
        return id;
    }
}
