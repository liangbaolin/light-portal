package com.networknt.portal.usermanagement.model.auth.command.user;


public class DeleteUserCommand implements UserCommand {

    private String userId;

    public DeleteUserCommand(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
