package com.networknt.portal.usermanagement.model.common.event;


public class UserEmailConfirmEvent implements UserEvent {

    private String email;
    private boolean confirmed = true;

    private UserEmailConfirmEvent() {
    }

    public UserEmailConfirmEvent(String email) {
        this.email = email;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String  email) {
        this.email = email;
    }




}