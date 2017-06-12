package com.networknt.portal.usermanagement.common.event;

/**
 * Created by gavin on 2017-06-12.
 */
public interface UserEvent<T> {
    /**
     * The identity may be explicit, for example the sequence number of a payment,
     * or it could be derived from various aspects of the event such as where, when and what
     * has happened.
     *
     * @param other The other domain event
     * @return <code>true</code> if the given domain event and this event are regarded as being the
     *     same event
     */
    boolean sameEventAs(T other);
}
