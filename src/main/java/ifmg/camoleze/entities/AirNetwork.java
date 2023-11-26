package ifmg.camoleze.entities;

import ifmg.camoleze.structs.graphs.EdgeProcessor;
import ifmg.camoleze.structs.graphs.LinkedGraph;
import ifmg.camoleze.structs.graphs.Vertex;
import ifmg.camoleze.structs.lists.ArrayList;
import ifmg.camoleze.structs.graphs.ArrayGraph;
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

    public void calculateAllFlightsDuration() {
        EdgeProcessor<Airport, Flight> edgeProcessor = AirNetwork::flightsDurationCalculator;
        this.flights.processEdges(edgeProcessor);
    }

    private static void flightsDurationCalculator(Airport source, Flight edge, Airport destin) {
        TimeZone sourceTimeZone = source.getTimeZone();
        TimeZone destinTimeZone = destin.getTimeZone();
        LocalTime departure = edge.getDeparture();
        LocalTime arrival = edge.getArrival();

        ZonedDateTime zonedDateTime1 = TimeConverterUtil.localTimeToZonedDateTime(departure, sourceTimeZone);
        ZonedDateTime zonedDateTime2 = TimeConverterUtil.localTimeToZonedDateTime(arrival, destinTimeZone);

        Duration diff = TimeConverterUtil.calculateHourDifference(zonedDateTime1, zonedDateTime2);
        System.out.printf("Voo %d -> %d:%02d\n", edge.getId(), diff.toHours(), diff.toMinutesPart());
    }

}
