package ifmg.camoleze.structs.graphs;

import ifmg.camoleze.requirements.RequiredAttributes;
import ifmg.camoleze.requirements.RequiredMethods;
import ifmg.camoleze.structs.lists.ArrayList;
import ifmg.camoleze.structs.lists.CollectionList;
import ifmg.camoleze.structs.lists.List;


@SuppressWarnings("unchecked")
public class Graph<K extends RequiredMethods, V extends RequiredMethods> {
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

    public void addVertex(K vertex, V collection) {
        if (vertices.indexOf(vertex) == -1) {
            vertices.add(vertex);
        }

        for (ArrayList<V> edge : this.edges) {
            edge.add((V) CollectionList.newInstance());
        }

        ArrayList<V> edge = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            edge.add((V) CollectionList.newInstance());
        }
        edges.add(edge);
    }

    public void addEdge(K source, K destination, V value) {
        int sourceIndex = vertices.indexOf(source);
        int destinationIndex = vertices.indexOf(destination);

        if (sourceIndex == -1 || destinationIndex == -1) return;

        edges.get(sourceIndex).set(destinationIndex, value);
    }

    public void addEdge(K source, K destination, Object value) {
        int sourceIndex = vertices.indexOf(source);
        int destinationIndex = vertices.indexOf(destination);

        if (sourceIndex == -1 || destinationIndex == -1) return;

        ArrayList<V> edge = edges.get(sourceIndex);
        if (edge.get(destinationIndex) instanceof CollectionList<?>) {
            ((CollectionList<V>) edge.get(destinationIndex)).add((V) value);
        }
    }

    public ArrayList<K> getVertices() {
        return vertices;
    }

    public ArrayList<ArrayList<V>> getEdges() {
        return edges;
    }

    private void processEdges(EdgeProcessor<K, V> edgeProcessor) {
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

    public void showEdges() {
        EdgeProcessor<K, V> processor = (source, value, destin) -> {
            String abbrSource = source.showInGraph();
            String abbrDestin = destin.showInGraph();
            String valueToShow = value.showInGraph();
            System.out.printf("%s-->%s-->%s\n", abbrSource, valueToShow, abbrDestin);
        };
        this.processEdges(processor);
    }

}

