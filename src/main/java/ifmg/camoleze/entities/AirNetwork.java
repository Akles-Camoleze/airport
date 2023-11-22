package ifmg.camoleze.entities;

import ifmg.camoleze.structs.ArrayList;
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
