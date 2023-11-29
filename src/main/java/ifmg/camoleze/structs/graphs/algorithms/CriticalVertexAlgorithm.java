package ifmg.camoleze.structs.graphs.algorithms;

import ifmg.camoleze.structs.graphs.Graph;
import ifmg.camoleze.structs.graphs.Vertex;
import ifmg.camoleze.structs.lists.ArrayList;
import ifmg.camoleze.structs.lists.List;
import ifmg.camoleze.structs.map.HashMap;
import ifmg.camoleze.structs.set.HashSet;

public class CriticalVertexAlgorithm<K, V> {
    private final DijkstraAlgorithm<K, V> dijkstraAlgorithm;
    private final Graph<K, V, ArrayList<V>> graph;

    public CriticalVertexAlgorithm(DijkstraAlgorithm<K, V> dijkstraAlgorithm, Graph<K, V, ArrayList<V>> graph) {
        this.dijkstraAlgorithm = dijkstraAlgorithm;
        this.graph = graph;
    }

    public ArrayList<VertexResult<K>> findCriticalAirports(Vertex<K> start) {
        HashMap<Vertex<K>, DijkstraAlgorithm.DijkstraResult<K>> dijkstraResults = dijkstraAlgorithm.dijkstra(start);
        ArrayList<VertexResult<K>> results = new ArrayList<>();

        dijkstraResults.forEach((kVertex, kDijkstraResult) -> {
            HashSet<Vertex<K>> criticalVertex = findCriticalVerticesInPath(kDijkstraResult.path());
            results.add(new VertexResult<>(kDijkstraResult, criticalVertex));
        });

        return results;
    }

    public VertexResult<K> findCriticalAirports(Vertex<K> start, Vertex<K> destination) {
        HashMap<Vertex<K>, DijkstraAlgorithm.DijkstraResult<K>> dijkstraResults = dijkstraAlgorithm.dijkstra(start, destination);
        DijkstraAlgorithm.DijkstraResult<K> destinationResult = dijkstraResults.get(destination);

        HashSet<Vertex<K>> criticalAirports = findCriticalVerticesInPath(destinationResult.path());

        return new VertexResult<>(destinationResult, criticalAirports);
    }

    private HashSet<Vertex<K>> findCriticalVerticesInPath(List<Vertex<K>> path) {
        HashSet<Vertex<K>> criticalVertices = new HashSet<>();

        for (int i = 0; i < path.size() - 1; i++) {
            Vertex<K> currentVertex = path.get(i);
            Vertex<K> nextVertex = path.get(i + 1);

            if (this.graph.getEdgesToVertex(nextVertex).size() == 1) {
                criticalVertices.add(currentVertex);
            }
        }

        return criticalVertices;
    }

    public record VertexResult<K>(DijkstraAlgorithm.DijkstraResult<K> dijkstraResult, HashSet<Vertex<K>> criticalVertex) {
        @Override
        public String toString() {
            return "\r[\n\t" + dijkstraResult + "\n\t(Aeroportos Criticos: " + criticalVertex + ")\n]";
        }
    }
}

