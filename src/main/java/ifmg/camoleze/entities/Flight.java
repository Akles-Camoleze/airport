package ifmg.camoleze.entities;

import ifmg.camoleze.requirements.RequiredAttributes;
import java.time.LocalTime;

public class Flight extends RequiredAttributes {
    private String airline;
    private LocalTime departure;
    private LocalTime arrival;
    private Integer stops;

    public Flight(int id) {
        super(id);
    }

    public Flight(int id, String airline, LocalTime departure, LocalTime arrival, Integer stops) {
        super(id);
        this.airline = airline;
        this.departure = departure;
        this.arrival = arrival;
        this.stops = stops;
    }

    public String getAirline() {
        return airline;
    }

    public LocalTime getDeparture() {
        return departure;
    }

    public LocalTime getArrival() {
        return arrival;
    }

    public Integer getStops() {
        return stops;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public void setDeparture(LocalTime departure) {
        this.departure = departure;
    }

    public void setArrival(LocalTime arrival) {
        this.arrival = arrival;
    }

    public void setStops(Integer stops) {
        this.stops = stops;
    }

    @Override
    public String toString() {
        return "(Voo " + id +
                ", airline: " + airline +
                ", saida: " + departure +
                ", chegada: " + arrival +
                ", paradas: " + stops +
                ")";
    }
}
