package ifmg.camoleze.utils;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import java.time.Duration;
import java.time.LocalDate;

public class TimeConverterUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("hmma");

    public static LocalTime convertStringToTime(String timeString) {
        String timeStringWithoutAMPM = timeString + "M";
        return LocalTime.parse(timeStringWithoutAMPM, FORMATTER);
    }

    public static ZonedDateTime localTimeToZonedDateTime(LocalTime localTime, TimeZone timeZone) {
        return ZonedDateTime.of(LocalDate.now(), localTime, ZoneId.of(timeZone.getID()));
    }

    public static Duration calculateHourDifference(ZonedDateTime zonedDateTime1, ZonedDateTime zonedDateTime2) {
        return Duration.between(zonedDateTime1, zonedDateTime2).abs();
    }

}