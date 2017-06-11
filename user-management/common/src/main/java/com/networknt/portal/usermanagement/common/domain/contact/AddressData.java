
package com.networknt.portal.usermanagement.common.domain.contact;

import com.networknt.portal.usermanagement.common.domain.ValueObject;

/**
 * Represents an address.
 */
public class AddressData implements ValueObject<AddressData> {

  private Country country;
  private State state;
  private String city;

  private String addressLine1;
  private String addressLine2;

  private String zipCode;

  private AddressType addressType;

  @Override
  public boolean sameValueAs(AddressData other) {
    return equals(other);
  }

}
