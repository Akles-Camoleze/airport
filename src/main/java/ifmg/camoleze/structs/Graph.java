package ifmg.camoleze.structs;

import ifmg.camoleze.entities.RequiredAttributes;

public class Graph<K, V extends RequiredAttributes> {
    private final ArrayList<K> vertices;
    private final ArrayList<ArrayList<V>> edges;

    public Graph() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    /**
     * Construtor dedicado a grafos com vertices compartilhados.
     * */
    public Graph(ArrayList<K> vertices) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
    }

    public void addVertex(K vertex) {
        if (vertices.indexOf(vertex) != -1) return;

        vertices.add(vertex);

        for (ArrayList<V> edge : edges) {
            edge.add(null);
        }

        ArrayList<V> edge = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            edge.add(null);
        }
        edges.add(edge);
    }

    public void addEdge(K source, K destination, V value) {
        int sourceIndex = vertices.indexOf(source);
        int destinationIndex = vertices.indexOf(destination);

        if (sourceIndex == -1 || destinationIndex == -1) {
            throw new IllegalArgumentException("Os vértices devem existir antes de adicionar uma aresta");
        }

        edges.get(sourceIndex).set(destinationIndex, value);
    }
}
