package com.networknt.portal.usermanagement.model.common.event;


import com.networknt.portal.usermanagement.model.common.domain.UserDto;


public class UserSignUpEvent implements UserEvent {

    private UserDto user;

    private UserSignUpEvent() {
    }

    public UserSignUpEvent(UserDto user) {
        this.user = user;
    }

    public UserDto getUserDto() {
        return user;
    }

    public void setUserDto(UserDto todo) {
        this.user = user;
    }


}