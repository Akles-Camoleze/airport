package ifmg.camoleze.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeConverterUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("hmma");

    public static LocalTime convertStringToTime(String timeString) {
        String timeStringWithoutAMPM = timeString + "M";
        LocalTime localTime = LocalTime.parse(timeStringWithoutAMPM, FORMATTER);
        System.out.println(localTime);
        return localTime;
    }

}