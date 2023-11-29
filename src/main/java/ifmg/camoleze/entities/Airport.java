package ifmg.camoleze.entities;

import java.util.TimeZone;

public class Airport {
    private String abbreviation;
    private String name;
    private TimeZone timeZone;
    private Integer latitude;
    private Integer longitude;

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public static Airport createAirportFromValues(String[] values) {
        Airport airport = new Airport();
        airport.setAbbreviation(values[0]);
        airport.setTimeZone(TimeZone.getTimeZone("GMT" + values[1]));
        airport.setLongitude(Integer.parseInt(values[2]));
        airport.setLatitude(Integer.parseInt(values[3]));
        airport.setName(values[4]);
        return airport;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getName() {
        return name;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public Integer getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return this.abbreviation;
    }
}
