package com.networknt.portal.usermanagement.common.event;

/**
 * Functional interface to emitting .
 */
@FunctionalInterface
public interface UserEventEmitter {

  void emit(UserEvent userEvent);

}
