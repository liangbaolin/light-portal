package com.networknt.portal.usermanagement.model.auth.command.user;


import com.networknt.portal.usermanagement.model.common.domain.UserDto;

public class UserActionCommand implements UserCommand {

    private UserDto user;
    private String tokenId;

    public UserActionCommand(UserDto user, String tokenId) {
        this.user = user;
        this.tokenId = tokenId;
    }

    public UserDto getUser() {
        return user;
    }

    public String getTokenId() {
        return this.tokenId;
    }
}
