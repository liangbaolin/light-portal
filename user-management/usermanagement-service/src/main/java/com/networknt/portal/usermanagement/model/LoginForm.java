
package com.networknt.portal.usermanagement.model;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginForm {

    
    private String nameOrEmail;
    
    private String password;
    
    private String token;
    

    public LoginForm () {
    }

    
    
    @JsonProperty("nameOrEmail")
    public String getNameOrEmail() {
        return nameOrEmail;
    }

    public void setNameOrEmail(String nameOrEmail) {
        this.nameOrEmail = nameOrEmail;
    }
    
    
    
    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LoginForm LoginForm = (LoginForm) o;

        return Objects.equals(nameOrEmail, LoginForm.nameOrEmail) &&
        Objects.equals(password, LoginForm.password) &&
        
        Objects.equals(token, LoginForm.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameOrEmail, password,  token);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class LoginForm {\n");
        
        sb.append("    nameOrEmail: ").append(toIndentedString(nameOrEmail)).append("\n");
        sb.append("    password: ").append(toIndentedString(password)).append("\n");
        sb.append("    token: ").append(toIndentedString(token)).append("\n");
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
