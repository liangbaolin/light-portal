
package com.networknt.portal.usermanagement.model;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    
    private String password;
    
    private ContactData contactData;
    
    private String timezone;
    
    private String host;
    
    private String id;
    
    private String screenName;
    
    private String locale;
    

    public User () {
    }

    
    
    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
    @JsonProperty("contactData")
    public ContactData getContactData() {
        return contactData;
    }

    public void setContactData(ContactData contactData) {
        this.contactData = contactData;
    }
    
    
    
    @JsonProperty("timezone")
    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
    
    
    
    @JsonProperty("host")
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
    
    
    
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
    
    @JsonProperty("screenName")
    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
    
    
    
    @JsonProperty("locale")
    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
    
    

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User User = (User) o;

        return Objects.equals(password, User.password) &&
        Objects.equals(contactData, User.contactData) &&
        Objects.equals(timezone, User.timezone) &&
        Objects.equals(host, User.host) &&
        Objects.equals(id, User.id) &&
        Objects.equals(screenName, User.screenName) &&
        
        Objects.equals(locale, User.locale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, contactData, timezone, host, id, screenName,  locale);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class User {\n");
        
        sb.append("    password: ").append(toIndentedString(password)).append("\n");
        sb.append("    contactData: ").append(toIndentedString(contactData)).append("\n");
        sb.append("    timezone: ").append(toIndentedString(timezone)).append("\n");
        sb.append("    host: ").append(toIndentedString(host)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    screenName: ").append(toIndentedString(screenName)).append("\n");
        sb.append("    locale: ").append(toIndentedString(locale)).append("\n");
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
