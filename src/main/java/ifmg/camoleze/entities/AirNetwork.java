package ifmg.camoleze.entities;

import ifmg.camoleze.structs.graphs.EdgeProcessor;
import ifmg.camoleze.structs.graphs.LinkedGraph;
import ifmg.camoleze.structs.graphs.Vertex;
import ifmg.camoleze.structs.lists.ArrayList;
import ifmg.camoleze.structs.graphs.ArrayGraph;
import ifmg.camoleze.structs.map.HashMap;
import ifmg.camoleze.utils.TimeConverterUtil;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.TimeZone;

public class AirNetwork {
    private final ArrayList<Vertex<Airport>> vertices;
    private final ArrayGraph<Airport, Route> routes;
    private final LinkedGraph<Airport, Flight> flights;

    public AirNetwork() {
        this.vertices = new ArrayList<>();
        routes = new ArrayGraph<>(this.vertices, false);
        flights = new LinkedGraph<>(this.vertices, true);
    }

    public void addVertex(Airport airport) {
        Vertex<Airport> vertex = new Vertex<>(airport);
        this.routes.addVertex(vertex);
        this.flights.addVertex(vertex);
    }

    public ArrayList<Vertex<Airport>> getVertices() {
        return vertices;
    }

    public ArrayGraph<Airport, Route> getRoutes() {
        return routes;
    }

    public LinkedGraph<Airport, Flight> getFlights() {
        return flights;
    }

    public FlightDurationPair getFastedFlight(Vertex<Airport> sourceVertex, Vertex<Airport> destinVertex) {
        if (sourceVertex == null || destinVertex == null) {
            throw new RuntimeException("Vertice inválido");
        }

        HashMap<Vertex<Airport>, ArrayList<Flight>> sourceFlights = this.flights.getEdgesFromVertex(sourceVertex);
        ArrayList<Flight> flightsForDestin = sourceFlights.get(destinVertex);

        Airport source = sourceVertex.getData();
        Airport destin = destinVertex.getData();

        if (flightsForDestin == null) {
            throw new RuntimeException("Não foram encontrados voos de " + source + " para " + destin);
        }

        Flight fasterFlight = flightsForDestin.get(0);
        Duration smallerDuration = flightsDurationCalculator(source, fasterFlight, destin);
        FlightDurationPair flightDurationPair = new FlightDurationPair(fasterFlight, smallerDuration);

        for (int i = 1; i < flightsForDestin.size(); i++) {
            Flight flight = flightsForDestin.get(i);
            Duration duration = flightsDurationCalculator(source, flight, destin);
            if (duration.compareTo(smallerDuration) < 0) {
                flightDurationPair = new FlightDurationPair(flight, duration);
            }
        }

        return flightDurationPair;
    }

    public void calculateAllFlightsDuration() {
        EdgeProcessor<Airport, Flight> edgeProcessor = AirNetwork::showDurations;
        this.flights.processEdges(edgeProcessor);
    }

    private static Duration flightsDurationCalculator(Airport source, Flight edge, Airport destin) {
        TimeZone sourceTimeZone = source.getTimeZone();
        TimeZone destinTimeZone = destin.getTimeZone();
        LocalTime departure = edge.getDeparture();
        LocalTime arrival = edge.getArrival();

        ZonedDateTime zonedDateTime1 = TimeConverterUtil.localTimeToZonedDateTime(departure, sourceTimeZone);
        ZonedDateTime zonedDateTime2 = TimeConverterUtil.localTimeToZonedDateTime(arrival, destinTimeZone);

        return TimeConverterUtil.calculateHourDifference(zonedDateTime1, zonedDateTime2);
    }

    private static void showDurations(Airport source, Flight edge, Airport destin) {
        Duration duration = flightsDurationCalculator(source, edge, destin);
        System.out.printf("Voo %d -> %d:%02d\n", edge.getId(), duration.toHours(), duration.toMinutesPart());
    }

    public record FlightDurationPair(Flight flight, Duration duration) {
        @Override
        public String toString() {
            return flight + "=(" + String.format("%d:%02d", duration.toHours(), duration.toMinutesPart()) + ')';
        }
    }

}
