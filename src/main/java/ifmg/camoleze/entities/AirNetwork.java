package ifmg.camoleze.entities;

import ifmg.camoleze.structs.graphs.EdgeProcessor;
import ifmg.camoleze.structs.graphs.LinkedGraph;
import ifmg.camoleze.structs.graphs.Vertex;
import ifmg.camoleze.structs.lists.ArrayList;
import ifmg.camoleze.structs.graphs.ArrayGraph;
import ifmg.camoleze.structs.map.HashMap;
import java.time.Duration;

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

        if (flightsForDestin == null || flightsForDestin.size() == 0) {
            throw new RuntimeException("Não foram encontrados voos de " + source + " para " + destin);
        }

        FlightDurationPair flightDurationPair = null;

        for (Flight flight : flightsForDestin) {
            Duration duration = flight.getDuration(source.getTimeZone(), destin.getTimeZone());
            if (flightDurationPair == null || duration.compareTo(flightDurationPair.duration) < 0) {
                flightDurationPair = new FlightDurationPair(flight, duration);
            }
        }

        return flightDurationPair;
    }

    public void calculateAllFlightsDuration() {
        EdgeProcessor<Airport, Flight> edgeProcessor = AirNetwork::showDurations;
        this.flights.processEdges(edgeProcessor);
    }

    private static void showDurations(Airport source, Flight flight, Airport destin) {
        Duration duration = flight.getDuration(source.getTimeZone(), destin.getTimeZone());
        FlightDurationPair flightDurationPair = new FlightDurationPair(flight, duration);
        System.out.println(flightDurationPair);
    }

    public record FlightDurationPair(Flight flight, Duration duration) {
        @Override
        public String toString() {
            return flight + "=(" + String.format("%d:%02d", duration.toHours(), duration.toMinutesPart()) + ')';
        }
    }

}
