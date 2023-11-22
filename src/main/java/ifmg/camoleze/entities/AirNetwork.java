package ifmg.camoleze.entities;

import ifmg.camoleze.structs.ArrayList;
import ifmg.camoleze.structs.EdgeProcessor;
import ifmg.camoleze.structs.Graph;

public class AirNetwork {
    private ArrayList<Airport> vertices;
    private Graph<Airport, Route> routes;
    private Graph<Airport, Flight> flights;

    public AirNetwork() {
        this.vertices = new ArrayList<>();
        routes = new Graph<>(this.vertices);
        flights = new Graph<>(this.vertices);
    }

    public void addVertex(Airport vertex) {
        this.routes.addVertex(vertex);
        this.flights.addVertex(vertex);
    }

    public void addEdge(Airport source, Airport destination, Route route) {
        this.routes.addEdge(source, destination, route);
    }

    public void addEdge(Airport source, Airport destination, Flight flight) {
        this.flights.addEdge(source, destination, flight);
    }

    public void showFlights() {
        EdgeProcessor<Airport, Flight> processor = (source, flight, destin) -> {
            String abbrSource = source.getAbbreviation();
            String abbrDestin = destin.getAbbreviation();
            System.out.printf("%s-->%s-->%s\n", abbrSource, flight.toString(), abbrDestin);
        };

        this.flights.processEdges(processor);
    }

    public void showRoutes() {
        EdgeProcessor<Airport, Route> processor = (source, route, destin) -> {
            String abbrSource = source.getAbbreviation();
            String abbrDestin = destin.getAbbreviation();
            int distance = route.getDistance();
            System.out.printf("%s-->%s-->%s\n", abbrSource, distance, abbrDestin);
        };

        this.routes.processEdges(processor);
    }

    public ArrayList<Airport> getVertices() {
        return vertices;
    }

    public Graph<Airport, Route> getRoutes() {
        return routes;
    }

    public Graph<Airport, Flight> getFlights() {
        return flights;
    }


}
