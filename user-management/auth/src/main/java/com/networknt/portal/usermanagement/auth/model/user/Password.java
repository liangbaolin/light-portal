
package com.networknt.portal.usermanagement.auth.model.user;


import com.networknt.portal.usermanagement.common.domain.ValueObject;

/**
 * Represents Users' password.
 */

public class Password implements ValueObject<Password> {

  private String passwordHash;
  private String passwordSalt;

  /**
   * Create a new password.
   *
   * @param passwordHash password hash
   * @param passwordSalt password salt
   */
  public Password(String passwordHash, String passwordSalt) {
    this.passwordHash = passwordHash;
    this.passwordSalt = passwordSalt;
  }

  @Override
  public boolean sameValueAs(Password other) {
    return equals(other);
  }

}
