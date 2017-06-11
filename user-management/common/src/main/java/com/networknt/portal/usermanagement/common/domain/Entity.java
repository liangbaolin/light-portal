
package com.networknt.portal.usermanagement.common.domain;

/**
 * An entity, as explained in the DDD book.
 */
public interface Entity<I, E> {

  /**
   * Entities compare by identity, not by attributes.
   *
   * @param other The other entity.
   * @return true if the identities are the same, regardles of other attributes.
   */
  boolean sameIdentityAs(E other);

  /**
   * Gets the entity's unique ID.
   *
   * @return ID
   */
  I getId();

  /**
   * Sets the entity's unique ID.
   *
   * @param id ID
   */
  default void setId(I id) {
  }

  /**
   * Checks if {@code this} is a new entity or it isn't. An entity is new if it hasn't been an
   * identity assigned to.
   *
   * @return {@code true} if this is new entity, {@code false} otherwise
   */
  default boolean isNew() {
    if (getId() == null) {
      return true;
    }
    return false;
  }

}
