package com.networknt.portal.usermanagement.model.common.utils;

import com.networknt.portal.usermanagement.model.common.utils.LocalDateTimeUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;

import static java.time.Clock.systemUTC;
import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * Created by gavin on 2017-08-23.
 */
public class LocalDateTimeUtilTest {

    static LocalDateTime t1, t2;

    @BeforeClass
    public static void setUp() {
        t1 = LocalDateTime.now(systemUTC()).plus(10*60, MINUTES);
        t2 = LocalDateTime.now(systemUTC());

    }

    @Test
    public void testGetMinsDiff()  throws Exception {
        System.out.println("Minutes:" +LocalDateTimeUtil.getMinsDiff(t2, t1));
    }
}
