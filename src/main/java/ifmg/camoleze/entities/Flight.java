package ifmg.camoleze.entities;

import ifmg.camoleze.entities.requirements.RequiredAttributes;
import ifmg.camoleze.entities.requirements.RequiredMethods;

public class Flight extends RequiredAttributes implements RequiredMethods {
    private String airline;
    private String departure;
    private String arrival;
    private Integer stops;

    public Flight(int id) {
        super(id);
    }

    public Flight(int id, String airline, String departure, String arrival, Integer stops) {
        super(id);
        this.airline = airline;
        this.departure = departure;
        this.arrival = arrival;
        this.stops = stops;
    }

    public String getAirline() {
        return airline;
    }

    public String getDeparture() {
        return departure;
    }

    public String getArrival() {
        return arrival;
    }

    public Integer getStops() {
        return stops;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public void setStops(Integer stops) {
        this.stops = stops;
    }

    @Override
    public String showInGraph() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "Flight{" +
                "airline='" + airline + '\'' +
                ", departure='" + departure + '\'' +
                ", arrival='" + arrival + '\'' +
                ", stops=" + stops +
                ", id=" + id +
                '}';
    }
}
