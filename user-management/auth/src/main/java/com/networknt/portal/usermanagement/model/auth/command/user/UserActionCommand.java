package com.networknt.portal.usermanagement.model.auth.command.user;



public class UserActionCommand implements UserCommand {


    private String tokenId;

    public UserActionCommand(String tokenId) {

        this.tokenId = tokenId;
    }


    public String getTokenId() {
        return this.tokenId;
    }
}
