package ifmg.camoleze.structs.graphs;

import ifmg.camoleze.requirements.Methods;
import ifmg.camoleze.structs.lists.ArrayList;


public class ArrayGraph<K extends Methods, V extends Methods> implements Graph<K, V, ArrayList<ArrayList<V>>> {
    private final ArrayList<K> vertices;
    private final ArrayList<ArrayList<V>> edges;
    private final boolean targeted;

    public ArrayGraph(boolean targeted) {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.targeted = targeted;
    }

    /**
     * Construtor dedicado a grafos com vertices compartilhados.
     */
    public ArrayGraph(ArrayList<K> vertices, boolean targeted) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
        this.targeted = targeted;
    }

    @Override
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

    @Override
    public void addEdge(K source, K destination, V value) {
        int sourceIndex = vertices.indexOf(source);
        int destinationIndex = vertices.indexOf(destination);

        if (sourceIndex == -1 || destinationIndex == -1) return;

        edges.get(sourceIndex).set(destinationIndex, value);

        if (!targeted) {
            edges.get(destinationIndex).set(sourceIndex, value);
        }
    }

    @Override
    public ArrayList<K> getVertices() {
        return vertices;
    }

    @Override
    public ArrayList<ArrayList<V>> getEdges() {
        return edges;
    }

    @Override
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

    public void dfs(K source, K destination) {

    }

}

