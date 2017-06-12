
package com.networknt.portal.usermanagement.common.model;


import com.networknt.portal.usermanagement.common.domain.contact.ContactData;

import java.util.Locale;


/**
 * Created by lcsontos on 5/10/17.
 */

public class UserInfo {

  private Long id;
  private String screenName;

  private ContactData contactData = new ContactData();

  private Timezone timezone = Timezone.AMERICA_LOS_ANGELES;
  private Locale locale = Locale.US;

  private String password;

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
