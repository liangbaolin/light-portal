package com.networknt.portal.usermanagement.model.common.utils;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;



public class EmailSender {

    String host;
    String from;
    String password;
    String port;

    public EmailSender(String host, String port, String from, String password) {
        this.host = host;
        this.from = from;
        this.password = password;
        this.port = port;
    }

    public void sendMail (String to, String subject, String content) throws MessagingException{
        Properties props = new Properties();
        props.put("mail.smtp.user", this.from);
        props.put("mail.smtp.host", this.host);
        props.put("mail.smtp.port", this.port);
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.auth", "true");
     //   props.put("mail.smtp.socketFactory.port", this.port);
     //   props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
     //   props.put("mail.smtp.socketFactory.fallback", "false");

        SMTPAuthenticator auth = new SMTPAuthenticator(from, password);
        Session session = Session.getInstance(props, auth);

        //Properties properties = System.getProperties();
        //properties.setProperty("mail.smtp.host", this.host);
     //   Session session = Session.getDefaultInstance(properties);

        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        message.setSubject(subject);

        message.setContent(content, "text/html");

        // Send message
        Transport.send(message);
    }

    private class SMTPAuthenticator extends Authenticator {

        public  String user = "";
        public  String password = "";

        public SMTPAuthenticator(String user, String password) {
            this.user = user;
            this.password = password ;

        }

        public PasswordAuthentication getPasswordAuthentication()
        {
            return new PasswordAuthentication(this.user, this.password);
        }
    }
}