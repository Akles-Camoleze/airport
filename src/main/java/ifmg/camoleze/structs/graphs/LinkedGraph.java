package ifmg.camoleze.structs.graphs;

import ifmg.camoleze.requirements.RequiredMethods;
import ifmg.camoleze.structs.lists.ArrayList;
import ifmg.camoleze.structs.map.HashMap;

public class LinkedGraph<K extends RequiredMethods, V extends RequiredMethods> implements Graph<K, V> {
    private final ArrayList<K> vertices;
    private final HashMap<K, HashMap<K, ArrayList<V>>> edges;

    public LinkedGraph() {
        this.vertices = new ArrayList<>();
        this.edges = new HashMap<>();
    }

    /**
     * Construtor dedicado a grafos com vértices compartilhados.
     */
    public LinkedGraph(ArrayList<K> vertices) {
        this.vertices = vertices;
        this.edges = new HashMap<>();
    }

    public void addVertex(K vertex) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
        }
        edges.put(vertex, new HashMap<>());
    }

    public void addEdge(K source, K destination, V value) {
        HashMap<K, ArrayList<V>> sourceEdges = edges.get(source);
        if (sourceEdges == null) {
            sourceEdges = new HashMap<>();
            edges.put(source, sourceEdges);
        }

        ArrayList<V> values = sourceEdges.get(destination);
        if (values == null) {
            values = new ArrayList<>();
            sourceEdges.put(destination, values);
        }

        values.add(value);
    }

    public ArrayList<K> getVertices() {
        return vertices;
    }

    public HashMap<K, HashMap<K, ArrayList<V>>> getEdges() {
        return edges;
    }

    public void processEdges(EdgeProcessor<K, V> edgeProcessor) {
        for (K source : edges.getKeys()) {
            HashMap<K, ArrayList<V>> sourceEdges = edges.get(source);
            for (K destination : sourceEdges.getKeys()) {
                ArrayList<V> values = sourceEdges.get(destination);
                for (V value : values) {
                    edgeProcessor.process(source, value, destination);
                }
            }
        }
    }

}
