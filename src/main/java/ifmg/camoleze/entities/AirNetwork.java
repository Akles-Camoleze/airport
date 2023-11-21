package ifmg.camoleze.entities;

import ifmg.camoleze.structs.ArrayList;
import ifmg.camoleze.structs.Graph;

public class AirNetwork {
    private Graph<Airport, Route> routes;
    private Graph<Airport, Flight> flights;

    AirNetwork() {
        ArrayList<Airport> sharedVertices = new ArrayList<>();
        routes = new Graph<>(sharedVertices);
        flights = new Graph<>(sharedVertices);
    }
}
