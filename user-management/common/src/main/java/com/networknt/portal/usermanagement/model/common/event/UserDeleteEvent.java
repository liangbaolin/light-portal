package com.networknt.portal.usermanagement.model.common.event;



public class UserDeleteEvent implements UserEvent {

    private long userId;

    private UserDeleteEvent() {
    }

    public UserDeleteEvent(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


}