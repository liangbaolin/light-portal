

package com.networknt.portal.usermanagement.common.domain;

import java.time.LocalDateTime;

/**
 * Value object for keeping audit related data together.
 */
public class AuditData<U> implements ValueObject<AuditData<U>> {

  private LocalDateTime createdAt;
  private U createdBy;

  private LocalDateTime modifiedAt;
  private U modifiedBy;

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public U getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(U createdBy) {
    this.createdBy = createdBy;
  }

  public LocalDateTime getModifiedAt() {
    return modifiedAt;
  }

  public void setModifiedAt(LocalDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  public U getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(U modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  @Override
  public boolean sameValueAs(AuditData<U> other) {
    return equals(other);
  }

}
