package com.networknt.portal.usermanagement.model.common.event;



public class UserDeletedEvent implements UserEvent {

    private String userId;

    private UserDeletedEvent() {
    }

    public UserDeletedEvent(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}