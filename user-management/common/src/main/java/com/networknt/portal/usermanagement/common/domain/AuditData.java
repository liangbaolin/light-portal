

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

  @Override
  public boolean sameValueAs(AuditData<U> other) {
    return equals(other);
  }

}
