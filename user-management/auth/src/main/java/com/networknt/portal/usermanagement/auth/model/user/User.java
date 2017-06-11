
package com.networknt.portal.usermanagement.auth.model.user;


import com.networknt.portal.usermanagement.common.domain.AuditData;
import com.networknt.portal.usermanagement.common.domain.Entity;
import com.networknt.portal.usermanagement.common.domain.contact.ContactData;
import com.networknt.portal.usermanagement.common.exception.InvalidTokenException;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;


/**
 * Model class for users.
 */

public class User implements Entity<Long, User> {

  private Long id;
  private String screenName;

  private ContactData contactData = new ContactData();

  private Password password;

  private Set<String> authorities = new LinkedHashSet<>();

  private Timezone timezone = Timezone.AMERICA_LOS_ANGELES;
  private Locale locale = Locale.US;

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
  public User(Long id, String screenName, String email) {
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

  public Locale getLocale() {
    return locale;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
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
        .filter(ct -> token.equals(ct.getValue()))
        .findFirst();
  }

  public String getEmail() {
    return contactData.getEmail();
  }

  @Override
  public Long getId() {
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
  public void setId(Long id) {
    this.id = id;
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

}
