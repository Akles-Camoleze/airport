package ifmg.camoleze.structs.graphs;

import ifmg.camoleze.structs.lists.ArrayList;
import ifmg.camoleze.structs.map.HashMap;
import ifmg.camoleze.structs.queue.PriorityQueue;

import java.math.BigDecimal;

public class DijkstraAlgorithm<K, V> {
    private final Graph<K, V, ArrayList<V>> graph;

    public DijkstraAlgorithm(Graph<K, V, ArrayList<V>> graph) {
        this.graph = graph;
    }

    public HashMap<Vertex<K>, BigDecimal> dijkstra(Vertex<K> start, DijkstraProcessor<V> processor) {
        ArrayList<Vertex<K>> vertices = graph.getVertices();
        PriorityQueue<VertexDistancePair<K>> queue = new PriorityQueue<>();
        HashMap<Vertex<K>, BigDecimal> distances = new HashMap<>();

        for (Vertex<K> vertex : graph.getVertices()) {
            distances.put(vertex, BigDecimal.valueOf(Double.MAX_VALUE));
        }

        distances.put(start, BigDecimal.ZERO);
        queue.enqueue(new VertexDistancePair<>(start, BigDecimal.ZERO));

        while (!queue.isEmpty()) {
            VertexDistancePair<K> current = queue.dequeue();
            ArrayList<V> edges = graph.getEdgesFromVertex(current.vertex);

            for (int i = 0; i < edges.size(); i++) {
                V edge = edges.get(i);

                if (edge == null) continue;

                BigDecimal newDistance = distances.get(current.vertex).add(processor.greedyChoice(edge));
                Vertex<K> destin = vertices.get(i);
                if (newDistance.compareTo(distances.get(destin)) < 0) {
                    distances.put(destin, newDistance);
                    queue.enqueue(new VertexDistancePair<>(destin, newDistance));
                }
            }
        }

        return distances;
    }

    private record VertexDistancePair<K>(Vertex<K> vertex, BigDecimal distance) implements Comparable<VertexDistancePair<K>> {
        @Override
        public int compareTo(VertexDistancePair<K> other) {
            return this.distance.compareTo(other.distance);
        }
    }
}
