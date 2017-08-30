package com.networknt.portal.usermanagement.model.common.event;


import com.networknt.portal.usermanagement.model.common.domain.UserDto;


public class UserUpdateEvent implements UserEvent {

    private UserDto user;
    private long id;

    private UserUpdateEvent() {
    }

    public UserUpdateEvent(long id, UserDto user) {
        this.id = id;
        this.user = user;
    }

    public UserDto getUserDetail() {
        return user;
    }

    public void setUserDetail(UserDto todo) {
        this.user = user;
    }

    public long getId() {
        return id;
    }
}