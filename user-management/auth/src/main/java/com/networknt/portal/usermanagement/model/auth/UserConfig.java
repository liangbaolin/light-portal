package com.networknt.portal.usermanagement.model.auth;



/**
 * A User setting configuration file. It get from defined resource yml file
 *
 */
public class UserConfig {
    private String smtpHost;
    private String fromEmail;
    private String subject;
    private boolean emitEvent;
    private String content;
    private String serverHost;
    private String password;
    private String port;

    public UserConfig() {
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isEmitEvent() {
        return emitEvent;
    }

    public void setEmitEvent(boolean emitEvent) {
        this.emitEvent = emitEvent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
