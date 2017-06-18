
package com.networknt.portal.usermanagement.model.common.domain.contact;

import com.networknt.portal.usermanagement.model.common.domain.ValueObject;

/**
 * Represents an address.
 */
public class AddressData implements ValueObject<AddressData> {

  private Country country;
  private State state;
  private String city;

  private String addressLine1;

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getAddressLine1() {
    return addressLine1;
  }

  public void setAddressLine1(String addressLine1) {
    this.addressLine1 = addressLine1;
  }

  public String getAddressLine2() {
    return addressLine2;
  }

  public void setAddressLine2(String addressLine2) {
    this.addressLine2 = addressLine2;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public AddressType getAddressType() {
    return addressType;
  }

  public void setAddressType(AddressType addressType) {
    this.addressType = addressType;
  }

  private String addressLine2;

  private String zipCode;

  private AddressType addressType;


  @Override
  public boolean sameValueAs(AddressData other) {
    return equals(other);
  }

}
