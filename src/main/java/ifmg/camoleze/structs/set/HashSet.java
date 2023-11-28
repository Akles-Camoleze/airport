package ifmg.camoleze.structs.set;

import ifmg.camoleze.structs.lists.ArrayList;

public class HashSet<E> {
    private final ArrayList<E>[] buckets;
    private static final int DEFAULT_CAPACITY = 16;

    public HashSet() {
        this(DEFAULT_CAPACITY);
    }

    public HashSet(int capacity) {
        this.buckets = new ArrayList[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new ArrayList<>();
        }
    }

    private int hash(E element) {
        return Math.abs(element.hashCode() % buckets.length);
    }

    public void add(E element) {
        int index = hash(element);
        ArrayList<E> bucket = buckets[index];
        if (!bucket.contains(element)) {
            bucket.add(element);
        }
    }

    public boolean contains(E element) {
        int index = hash(element);
        return buckets[index].contains(element);
    }

    public void remove(E element) {
        int index = hash(element);
        buckets[index].remove(element);
    }

    public ArrayList<E> toArrayList() {
        ArrayList<E> result = new ArrayList<>();
        for (ArrayList<E> bucket : buckets) {
            for (E element : bucket) {
                result.add(element);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");

        boolean firstBucket = true;

        for (ArrayList<E> bucket : buckets) {
            for (E element : bucket) {
                if (!firstBucket) {
                    sb.append(", ");
                }
                sb.append(element);
                firstBucket = false;
            }
        }

        sb.append(")");

        return sb.toString();
    }
}
