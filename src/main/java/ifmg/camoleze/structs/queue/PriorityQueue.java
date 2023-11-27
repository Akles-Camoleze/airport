package ifmg.camoleze.structs.queue;

import ifmg.camoleze.structs.lists.ArrayList;
import ifmg.camoleze.structs.lists.List;


public class PriorityQueue<T extends Comparable<T>> implements Queue<T> {
    private final List<T> elements = new ArrayList<>();

    public PriorityQueue() {
    }

    public void enqueue(T element) {
        elements.add(element);
        heapifyUp();
    }

    public T dequeue() {
        if (isEmpty()) {
            return null;
        }

        T top = elements.get(0);
        int lastIndex = elements.size() - 1;
        elements.set(0, elements.get(lastIndex));
        elements.remove(lastIndex);
        heapifyDown();
        return top;
    }

    public boolean isEmpty() {
        return elements.size() == 0;
    }

    private void heapifyUp() {
        int currentIndex = elements.size() - 1;
        while (currentIndex > 0) {
            int parentIndex = (currentIndex - 1) / 2;
            if (elements.get(currentIndex).compareTo(elements.get(parentIndex)) > 0) {
                swap(currentIndex, parentIndex);
                currentIndex = parentIndex;
            } else {
                break;
            }
        }
    }

    private void heapifyDown() {
        int currentIndex = 0;
        int lastIndex = elements.size() - 1;

        while (true) {
            int leftChildIndex = 2 * currentIndex + 1;
            int rightChildIndex = 2 * currentIndex + 2;
            int maxIndex = currentIndex;

            if (leftChildIndex <= lastIndex && elements.get(leftChildIndex).compareTo(elements.get(maxIndex)) > 0) {
                maxIndex = leftChildIndex;
            }

            if (rightChildIndex <= lastIndex && elements.get(rightChildIndex).compareTo(elements.get(maxIndex)) > 0) {
                maxIndex = rightChildIndex;
            }

            if (maxIndex != currentIndex) {
                swap(currentIndex, maxIndex);
                currentIndex = maxIndex;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        T temp = elements.get(i);
        elements.set(i, elements.get(j));
        elements.set(j, temp);
    }
}
