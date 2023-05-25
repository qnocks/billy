package com.qnocks.billy.core.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeUtils {

    public static Date toDate(LocalDateTime value) {
        return Date.from(value.toInstant(ZoneOffset.UTC));
    }

    public static long toSeconds(LocalDate value) {
        var valueWithTime = value.atStartOfDay();
        return valueWithTime.toEpochSecond(ZoneOffset.UTC);
    }

    public static long toSeconds(LocalDateTime value) {
        return value.toEpochSecond(ZoneOffset.UTC);
    }

    public static LocalDate toDate(Long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC).toLocalDate();
    }
}
