package com.networknt.portal.usermanagement.model.common.domain;


import com.networknt.portal.usermanagement.model.common.domain.contact.ContactData;
import com.networknt.portal.usermanagement.model.common.model.Timezone;

import java.util.Locale;


public class UserInfo {


  private String timezone = Timezone.CANADA_EASTERN.name();
  private String locale = Locale.CANADA.getDisplayName();


  private ContactData contactData = new ContactData();
  private String screenName;



  private String password;
  private String host;

  private boolean emailChange;
  private boolean passwordReset;
  private boolean screenNameChange;

  public UserInfo() {
  }


  public UserInfo(String email, String screenName) {
    this.contactData.setEmail(email);
    this.screenName = screenName;
  }



  public String getTimezone() {
    return timezone;
  }

  public void setTimezone(String timezone) {
    this.timezone = timezone;
  }

  public String getLocale() {
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  public ContactData getContactData() {
    return contactData;
  }

  public void setContactData(ContactData contactData) {
    this.contactData = contactData;
  }

  public String getScreenName() {
    return screenName;
  }

  public void setScreenName(String screenName) {
    this.screenName = screenName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public boolean isEmailChange() {
    return emailChange;
  }

  public void setEmailChange(boolean emailChange) {
    this.emailChange = emailChange;
  }

  public boolean isPasswordReset() {
    return passwordReset;
  }

  public void setPasswordReset(boolean passwordReset) {
    this.passwordReset = passwordReset;
  }

  public boolean isScreenNameChange() {
    return screenNameChange;
  }

  public void setScreenNameChange(boolean screenNameChange) {
    this.screenNameChange = screenNameChange;
  }
}
