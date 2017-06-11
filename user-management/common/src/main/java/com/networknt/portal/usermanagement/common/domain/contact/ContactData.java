
package com.networknt.portal.usermanagement.common.domain.contact;

import com.networknt.portal.usermanagement.common.domain.ValueObject;

import java.time.LocalDate;
import java.util.Set;


/**
 * Represents a contact.
 */
public class ContactData implements ValueObject<ContactData> {

  private String email;

  private String firstName;
  private String lastName;

  private Set<AddressData> addresses;

  private Gender gender;

  private LocalDate birthday;


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Set<AddressData> getAddresses() {
    return addresses;
  }

  public void setAddresses(Set<AddressData> addresses) {
    this.addresses = addresses;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(LocalDate birthday) {
    this.birthday = birthday;
  }

  @Override
  public boolean sameValueAs(ContactData other) {
    return equals(other);
  }

}
