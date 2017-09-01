package com.networknt.portal.usermanagement.model.common.event;



public class UserDeleteEvent implements UserEvent {

    private String userId;

    private UserDeleteEvent() {
    }

    public UserDeleteEvent(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}