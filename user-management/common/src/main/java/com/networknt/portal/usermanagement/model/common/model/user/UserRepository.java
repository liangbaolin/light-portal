
package com.networknt.portal.usermanagement.model.common.model.user;


import com.networknt.portal.usermanagement.model.common.exception.NoSuchUserException;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing the lifecycle of {@link User} entities.
 */
public interface UserRepository {

  /**
   * Deletes the given user, provided that it exists.
   *
   * @param userId {@link User}'s ID
   */
  int  delete(String userId);

  /**
   * Finds a user based on its ID.
   *
   * @return a list of {@link User}
   */
  List<User> getAllUsers();

  /**
   * Finds a user based on its ID.
   *
   * @return a list of {@link User}
   */
  String getUserIdByToken(String token);

  /**
   * Finds a user based on its ID.
   *
   * @param userId ID
   * @return a {@link User}
   */
  Optional<User> findById(String userId);

  /**
   * Finds a user by email address.
   *
   * @param email Email address
   * @return a {@link User}
   */
  Optional<User> findByEmail(String email);

  /**
   * Finds a user by screen name.
   * @param screenName Screen name
   * @return a {@link User}
   */
  Optional<User> findByScreenName(String screenName);

  /**
   * Stores the given user.
   * @param user a {@link User}
   * @return the stored {@link User}
   */
  User save(User user);

  /**
   * update the given user.
   * @param user a {@link User}
   * @return the stored {@link User}
   */
  User update(User user);

  /**
   * Active the given user, provided that it exists.
   *
   * @param userId {@link User}'s ID
   * @param token {@link User}'s token
   * @throws NoSuchUserException if the user doesn't
   *     exist
   */
  void activeUser(String userId, String token) throws NoSuchUserException;
}
