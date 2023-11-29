package ifmg.camoleze.structs.lists;

import java.util.function.Predicate;

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

    T find(Predicate<T> condition);

    Object filter(Predicate<T> condition);

    int findIndex(Predicate<T> condition);

    Object[] toArray();
}