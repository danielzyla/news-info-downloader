package io.github.danielzyla.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeConverter {
    public static String getFormattedPublishedAt(ZonedDateTime zonedDateTimeToFormat) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");
        ZonedDateTime zonedDateTime = zonedDateTimeToFormat.withZoneSameInstant(ZoneId.systemDefault());
        return dateTimeFormatter.format(zonedDateTime);
    }
}
