package ifmg.camoleze.structs.graphs;

import ifmg.camoleze.requirements.Methods;
import ifmg.camoleze.structs.lists.ArrayList;
import ifmg.camoleze.structs.map.HashMap;

public class LinkedGraph<K extends Methods, V extends Methods> implements Graph<K, V, HashMap<K, HashMap<K, ArrayList<V>>>> {
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
        String start = "=".repeat(40);
        String end = start.repeat(2) + "=".repeat(14);
        edges.forEach((source, edges) -> edges.forEach((destin, values) -> {
            System.out.printf("\n%s[%s para %s]%s\n\n", start, source.showInGraph(), destin.showInGraph(), start);
            for (V value : values) {
                edgeProcessor.process(source, value, destin);
            }
            System.out.printf("\n%s\n", end);
        }));
    }

}