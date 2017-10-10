package com.networknt.portal.usermanagement.model.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * LocalDateTimeUtil, util class for using LocalDateTime .
 */
public class LocalDateTimeUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(LocalDateTimeUtil.class);



  public static long getMillisDiff(LocalDateTime time1, LocalDateTime time2) {
    Objects.requireNonNull(time1);
    Objects.requireNonNull(time2);
    Duration duration = Duration.between(time1, time2);
    return duration.toMillis();

  }


  public static int getMinsDiff(LocalDateTime time1, LocalDateTime time2) {
    Objects.requireNonNull(time1);
    Objects.requireNonNull(time2);
    Duration duration = Duration.between(time1, time2);
    return (int) duration.toMinutes();

  }

}
