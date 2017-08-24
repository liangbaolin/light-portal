
package com.networknt.portal.usermanagement.model;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactData {

    
    private java.time.LocalDateTime birthday;
    
    private String firstName;
    
    private String lastName;
    
    
    
    public enum GenderEnum {
        
        FEMALE ("FEMALE"),
        
        MALE ("MALE"),
        
        UNKNOWN ("UNKNOWN");
        

        private final String value;

        GenderEnum(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        public static GenderEnum fromValue(String text) {
            for (GenderEnum b : GenderEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                return b;
                }
            }
            return null;
        }
    }

    private GenderEnum gender;

    
    
    private String email;
    

    public ContactData () {
    }

    
    
    @JsonProperty("birthday")
    public java.time.LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(java.time.LocalDateTime birthday) {
        this.birthday = birthday;
    }
    
    
    
    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    
    
    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    
    
    @JsonProperty("gender")
    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }
    
    
    
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ContactData ContactData = (ContactData) o;

        return Objects.equals(birthday, ContactData.birthday) &&
        Objects.equals(firstName, ContactData.firstName) &&
        Objects.equals(lastName, ContactData.lastName) &&
        Objects.equals(gender, ContactData.gender) &&
        
        Objects.equals(email, ContactData.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthday, firstName, lastName, gender,  email);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ContactData {\n");
        
        sb.append("    birthday: ").append(toIndentedString(birthday)).append("\n");
        sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
        sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
        sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
        sb.append("    email: ").append(toIndentedString(email)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
