package com.networknt.portal.usermanagement.auth.crypto;


import com.networknt.portal.usermanagement.auth.model.user.Password;

/**
 * Interface for checking and encrypting passwords.
 */
public interface PasswordSecurity {

  /**
   * Checks the given {@code password} against the given {@code rawPassword}.
   *
   * @param password Encrypted password
   * @param rawPassword Cleartext password
   * @return true if matches, false otherwise
   */
  boolean check(Password password, String rawPassword);

  /**
   * Encrypts the given {@code rawPassword}.
   *
   * @param rawPassword Cleartext password
   * @return encrypted password
   */
  Password ecrypt(String rawPassword);

}
