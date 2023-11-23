package ifmg.camoleze.structs.graphs;

import ifmg.camoleze.requirements.RequiredMethods;
import ifmg.camoleze.structs.lists.ArrayList;

public interface Graph<K extends RequiredMethods, V extends RequiredMethods> {
    void addVertex(K vertex);

    void addEdge(K source, K destination, V value);

    ArrayList<K> getVertices();

    Object getEdges();

    void processEdges(EdgeProcessor<K, V> edgeProcessor);

    default void showEdges() {
        EdgeProcessor<K, V> processor = (source, value, destin) -> {
            String abbrSource = source.showInGraph();
            String abbrDestin = destin.showInGraph();
            String valueToShow = value.showInGraph();
            System.out.printf("%s-->%s-->%s\n", abbrSource, valueToShow, abbrDestin);
        };
        this.processEdges(processor);
    }
}
