package ifmg.camoleze.structs.graphs.algorithms;

import ifmg.camoleze.structs.graphs.Graph;
import ifmg.camoleze.structs.graphs.Vertex;
import ifmg.camoleze.structs.lists.ArrayList;

public class HamiltonianCircuit<K, V, U> {

    private final Graph<K, V, U> graph;
    private final int size;
    private Vertex<K> startVertex;
    private final ArrayList<Vertex<K>> path;

    public HamiltonianCircuit(Graph<K, V, U> graph) {
        this.graph = graph;
        this.size = graph.getVertices().size();
        this.path = new ArrayList<>();
    }

    public ArrayList<Vertex<K>> findHamiltonianCircuit(Vertex<K> startVertex) {
        this.startVertex = startVertex;
        path.add(startVertex);

        if (!hamiltonianCircuitUtil(1)) {
            throw new RuntimeException("Não existe um circuito Hamiltoniano partindo do vértice selecionado.");
        }

        return path;
    }

    private boolean hamiltonianCircuitUtil(int pos) {
        if (pos == size) {
            Vertex<K> lastVertex = path.get(pos - 1);
            U edges = graph.getEdgesFromVertex(lastVertex);
            int startIndex = graph.getVertices().indexOf(startVertex);

            if (edges != null && graph.getEdges().get(startIndex) != null && edges.equals(graph.getEdges().get(startIndex))) {
                path.add(startVertex);
                return true;
            }
            return false;
        }

        ArrayList<Vertex<K>> neighbors = graph.getNeighbors(path.get(pos - 1));

        for (Vertex<K> neighbor : neighbors) {
            if (!path.contains(neighbor)) {
                path.add(neighbor);

                if (hamiltonianCircuitUtil(pos + 1)) {
                    return true;
                }

                path.remove(path.size() - 1);
            }
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Vertex<K> vertex : path) {
            result.append(vertex).append("--->");
        }
        return result.delete(result.length() - 4, result.length()).toString();
    }
}
