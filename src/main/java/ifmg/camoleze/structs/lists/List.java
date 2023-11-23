package ifmg.camoleze.structs.lists;

public interface List<T> {
    void add(T element);

    void add(int index, T element);

    T get(int index);

    T set(int index, T element);

    boolean contains(T element);

    int indexOf(T element);

    boolean remove(T element);

    T remove(int index);

    int size();

    Object[] toArray();
}