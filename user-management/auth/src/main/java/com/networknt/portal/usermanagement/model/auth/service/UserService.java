
package com.networknt.portal.usermanagement.model.auth.service;



import com.networknt.portal.usermanagement.model.common.domain.UserDto;
import com.networknt.portal.usermanagement.model.common.exception.InvalidEmailException;
import com.networknt.portal.usermanagement.model.common.exception.InvalidTokenException;
import com.networknt.portal.usermanagement.model.common.exception.NoSuchUserException;
import com.networknt.portal.usermanagement.model.common.model.user.User;

import java.util.List;
import java.util.Optional;

/**
 * {@link UserService} groups functionaries which are needed to manage all the aspects of users.
 */
public interface UserService {

  /**
   * Changes the {@link User}'s email address, provided that {@code newEmail} is available.
   *
   * @param userId {@link User}'s ID
   * @param newEmail new email address
   * @return the modified {@link User}
   * @throws NoSuchUserException if the user doesn't exist

   */
  User changeEmail(String userId, String newEmail)
      throws InvalidEmailException, NoSuchUserException;

  /**
   * Changes the {@link User}'s password.
   *
   * @param userId {@link User}'s ID
   * @param rawPassword new (cleartext) password
   * @return the modified {@link User}
   * @throws NoSuchUserException if the user doesn't exist
   */
  User changePassword(String userId, String rawPassword) throws NoSuchUserException;

  /**
   * Changes the {@link User}'s screen name, provided that {@code newScreenName} is available.
   *
   * @param userId {@link User}'s ID
   * @param newScreenName new screen name
   * @return the modified {@link User}
   * @throws NoSuchUserException if the user doesn't exist
   */
  User changeScreenName(String userId, String newScreenName)
      throws Exception;

  /**
   * Confirms the {@link User}'s email address with the given token, provided that it's valid.
   *
   * @param token confirmation token
   * @return the modified {@link User}
   * @throws NoSuchUserException if the user doesn't exist
   * @throws InvalidTokenException if the given confirmation token is invalid
   */
  User confirmEmail(String token)
      throws InvalidTokenException, NoSuchUserException;

  /**
   * Confirms the {@link User}'s previously requested password reset.
   *
   * @param userId {@link User}'s ID
   * @param token confirmation token
   * @return the modified {@link User}
   * @throws NoSuchUserException if the user doesn't exist
   * @throws InvalidTokenException if the given confirmation token is invalid
   */
  User confirmPasswordReset(String userId, String token)
      throws InvalidTokenException, NoSuchUserException;

  /**
   * Deletes the given {@link User}.
   *
   * @param userId {@link User}'s ID
   */
  int delete(String userId) ;

  /**
   * Finds a {@link User} in the system by its ID.
   *
   * @param userId Email or screen name
   * @return the {@link User} if exists, null otherwise
   */
  Optional<User> findUserById(String userId);

  /**
   * Finds a {@link User} in the system by its ID.
   *
   * @param emailOrScreenName Email or screen name
   * @return the {@link User} if exists, null otherwise
   */
  Optional<User> findUser(String emailOrScreenName);

  /**
   * Finds a {@link User} in the system by its ID.
   *
   * @param userId Email or screen name
   * @return the {@link User}'s ID if exists
   * @throws NoSuchUserException if the user doesn't exist
   */
  User getUserById(String userId) throws NoSuchUserException;

  /**
   * Finds a {@link User} in the system by its ID.
   *
   * @return the {@link User}'s ID if exists
   */
  List<User> getUser();

  /**
   * Finds a {@link User} in the system by its ID.
   *
   * @param emailOrScreenName Email or screen name
   * @return the {@link User}'s ID if exists
   * @throws NoSuchUserException if the user doesn't exist
   */
  User getUser(String emailOrScreenName) throws NoSuchUserException;

  /**
   * Checks if the given {@code email} is taken.
   *
   * @param email email to check
   * @return true if it's taken, false otherwise
   */
  boolean isEmailTaken(String email);

  /**
   * Checks if the given {@code screenName} is taken.
   *
   * @param screenName screenName to check
   * @return true if it's taken, false otherwise
   */
  boolean isScreenNameTaken(String screenName);

  /**
   * Logs a user in with the given {@code emailOrScreenName} and {@code password}.
   *
   * @param emailOrScreenName Email or screen name
   * @param password password
   * @return the {@link User} if it exists and its password matches
   */
  User login(String emailOrScreenName, String password)
      throws NoSuchUserException;

  /**
   * Returns the next available screen name based on the given email address.
   *
   * @param email Email
   * @return an available screen name.
   * @throws Exception if the given {@code email} isn't an email address.
   */
  String nextScreenName(String email) throws Exception;

  /**
   * Request email change for the given user.
   *
   * @param userId {@link User}'s ID
   * @param newEmail new email
   * @throws NoSuchUserException if the user doesn't exist
   */
  void requestEmailChange(String userId, String newEmail)
      throws Exception;

  /**
   * Request password reset for the given {@link User}.
   *
   * @param userId {@link User}'s ID
   * @throws NoSuchUserException if the user doesn't exist
   */
  void requestPasswordReset(String userId) throws NoSuchUserException;

  /**
   * Signs a user up.
   *
   * @param user a {@link User}
   * @param rawPassword {@link User}'s cleartext password
   */
  void signup(User user, String rawPassword)
      throws Exception;

  /**
   * Stores the given {@link User}.
   *
   * @param user a {@link User} to store
   * @return the stored user
   */
  User store(User user)
      throws Exception;

  /**
   * update the given {@link User}.
   *
   * @param user a {@link User} to store
   * @return the stored user
   */
  User update(User user);

  User confirmUser(User user, String token)
          throws NoSuchUserException;

  boolean isEmitEvent();

  void setEmitEvent(boolean emitEvent);

   UserDto toUserDto(User user);

  User fromUserDto(UserDto user);
}
