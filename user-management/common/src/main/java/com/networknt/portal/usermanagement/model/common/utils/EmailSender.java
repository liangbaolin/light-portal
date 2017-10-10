package com.networknt.portal.usermanagement.model.common.utils;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;



public class EmailSender {

    String host;
    String from;
    String to;

    public EmailSender(String host, String from, String to) {
        this.host = host;
        this.from = from;
        this.to = to;
    }

    public void sendMail (String subject, String content) throws MessagingException{
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", this.host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        message.setSubject(subject);

        message.setContent(content, "text/html");

        // Send message
        Transport.send(message);
    }
}