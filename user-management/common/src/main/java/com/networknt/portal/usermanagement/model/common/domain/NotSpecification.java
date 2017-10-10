
package com.networknt.portal.usermanagement.model.common.domain;

/**
 * NOT decorator, used to create a new specifcation that is the inverse (NOT) of the given spec.
 */
public class NotSpecification<T> extends AbstractSpecification<T> {

  private Specification<T> spec;

  /**
   * Create a new NOT specification based on another spec.
   *
   * @param spec Specification instance to not.
   */
  public NotSpecification(final Specification<T> spec) {
    this.spec = spec;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isSatisfiedBy(final T t) {
    return !spec.isSatisfiedBy(t);
  }

}
