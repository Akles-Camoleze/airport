package ifmg.camoleze.structs.graphs;

import ifmg.camoleze.structs.lists.ArrayList;
import ifmg.camoleze.structs.map.HashMap;
//import ifmg.camoleze.structs.queue.PriorityQueue;
import java.util.PriorityQueue;

@SuppressWarnings("unchecked")
public class DijkstraAlgorithm<K, V> {
    private final Graph<K, V, ArrayList<V>> graph;

    public DijkstraAlgorithm(Graph<K, V, ArrayList<V>> graph) {
        this.graph = graph;
    }

    public ArrayList<VertexDistancePair<K>> dijkstra(Vertex<K> start, DijkstraProcessor<V> processor) {
        Object[] vertices = graph.getVertices().toArray();
        PriorityQueue<VertexDistancePair<K>> queue = new PriorityQueue<>();
        ArrayList<VertexDistancePair<K>> distances = new ArrayList<>();

        for (Vertex<K> vertex : graph.getVertices()) {
            if (vertex.equals(start)) {
                distances.add(new VertexDistancePair<>(vertex, 0));
                queue.add(new VertexDistancePair<>(start, 0));
            } else {
                distances.add(new VertexDistancePair<>(vertex, Integer.MAX_VALUE));
            }
        }

        while (!queue.isEmpty()) {
            VertexDistancePair<K> current = queue.poll();
            int index = distances.findIndex(element -> element.equals(current));
            ArrayList<V> edges = graph.getEdgesFromVertex(current.vertex);

            for (int i = 0; i < edges.size(); i++) {
                V edge = edges.get(i);

                if (edge == null) continue;

                int newDistance = distances.get(index).distance + processor.greedyChoice(edge);
                Vertex<K> destin = (Vertex<K>) vertices[i];
                if (newDistance < distances.get(i).distance) {
                    distances.set(i, new VertexDistancePair<>(destin, newDistance));
                    queue.add(new VertexDistancePair<>(destin, newDistance));
                }
            }
        }

        return distances;
    }

    public record VertexDistancePair<K>(Vertex<K> vertex, int distance) implements Comparable<VertexDistancePair<K>> {
        @Override
        public int compareTo(VertexDistancePair<K> other) {
            return Integer.compare(this.distance, other.distance);
        }
    }
}
