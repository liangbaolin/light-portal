package com.networknt.portal.usermanagement.model.common.event;


public class UserActionEvent implements UserEvent {

    private String tokenId;

    private UserActionEvent() {
    }

    public UserActionEvent(String tokenId) {
        this.tokenId = tokenId;
    }


    public String getTokenId() {
        return tokenId;
    }
}