
package com.networknt.portal.usermanagement.auth.model.session;

import com.networknt.portal.usermanagement.common.domain.Entity;


import java.time.LocalDateTime;

import static java.time.Clock.systemUTC;
import static java.time.temporal.ChronoUnit.MINUTES;


/**
 * Data stored in User's session.
 */

public class Session implements Entity<Long, Session> {

  public static final int DEFAULT_EXPIRATION_MINUTES = 30 * 24 * 60;

  private Long id;
  private Long userId;

  private String token;

  private LocalDateTime expiresAt;
  private LocalDateTime issuedAt;
  private LocalDateTime lastUsedAt;
  private LocalDateTime removedAt;

  private boolean deleted;

  /**
   * Creates a new {@link Session} with the given ID for the given User ID.
   *
   * @param id Session ID
   * @param userId User ID
   */
  public Session(Long id, Long userId, String token) {
    this(id, userId, token, DEFAULT_EXPIRATION_MINUTES);
  }

  /**
   * Creates a new {@link Session} with the given ID for the given User ID.
   *
   * @param id Session ID
   * @param userId User ID
   * @param token Token's token
   * @param minutes minutes to expire from time of issue
   */
  public Session(Long id, Long userId, String token, int minutes) {
    this.id = id;
    this.userId = userId;
    this.token = token;
    if (minutes == 0) {
      minutes = DEFAULT_EXPIRATION_MINUTES;
    }
    expiresAt = LocalDateTime.now(systemUTC()).plus(minutes, MINUTES);
    issuedAt = LocalDateTime.now(systemUTC());
  }

  /**
   * Use for testing only.
   *
   * @param id Session ID
   * @param userId User ID
   * @param token Token's token
   * @param expiresAt expire at
   * @param issuedAt issued at
   */
  public Session(
      Long id, Long userId, String token, LocalDateTime expiresAt, LocalDateTime issuedAt) {

    this.id = id;
    this.userId = userId;
    this.token = token;
    this.expiresAt = expiresAt;
    this.issuedAt = issuedAt;
  }

  @Override
  public Long getId() {
    return id;
  }

  /**
   * Returns is this session if still valid.
   *
   * @return true if valid, false otherwise
   */
  public boolean isValid() {
    LocalDateTime now = LocalDateTime.now(systemUTC());
    return isValid(now);
  }

  public boolean isValid(LocalDateTime now) {
    return expiresAt.isAfter(now) && !deleted;
  }

  @Override
  public boolean sameIdentityAs(Session other) {
    return false;
  }

  public Long getUserId() {
    return userId;
  }

  public String getToken() {
    return token;
  }

  public LocalDateTime getExpiresAt() {
    return expiresAt;
  }

  public LocalDateTime getIssuedAt() {
    return issuedAt;
  }

  public LocalDateTime getLastUsedAt() {
    return lastUsedAt;
  }

  public LocalDateTime getRemovedAt() {
    return removedAt;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
    this.removedAt = deleted ? LocalDateTime.now(systemUTC()) : null;
  }

  public void setRemovedAt(LocalDateTime removedAt) {
    this.removedAt = removedAt;
  }

  public void setLastUsedAt(LocalDateTime lastUsedAt) {
    this.lastUsedAt = lastUsedAt;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }


}
