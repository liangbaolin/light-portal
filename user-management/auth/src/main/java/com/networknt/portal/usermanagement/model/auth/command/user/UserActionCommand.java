package com.networknt.portal.usermanagement.model.auth.command.user;



public class UserActionCommand implements UserCommand {


    private String tokenId;
    private String id;

    public UserActionCommand(String id, String tokenId) {
        this.id = id;
        this.tokenId = tokenId;
    }

    public String getId() {
        return id;
    }

    public String getTokenId() {
        return this.tokenId;
    }
}
