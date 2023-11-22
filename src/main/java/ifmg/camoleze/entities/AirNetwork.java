package ifmg.camoleze.entities;

import ifmg.camoleze.structs.ArrayList;
import ifmg.camoleze.structs.Graph;

import java.util.Optional;

public class AirNetwork {
    private final ArrayList<Airport> vertices;
    private final Graph<Airport, Route> routes;
    private final Graph<Airport, Flight> flights;

    public AirNetwork() {
        this.vertices = new ArrayList<>();
        routes = new Graph<>(this.vertices);
        flights = new Graph<>(this.vertices);
    }

    public void addVertex(Airport vertex) {
        this.routes.addVertex(vertex);
        this.flights.addVertex(vertex);
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

    public Airport findVertexByAbbreviation(String abbr) {
        for (Airport airport : vertices) {
            String value = airport.getAbbreviation();
            if (value != null && value.equals(abbr)) {
                return airport;
            }
        }
        return null;
    }

}
