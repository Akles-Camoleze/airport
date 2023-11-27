package ifmg.camoleze.structs.queue;

public interface Queue<T> {
    boolean isEmpty();

    void enqueue(T data);

    T dequeue();
}
