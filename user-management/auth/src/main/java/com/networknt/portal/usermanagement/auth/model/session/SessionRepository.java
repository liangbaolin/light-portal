
package com.networknt.portal.usermanagement.auth.model.session;


import java.util.List;
import java.util.Optional;

/**
 * Repository for managing the lifecycle of {@link Session} entities.
 */
public interface SessionRepository {

  /**
   * Finds a session based on its ID.
   *
   * @param id ID
   * @return a {@link Session}
   */
  Optional<Session> findById(Long id);

  /**
   * Finds all the sessions belonging to the given User ID.
   *
   * @param userId ID
   * @return a {@link Session}
   */
  List<Session> findByUserId(Long userId);

  /**
   * Stores the given session.
   *
   * @param session a {@link Session}
   * @return the stored {@link Session}
   */
  Session save(Session session);

}
