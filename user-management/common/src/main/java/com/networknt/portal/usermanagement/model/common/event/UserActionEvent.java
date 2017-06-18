package com.networknt.portal.usermanagement.model.common.event;


import com.networknt.portal.usermanagement.model.common.domain.UserDto;


public class UserActionEvent implements UserEvent {

    private UserDto user;
    private String tokenId;

    private UserActionEvent() {
    }

    public UserActionEvent(UserDto user, String tokenId) {
        this.user = user;
        this.tokenId = tokenId;
    }

    public UserDto getUserDetail() {
        return user;
    }

    public void setUserDetail(UserDto todo) {
        this.user = user;
    }

    public String getTokenId() {
        return tokenId;
    }
}