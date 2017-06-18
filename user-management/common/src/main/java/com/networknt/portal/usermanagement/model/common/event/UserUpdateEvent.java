package com.networknt.portal.usermanagement.model.common.event;


import com.networknt.portal.usermanagement.model.common.domain.UserDto;


public class UserUpdateEvent implements UserEvent {

    private UserDto user;

    private UserUpdateEvent() {
    }

    public UserUpdateEvent(UserDto user) {
        this.user = user;
    }

    public UserDto getUserDetail() {
        return user;
    }

    public void setUserDetail(UserDto todo) {
        this.user = user;
    }


}