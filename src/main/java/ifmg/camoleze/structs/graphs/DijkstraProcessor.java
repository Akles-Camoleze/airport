package ifmg.camoleze.structs.graphs;

@FunctionalInterface
public interface DijkstraProcessor<V> {
    Integer greedyChoice(V currEdge);
}
