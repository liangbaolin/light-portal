package com.networknt.portal.usermanagement.model.common.event;

public class UserSignUpSuccessEvent implements UserEvent {
  private String token;
  private String email;

  private UserSignUpSuccessEvent() {
  }

  public UserSignUpSuccessEvent(String token, String email) {
    this.token = token;
    this.email = email;
  }

  public String getToken() {
    return token;
  }

  public String getEmail() {
    return email;
  }
}
