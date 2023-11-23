package ifmg.camoleze.structs.graphs;

@FunctionalInterface
public interface EdgeProcessor<K, V> {
    void process(K source, V edge, K destin);
}
