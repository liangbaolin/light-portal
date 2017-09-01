package com.networknt.portal.usermanagement.model.common.event;


public class UserActionEvent implements UserEvent {

    private String tokenId;
    private String id;

    private UserActionEvent() {
    }

    public UserActionEvent(String id, String tokenId) {
        this.id = id;

        this.tokenId = tokenId;
    }

    public String getId() {
        return id;
    }
    public String getTokenId() {
        return tokenId;
    }
}