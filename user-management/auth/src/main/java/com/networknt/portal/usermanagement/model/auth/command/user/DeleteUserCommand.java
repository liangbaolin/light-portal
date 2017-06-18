package com.networknt.portal.usermanagement.model.auth.command.user;


public class DeleteUserCommand implements UserCommand {

    private long userId;

    public DeleteUserCommand(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }
}
