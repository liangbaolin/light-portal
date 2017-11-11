package com.networknt.portal.usermanagement.model.common.utils;

import com.networknt.config.Config;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;

import static java.time.Clock.systemUTC;
import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * Created by gavin on 2017-08-23.
 */
public class EmailSenderTest {

    static EmailSender sender;

    @BeforeClass
    public static void setUp() {
        sender = new EmailSender("mail.lightapi.net", "587", "noreply@lightapi.net","change-to-real");

    }

    @Test
    public void testSendEmail()  throws Exception {
     //   sender.sendMail("sent.to@gmail.com","testEmail", "test-test");
    }
}
