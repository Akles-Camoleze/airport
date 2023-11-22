package ifmg.camoleze.entities;

import ifmg.camoleze.structs.lists.CollectionList;
import ifmg.camoleze.structs.lists.ArrayList;
import ifmg.camoleze.structs.graphs.Graph;

public class AirNetwork {
    private final ArrayList<Airport> vertices;
    private final Graph<Airport, Route> routes;
    private final Graph<Airport, CollectionList<Flight>> flights;

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

    public Graph<Airport, CollectionList<Flight>> getFlights() {
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
