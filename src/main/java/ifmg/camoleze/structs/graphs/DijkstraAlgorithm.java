package ifmg.camoleze.structs.graphs;

import ifmg.camoleze.structs.lists.ArrayList;
import ifmg.camoleze.structs.map.HashMap;
import ifmg.camoleze.structs.queue.PriorityQueue;

public class DijkstraAlgorithm<K, V> {
    private final Graph<K, V, ArrayList<V>> graph;

    public DijkstraAlgorithm(Graph<K, V, ArrayList<V>> graph) {
        this.graph = graph;
    }

    public HashMap<Vertex<K>, Integer> dijkstra(Vertex<K> start, DijkstraProcessor<V> processor) {
        ArrayList<Vertex<K>> vertices = graph.getVertices();
        PriorityQueue<VertexDistancePair<K>> queue = new PriorityQueue<>();
        HashMap<Vertex<K>, Integer> distances = new HashMap<>();

        for (Vertex<K> vertex : graph.getVertices()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }

        distances.put(start, 0);
        queue.enqueue(new VertexDistancePair<>(start, 0));

        while (!queue.isEmpty()) {
            VertexDistancePair<K> current = queue.dequeue();
            ArrayList<V> edges = graph.getEdgesFromVertex(current.vertex);

            for (int i = 0; i < edges.size(); i++) {
                V edge = edges.get(i);

                if (edge == null) continue;

                int newDistance = distances.get(current.vertex) + processor.greedyChoice(edge);
                Vertex<K> destin = vertices.get(i);
                if (newDistance < distances.get(destin)) {
                    distances.put(destin, newDistance);
                    queue.enqueue(new VertexDistancePair<>(destin, newDistance));
                }
            }
        }

        return distances;
    }

    private record VertexDistancePair<K>(Vertex<K> vertex, int distance) implements Comparable<VertexDistancePair<K>> {
        @Override
        public int compareTo(VertexDistancePair<K> other) {
            return Integer.compare(this.distance, other.distance);
        }
    }
}
