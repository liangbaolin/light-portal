package com.networknt.portal.usermanagement.common.event;


import com.networknt.portal.usermanagement.common.domain.UserDto;


public class UserSignUpEvent implements UserEvent {

    private UserDto user;

    private UserSignUpEvent() {
    }

    public UserSignUpEvent(UserDto user) {
        this.user = user;
    }

    public UserDto getUserDetail() {
        return user;
    }

    public void setUserDetail(UserDto todo) {
        this.user = user;
    }


}