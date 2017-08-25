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
}
