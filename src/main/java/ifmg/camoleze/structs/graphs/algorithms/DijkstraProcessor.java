package ifmg.camoleze.structs.graphs.algorithms;

import java.math.BigDecimal;

@FunctionalInterface
public interface DijkstraProcessor<V> {
    BigDecimal greedyChoice(V currEdge);
}
