package ifmg.camoleze.structs.graphs;

import ifmg.camoleze.structs.lists.ArrayList;
import ifmg.camoleze.structs.map.HashMap;
import java.util.function.Predicate;

public class LinkedGraph<K, V> implements Graph<K, V, HashMap<Vertex<K>, ArrayList<V>>> {
    private final ArrayList<Vertex<K>> vertices;
    private final ArrayList<HashMap<Vertex<K>, ArrayList<V>>> edges;
    private final boolean targeted;

    public LinkedGraph(boolean targeted) {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.targeted = targeted;
    }

    /**
     * Construtor dedicado a grafos com vértices compartilhados.
     */
    public LinkedGraph(ArrayList<Vertex<K>> vertices, boolean targeted) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
        this.targeted = targeted;
    }

    @Override
    public void addVertex(Vertex<K> vertex) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
        }
        edges.add(new HashMap<>());
    }

    @Override
    public void addEdge(Vertex<K> source, Vertex<K> destination, V value) {
        addEdgeHelper(source, destination, value);

        source.setExitDegree(source.getExitDegree() + 1);
        destination.setEntryDegree(destination.getEntryDegree() + 1);

        if (!targeted) {
            addEdgeHelper(destination, source, value);
            destination.setExitDegree(destination.getExitDegree() + 1);
            source.setEntryDegree(source.getEntryDegree() + 1);
        }
    }

    @Override
    public ArrayList<Vertex<K>> getVertices() {
        return vertices;
    }

    @Override
    public ArrayList<HashMap<Vertex<K>, ArrayList<V>>> getEdges() {
        return edges;
    }

    @Override
    public HashMap<Vertex<K>, ArrayList<V>> getEdgesFromVertex(Vertex<K> vertex) {
        int index = vertices.indexOf(vertex);

        if (index == -1) {
            throw new IndexOutOfBoundsException("Indice fora dos limites");
        }

        return edges.get(vertices.indexOf(vertex));
    }

    @Override
    public ArrayList<Vertex<K>> getNeighbors(Vertex<K> vertex) {
        ArrayList<Vertex<K>> neighbors = new ArrayList<>();
        int vertexIndex = vertices.indexOf(vertex);

        if (vertexIndex != -1) {
            HashMap<Vertex<K>, ArrayList<V>> vertexEdges = edges.get(vertexIndex);

            if (vertexEdges != null) {
                vertexEdges.forEach((neighbor, values) -> {
                    if (!neighbors.contains(neighbor)) {
                        neighbors.add(neighbor);
                    }
                });
            }
        }

        return neighbors;
    }

    public HashMap<Vertex<K>, ArrayList<V>> getEdgesFromVertex(Vertex<K> vertex, Predicate<ArrayList<V>> predicate) {
        return this.getEdgesFromVertex(vertex).copy().filterByValue(predicate);
    }

    public void processEdges(EdgeProcessor<K, V> edgeProcessor) {
        String start = "=".repeat(40);
        String end = start.repeat(2) + "=".repeat(14);
        for (int i = 0; i < edges.size(); i++) {
            K source = vertices.get(i).getData();
            HashMap<Vertex<K>, ArrayList<V>> sourceEdges = edges.get(i);

            sourceEdges.forEach( (destin, values) -> {
                System.out.printf("\n%s[%s para %s]%s\n\n", start, source, destin.getData(), start);
                for (V value : values) {
                    edgeProcessor.process(source, value, destin.getData());
                }
                System.out.printf("\n%s\n", end);
            });
        }
    }


    private void addEdgeHelper(Vertex<K> source, Vertex<K> destination, V value) {
        HashMap<Vertex<K>, ArrayList<V>> sourceEdges = edges.get(vertices.indexOf(source));
        if (sourceEdges == null) {
            sourceEdges = new HashMap<>();
            edges.add(sourceEdges);
        }

        ArrayList<V> values = sourceEdges.get(destination);
        if (values == null) {
            values = new ArrayList<>();
            sourceEdges.put(destination, values);
        }

        values.add(value);
    }
}