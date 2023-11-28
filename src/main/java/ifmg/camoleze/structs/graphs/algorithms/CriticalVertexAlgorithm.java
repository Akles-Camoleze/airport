package ifmg.camoleze.structs.graphs.algorithms;

import ifmg.camoleze.structs.graphs.Vertex;
import ifmg.camoleze.structs.map.HashMap;
import ifmg.camoleze.structs.set.HashSet;

public class CriticalVertexAlgorithm<K, V> {
    private final DijkstraAlgorithm<K, V> dijkstraAlgorithm;

    public CriticalVertexAlgorithm(DijkstraAlgorithm<K, V> dijkstraAlgorithm) {
        this.dijkstraAlgorithm = dijkstraAlgorithm;
    }

    public AirportResult<K> findCriticalAirports(Vertex<K> start, Vertex<K> destination) {
        HashMap<Vertex<K>, DijkstraAlgorithm.DijkstraResult<K>> dijkstraResults = dijkstraAlgorithm.dijkstra(start, destination);

        DijkstraAlgorithm.DijkstraResult<K> destinationResult = dijkstraResults.get(destination);

        HashSet<Vertex<K>> criticalAirports = new HashSet<>();

        for (Vertex<K> vertex : destinationResult.path()) {
            if (vertex.getExitDegree() == 1) {
                criticalAirports.add(vertex);
            }
        }

        return new AirportResult<>(destinationResult, criticalAirports);
    }

    public record AirportResult<K>(DijkstraAlgorithm.DijkstraResult<K> dijkstraResult, HashSet<Vertex<K>> criticalAirports) {
        @Override
        public String toString() {
            return "Distance: " + dijkstraResult.distance() + " Path: " + dijkstraResult.path() +
                    " Critical Airports: " + criticalAirports;
        }
    }
}

