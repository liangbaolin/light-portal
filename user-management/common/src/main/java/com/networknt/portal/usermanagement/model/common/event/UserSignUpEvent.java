package com.networknt.portal.usermanagement.model.common.event;


import com.networknt.portal.usermanagement.model.common.domain.UserDto;


public class UserSignUpEvent implements UserEvent {

    private UserDto userDto;

    private UserSignUpEvent() {
    }

    public UserSignUpEvent(UserDto userDto) {
        this.userDto = userDto;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }


}