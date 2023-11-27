package ifmg.camoleze.structs.graphs;

import ifmg.camoleze.structs.lists.ArrayList;
import ifmg.camoleze.structs.map.HashMap;
import ifmg.camoleze.structs.queue.PriorityQueue;
import java.math.BigDecimal;

public class DijkstraAlgorithm<K, V> {
    private final Graph<K, V, ArrayList<V>> graph;
    private final ArrayList<Vertex<K>> vertices;
    private final PriorityQueue<VertexDistancePair<K>> queue;
    private final HashMap<Vertex<K>, DijkstraResult<K>> results;
    private final DijkstraProcessor<V> processor;

    public DijkstraAlgorithm(Graph<K, V, ArrayList<V>> graph, DijkstraProcessor<V> dijkstraProcessor) {
        this.graph = graph;
        this.vertices = graph.getVertices();
        this.queue = new PriorityQueue<>();
        this.results = new HashMap<>();
        this.processor = dijkstraProcessor;
    }

    public HashMap<Vertex<K>, DijkstraResult<K>> dijkstra(Vertex<K> start) {
        return dijkstra(start, null);
    }

    public HashMap<Vertex<K>, DijkstraResult<K>> dijkstra(Vertex<K> start, Vertex<K> destination) {
        if (start == null) {
            throw new RuntimeException("Vertice inválido");
        }

        initializeResults(start);
        results.get(start).path().add(start);
        queue.enqueue(new VertexDistancePair<>(start, BigDecimal.ZERO));

        while (!queue.isEmpty()) {
            VertexDistancePair<K> current = queue.dequeue();
            relaxation(current, destination);
        }

        if (destination != null) {
            HashMap<Vertex<K>, DijkstraResult<K>> destinationResult = new HashMap<>();
            destinationResult.put(destination, results.get(destination));
            return destinationResult;
        }

        return results;
    }

    private void initializeResults(Vertex<K> start) {
        for (Vertex<K> vertex : vertices) {
            BigDecimal initialDistance = vertex.equals(start) ? BigDecimal.ZERO : BigDecimal.valueOf(Double.MAX_VALUE);
            results.put(vertex, new DijkstraResult<>(initialDistance, new ArrayList<>()));
        }
    }

    private void relaxation(VertexDistancePair<K> current, Vertex<K> destination) {
        ArrayList<V> edges = graph.getEdgesFromVertex(current.vertex);

        for (int i = 0; i < edges.size(); i++) {
            V edge = edges.get(i);

            if (edge == null) continue;

            BigDecimal newDistance = results.get(current.vertex).distance.add(processor.greedyChoice(edge));
            Vertex<K> destin = vertices.get(i);

            if (newDistance.compareTo(results.get(destin).distance()) < 0) {
                ArrayList<Vertex<K>> newPath = results.get(current.vertex).path().copy();
                newPath.add(destin);
                results.put(destin, new DijkstraResult<>(newDistance, newPath));
                queue.enqueue(new VertexDistancePair<>(destin, newDistance));

                if (destin.equals(destination)) break;

            }
        }
    }

    private record VertexDistancePair<K>(Vertex<K> vertex, BigDecimal distance) implements Comparable<VertexDistancePair<K>> {
        @Override
        public int compareTo(VertexDistancePair<K> other) {
            return this.distance.compareTo(other.distance);
        }
    }

    public record DijkstraResult<K>(BigDecimal distance, ArrayList<Vertex<K>> path) {
        @Override
        public String toString() {
            StringBuilder buffer = new StringBuilder();
            buffer.append('(').append(distance).append("km, ");

            for (Vertex<K> vertex : path) {
                buffer.append(vertex.getData()).append("--->");
            }

            buffer.delete(buffer.length() - 4, buffer.length());

            return buffer.append(')').toString();
        }
    }
}
