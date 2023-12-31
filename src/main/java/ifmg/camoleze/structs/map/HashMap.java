package ifmg.camoleze.structs.map;

import ifmg.camoleze.structs.lists.ArrayList;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class HashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private Node<K, V>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public HashMap() {
        table = new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    public void put(K key, V value) {
        ensureCapacity();
        int index = getIndex(key);
        Node<K, V> newNode = new Node<>(key, value);
        if (table[index] == null) {
            table[index] = newNode;
        } else {
            Node<K, V> current = table[index];
            while (current.next != null) {
                if (Objects.equals(current.key, key)) {
                    current.value = value;
                    return;
                }
                current = current.next;
            }
            if (Objects.equals(current.key, key)) {
                current.value = value;
            } else {
                current.next = newNode;
            }
        }
        size++;
    }

    public void update(K key, V value, int indexValue) {
        int index = getIndex(key), i = 0;
        Node<K, V> current = table[index];
        while (current.next != null && i != indexValue) {
            current = current.next;
        }
        current.value = value;
    }

    public V get(K key) {
        int index = getIndex(key);
        Node<K, V> current = table[index];
        while (current != null) {
            if (Objects.equals(current.key, key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public ArrayList<K> getKeys() {
        ArrayList<K> keyList = new ArrayList<>();
        for (Node<K, V> node : table) {
            while (node != null) {
                keyList.add(node.key);
                node = node.next;
            }
        }
        return keyList;
    }

    public void forEach(BiConsumer<K, V> action) {
        for (Node<K, V> node : table) {
            while (node != null) {
                action.accept(node.key, node.value);
                node = node.next;
            }
        }
    }

    public HashMap<K, V> filterByValue(Predicate<V> predicate) {
        HashMap<K, V> result = new HashMap<>();

        this.forEach((key, value) -> {
            if (predicate.test(value)) {
                result.put(key, value);
            }
        });

        return result;
    }

    public HashMap<K, V> copy() {
        HashMap<K, V> copyMap = new HashMap<>();

        this.forEach(copyMap::put);

        return copyMap;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (Node<K, V> node : table) {
            while (node != null) {
                if (!first) {
                    sb.append(",\n");
                }
                sb.append(node.key).append("=").append(node.value);
                first = false;
                node = node.next;
            }
        }
        return sb.toString();
    }

    private void ensureCapacity() {
        if ((double) size / table.length > LOAD_FACTOR) {
            resize();
        }
    }

    private void resize() {
        int newCapacity = table.length * 2;
        Node<K, V>[] newTable = new Node[newCapacity];
        for (Node<K, V> node : table) {
            while (node != null) {
                int index = getIndex(node.key, newCapacity);
                Node<K, V> next = node.next;
                node.next = newTable[index];
                newTable[index] = node;
                node = next;
            }
        }
        table = newTable;
    }

    private int getIndex(K key) {
        return getIndex(key, table.length);
    }

    private int getIndex(K key, int capacity) {
        return Objects.hashCode(key) % capacity;
    }

    private static class Node<K, V> {
        private final K key;
        private V value;
        private Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }
}
