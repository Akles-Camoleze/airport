package ifmg.camoleze.structs.queue;

import ifmg.camoleze.structs.lists.ArrayList;
import ifmg.camoleze.structs.lists.List;


public class PriorityQueue<T extends Comparable<T>> {
    private final List<T> elements = new ArrayList<>();

    public PriorityQueue() {
    }

    public void add(T element) {
        elements.add(element);
        mergeSort(0, elements.size() - 1);
    }

    public T poll() {
        if (isEmpty()) {
            return null;
        }
        return elements.remove(0);
    }

    public boolean isEmpty() {
        return elements.size() == 0;
    }

    private void mergeSort(int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSort(left, mid);
            mergeSort(mid + 1, right);

            merge(left, mid, right);
        }
    }

    private void merge(int left, int mid, int right) {
        List<T> temp = new ArrayList<>();
        int i = left;
        int j = mid + 1;

        while (i <= mid && j <= right) {
            if (elements.get(i).compareTo(elements.get(j)) < 0) {
                temp.add(elements.get(i));
                i++;
            } else {
                temp.add(elements.get(j));
                j++;
            }
        }

        while (i <= mid) {
            temp.add(elements.get(i));
            i++;
        }

        while (j <= right) {
            temp.add(elements.get(j));
            j++;
        }

        for (int k = 0; k < temp.size(); k++) {
            elements.set(left + k, temp.get(k));
        }
    }
}
