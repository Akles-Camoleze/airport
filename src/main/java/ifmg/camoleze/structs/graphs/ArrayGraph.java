package ifmg.camoleze.structs.graphs;

import ifmg.camoleze.requirements.Methods;
import ifmg.camoleze.structs.lists.ArrayList;


public class ArrayGraph<K extends Methods, V extends Methods> implements Graph<K, V, ArrayList<ArrayList<V>>> {
    private final ArrayList<K> vertices;
    private final ArrayList<ArrayList<V>> edges;

    public ArrayGraph() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    /**
     * Construtor dedicado a grafos com vertices compartilhados.
     */
    public ArrayGraph(ArrayList<K> vertices) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
    }

    public void addVertex(K vertex) {
        if (vertices.indexOf(vertex) == -1) {
            vertices.add(vertex);
        }

        this.edges.forEach(edge -> edge.add(null));

        ArrayList<V> edge = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            edge.add(null);
        }
        edges.add(edge);
    }

    public void addEdge(K source, K destination, V value) {
        int sourceIndex = vertices.indexOf(source);
        int destinationIndex = vertices.indexOf(destination);

        if (sourceIndex == -1 || destinationIndex == -1) return;

        edges.get(sourceIndex).set(destinationIndex, value);
    }

    public ArrayList<K> getVertices() {
        return vertices;
    }

    public ArrayList<ArrayList<V>> getEdges() {
        return edges;
    }

    public void processEdges(EdgeProcessor<K, V> edgeProcessor) {
        for (int i = 0; i < edges.size(); i++) {
            for (int j = 0; j < edges.get(i).size(); j++) {
                if (i != j && edges.get(i).get(j) != null) {
                    K source = vertices.get(i);
                    K destin = vertices.get(j);
                    edgeProcessor.process(source, edges.get(i).get(j), destin);
                }
            }
        }
    }

}
