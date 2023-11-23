package ifmg.camoleze.entities;

import ifmg.camoleze.structs.graphs.LinkedGraph;
import ifmg.camoleze.structs.lists.ArrayList;
import ifmg.camoleze.structs.graphs.ArrayGraph;

public class AirNetwork {
    private final ArrayList<Airport> vertices;
    private final ArrayGraph<Airport, Route> routes;
    private final LinkedGraph<Airport, Flight> flights;

    public AirNetwork() {
        this.vertices = new ArrayList<>();
        routes = new ArrayGraph<>(this.vertices, false);
        flights = new LinkedGraph<>(this.vertices, true);
    }

    public void addVertex(Airport vertex) {
        this.routes.addVertex(vertex);
        this.flights.addVertex(vertex);
    }

    public ArrayList<Airport> getVertices() {
        return vertices;
    }

    public ArrayGraph<Airport, Route> getRoutes() {
        return routes;
    }

    public LinkedGraph<Airport, Flight> getFlights() {
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
