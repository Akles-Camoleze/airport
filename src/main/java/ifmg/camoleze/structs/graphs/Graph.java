package ifmg.camoleze.structs.graphs;

import ifmg.camoleze.structs.lists.ArrayList;

public interface Graph<K, V, U> {
    void addVertex(Vertex<K> vertex);

    void addEdge(Vertex<K> source, Vertex<K> destination, V value);

    ArrayList<Vertex<K>> getVertices();

    ArrayList<U> getEdges();

    U getEdgesFromVertex(Vertex<K> vertex);

    ArrayList<Vertex<K>> getNeighbors(Vertex<K> vertex);

    void processEdges(EdgeProcessor<K, V> edgeProcessor);

    default void showEdges() {
        EdgeProcessor<K, V> processor = (source, value, destin) ->
                System.out.printf("%s----%s---->%s\n", source, value, destin);
        this.processEdges(processor);
    }
}
