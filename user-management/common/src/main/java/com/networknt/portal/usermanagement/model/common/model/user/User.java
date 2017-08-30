
package com.networknt.portal.usermanagement.model.common.model.user;


import com.networknt.portal.usermanagement.model.common.domain.AuditData;
import com.networknt.portal.usermanagement.model.common.domain.Entity;
import com.networknt.portal.usermanagement.model.common.domain.contact.ContactData;
import com.networknt.portal.usermanagement.model.common.exception.InvalidTokenException;
import com.networknt.portal.usermanagement.model.common.model.Timezone;
import com.networknt.portal.usermanagement.model.common.utils.IdentityGenerator;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;


/**
 * Model class for users.
 */

public class User implements Entity<String, User> {

  private String id;
  private String screenName;
  private String host;
  private ContactData contactData = new ContactData();

  private Password password;

  private Set<String> authorities = new LinkedHashSet<>();

  private Timezone timezone = Timezone.CANADA_EASTERN;
  private Locale locale = Locale.CANADA;

  private boolean confirmed;
  private boolean locked;
  private boolean deleted;

  private AuditData<User> auditData;

  private Set<ConfirmationToken> confirmationTokens = new LinkedHashSet<>();

  /**
   * Creates a {@link User} instance.
   *
   * @param id ID
   * @param screenName screen name
   * @param email email
   */
  public User(String id, String screenName, String email) {
    this.id = id;
    this.screenName = screenName;
    contactData.setEmail(email);
  }

  /**
   * Add a confirmation token to the user and invalidates all other confirmation tokens of the same
   * type.
   *
   * @param type Token type
   * @return the newly added confirmation token
   */
  public ConfirmationToken addConfirmationToken(ConfirmationTokenType type) {
    return addConfirmationToken(type, 0);
  }

  public ConfirmationToken addConfirmationToken(ConfirmationToken token) {
    confirmationTokens.add(token);
    return token;
  }

  public Timezone getTimezone() {
    return timezone;
  }

  public String getTimezoneName() {
    return timezone.name();
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

  public ContactData getContactData() {
    return contactData;
  }

  public AuditData<User> getAuditData() {
    return auditData;
  }

  public void setAuditData(AuditData<User> auditData) {
    this.auditData = auditData;
  }

  public void setContactData(ContactData contactData) {
    this.contactData = contactData;
  }

  /**
   * Add a confirmation token to the user and invalidates all other confirmation tokens of the same
   * type.
   *
   * @param type token type
   * @param minutes token's expiration period in minutes
   * @return the newly added confirmation token
   */
  public ConfirmationToken addConfirmationToken(ConfirmationTokenType type, int minutes) {
    if (minutes == 0) {
      minutes = ConfirmationToken.DEFAULT_EXPIRATION_MINUTES;
    }
    // TODO: invalide all other confirmation tokens.
    ConfirmationToken confirmationToken = new ConfirmationToken(this, type, minutes);
    confirmationTokens.add(confirmationToken);
    return confirmationToken;
  }

  /**
   * Gets the confirmation token instance for the given token value, provided that it exists.
   *
   * @param token token's value.
   * @return a confirmation token
   */
  public Optional<ConfirmationToken> getConfirmationToken(String token) {
    return confirmationTokens.stream()
        .filter(ct -> token.equals(ct.getId()))
        .findFirst();
  }

  public String getEmail() {
    return contactData.getEmail();
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public boolean sameIdentityAs(User other) {
    return equals(other);
  }

  public void setEmail(String email) {
    contactData.setEmail(email);
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  public String getScreenName() {
    return screenName;
  }

  public void setScreenName(String screenName) {
    this.screenName = screenName;
  }

  public Password getPassword() {
    return password;
  }

  public void setPassword(Password password) {
    this.password = password;
  }

  public boolean isConfirmed() {
    return confirmed;
  }

  public void setConfirmed(boolean confirmed) {
    this.confirmed = confirmed;
  }

  public boolean isLocked() {
    return locked;
  }

  public void setLocked(boolean locked) {
    this.locked = locked;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  /**
   * Uses the given confirmation token if it exists and it's still valid.
   *
   * @param token token's value
   * @return the used token
   * @throws InvalidTokenException if there was no such token or if it wasn't valid.
   */
  public ConfirmationToken useConfirmationToken(String token)
      throws InvalidTokenException {

    Optional<ConfirmationToken> confirmationTokenHolder = getConfirmationToken(token);
    if (!confirmationTokenHolder.isPresent()) {
      throw new InvalidTokenException();
    }

    ConfirmationToken confirmationToken = confirmationTokenHolder.get();
    if (!confirmationToken.isValid()) {
      throw new InvalidTokenException();
    }


    return confirmationToken.use();
  }

  public Set<ConfirmationToken> getConfirmationTokens() {
   return confirmationTokens;
  }

}
