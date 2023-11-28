package ifmg.camoleze.structs.graphs.algorithms;

import ifmg.camoleze.structs.graphs.Vertex;
import ifmg.camoleze.structs.lists.ArrayList;
import ifmg.camoleze.structs.map.HashMap;
import ifmg.camoleze.structs.set.HashSet;

public class CriticalVertexAlgorithm<K, V> {
    private final DijkstraAlgorithm<K, V> dijkstraAlgorithm;

    public CriticalVertexAlgorithm(DijkstraAlgorithm<K, V> dijkstraAlgorithm) {
        this.dijkstraAlgorithm = dijkstraAlgorithm;
    }

    public ArrayList<VertexResult<K>> findCriticalAirports(Vertex<K> start) {
        HashMap<Vertex<K>, DijkstraAlgorithm.DijkstraResult<K>> dijkstraResults = dijkstraAlgorithm.dijkstra(start);

        ArrayList<VertexResult<K>> results = new ArrayList<>();

        dijkstraResults.forEach((kVertex, kDijkstraResult) -> {
            HashSet<Vertex<K>> criticalVertex = new HashSet<>();
            for (Vertex<K> vertex : kDijkstraResult.path()) {
                if (vertex.getExitDegree() == 1) {
                    criticalVertex.add(vertex);
                }
                results.add(new VertexResult<>(kDijkstraResult, criticalVertex));
            }
        });

        return results;
    }

    public VertexResult<K> findCriticalAirports(Vertex<K> start, Vertex<K> destination) {
        HashMap<Vertex<K>, DijkstraAlgorithm.DijkstraResult<K>> dijkstraResults;
        dijkstraResults = dijkstraAlgorithm.dijkstra(start, destination);
        DijkstraAlgorithm.DijkstraResult<K> destinationResult = dijkstraResults.get(destination);

        HashSet<Vertex<K>> criticalAirports = new HashSet<>();

        for (Vertex<K> vertex : destinationResult.path()) {
            if (vertex.getExitDegree() == 1) {
                criticalAirports.add(vertex);
            }
        }

        return new VertexResult<>(destinationResult, criticalAirports);
    }

    public record VertexResult<K>(DijkstraAlgorithm.DijkstraResult<K> dijkstraResult, HashSet<Vertex<K>> criticalVertex) {
        @Override
        public String toString() {
            return "\r[\n\t" + dijkstraResult + "\n\t(Aeroportos Criticos: " + criticalVertex + ")\n]";
        }
    }
}

