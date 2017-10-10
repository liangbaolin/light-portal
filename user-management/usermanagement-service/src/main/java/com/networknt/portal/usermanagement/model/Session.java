
package com.networknt.portal.usermanagement.model;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Session {

    
    private java.math.BigDecimal userId;
    
    private java.math.BigDecimal sessionId;
    
    private String token;
    

    public Session () {
    }

    
    
    @JsonProperty("userId")
    public java.math.BigDecimal getUserId() {
        return userId;
    }

    public void setUserId(java.math.BigDecimal userId) {
        this.userId = userId;
    }
    
    
    
    @JsonProperty("sessionId")
    public java.math.BigDecimal getSessionId() {
        return sessionId;
    }

    public void setSessionId(java.math.BigDecimal sessionId) {
        this.sessionId = sessionId;
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
        Session Session = (Session) o;

        return Objects.equals(userId, Session.userId) &&
        Objects.equals(sessionId, Session.sessionId) &&
        
        Objects.equals(token, Session.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, sessionId,  token);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Session {\n");
        
        sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
        sb.append("    sessionId: ").append(toIndentedString(sessionId)).append("\n");
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
