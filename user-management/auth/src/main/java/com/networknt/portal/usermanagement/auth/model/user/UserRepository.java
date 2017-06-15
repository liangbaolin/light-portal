
package com.networknt.portal.usermanagement.auth.model.user;


import com.networknt.portal.usermanagement.common.exception.NoSuchUserException;

import java.util.Optional;

/**
 * Repository for managing the lifecycle of {@link User} entities.
 */
public interface UserRepository {

  /**
   * Deletes the given user, provided that it exists.
   *
   * @param userId {@link User}'s ID
   * @throws NoSuchUserException if the user doesn't
   *     exist
   */
  void delete(Long userId) throws NoSuchUserException;

  /**
   * Finds a user based on its ID.
   *
   * @param userId ID
   * @return a {@link User}
   */
  Optional<User> findById(Long userId);

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

}
