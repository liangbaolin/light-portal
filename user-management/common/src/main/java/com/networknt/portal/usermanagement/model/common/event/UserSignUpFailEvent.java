package com.networknt.portal.usermanagement.model.common.event;

public class UserSignUpFailEvent implements UserEvent {
  private String message;

  private UserSignUpFailEvent() {
  }

  public UserSignUpFailEvent(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
