package com.networknt.portal.usermanagement.model.common.event;


import com.networknt.portal.usermanagement.model.common.domain.UserDto;


public class UserUpdateEvent implements UserEvent {

    private UserDto userDto;
    private String id;

    private UserUpdateEvent() {
    }

    public UserUpdateEvent(String id, UserDto userDto) {
        this.id = id;
        this.userDto = userDto;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public String getId() {
        return id;
    }
}