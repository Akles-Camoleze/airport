package ifmg.camoleze.structs;

@FunctionalInterface
public interface EdgeProcessor<K, V> {
    void process(K source, V edge, K destin);
}
