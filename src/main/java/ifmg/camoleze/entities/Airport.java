package ifmg.camoleze.entities;

import java.util.TimeZone;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import ifmg.camoleze.structs.ArrayList;

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

    @Override
    public String toString() {
        return "Airport{" +
                "abbreviation='" + abbreviation + '\'' +
                ", name='" + name + '\'' +
                ", timeZone=" + timeZone +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
