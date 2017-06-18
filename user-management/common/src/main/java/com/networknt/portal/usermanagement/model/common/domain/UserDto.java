package com.networknt.portal.usermanagement.model.common.domain;



import com.networknt.portal.usermanagement.model.common.domain.contact.ContactData;
import com.networknt.portal.usermanagement.model.common.model.Timezone;

import java.util.Locale;


public class UserDto {

  private Long id;
  private String screenName;

  private ContactData contactData = new ContactData();

  private Timezone timezone = Timezone.CANADA_EASTERN;
  private Locale locale = Locale.CANADA;

  private String password;

  public UserDto(Long id, String screenName) {
    this.id = id;
    this.screenName = screenName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getScreenName() {
    return screenName;
  }

  public void setScreenName(String screenName) {
    this.screenName = screenName;
  }

  public ContactData getContactData() {
    return contactData;
  }

  public void setContactData(ContactData contactData) {
    this.contactData = contactData;
  }

  public Timezone getTimezone() {
    return timezone;
  }

  public void setTimezone(Timezone timezone) {
    this.timezone = timezone;
  }

  public Locale getLocale() {
    return locale;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
