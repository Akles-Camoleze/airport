package ifmg.camoleze.structs.graphs;

import ifmg.camoleze.requirements.Methods;
import ifmg.camoleze.structs.lists.ArrayList;

public interface Graph<K extends Methods,V extends Methods, U> {
    void addVertex(Vertex<K> vertex);

    void addEdge(Vertex<K> source, Vertex<K> destination, V value);

    ArrayList<Vertex<K>> getVertices();

    U getEdges();

    void processEdges(EdgeProcessor<K, V> edgeProcessor);

    default void showEdges() {
        EdgeProcessor<K, V> processor = (source, value, destin) -> {
            String abbrSource = source.toString();
            String abbrDestin = destin.toString();
            String valueToShow = value.toString();
            System.out.printf("%s-->%s-->%s\n", abbrSource, valueToShow, abbrDestin);
        };
        this.processEdges(processor);
    }
}
