package sorts; // Ensure this matches your project structure

import java.util.Comparator;
import java.util.Arrays;

/**
 * Sorting utilities.
 * 
 * @author NJobse &amp; CRacicot
 */
public class Sort {
    // ----------------- BubbleSort -----------------
    public static <T extends Comparable<T>> void bubbleSort(T[] arr) {
        int n = arr.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].compareTo(arr[j + 1]) > 0) {
                    // Swap elements
                    swap(arr, j, j + 1);
                    swapped = true;
                }
            }
            // If no swaps were made, array is already sorted
            if (!swapped)
                break;
        }
    }

    /**
     * Sorts an array using bubble sort with a custom Comparator.
     *
     * @param arr array to be sorted
     * @param c   Comparator for custom sorting logic
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> c) {
        int n = arr.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (c.compare(arr[j], arr[j + 1]) > 0) {
                    swap(arr, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped)
                break;
        }
    }

    // ----------------- InsertionSort -----------------
    public static <T extends Comparable<T>> void insertionSort(T[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            T key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j].compareTo(key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    /**
     * Sorts an array using insertion sort with a custom Comparator.
     *
     * @param arr array to be sorted
     * @param c   Comparator for custom sorting logic
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> c) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            T key = arr[i];
            int j = i - 1;
            while (j >= 0 && c.compare(arr[j], key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    // ----------------- SelectionSort -----------------
    public static <T extends Comparable<T>> void selectionSort(T[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j].compareTo(arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            swap(arr, i, minIndex);
        }
    }

    /**
     * Sorts an array using selection sort with a custom Comparator.
     *
     * @param arr array to be sorted
     * @param c   Comparator for custom sorting logic
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> c) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (c.compare(arr[j], arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            swap(arr, i, minIndex);
        }
    }

    // ----------------- MergeSort -----------------
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || arr.length < 2)
            return;
        mergeSort(arr, 0, arr.length - 1, comparator);
    }

    private static <T> void mergeSort(T[] arr, int l, int r, Comparator<T> comparator) {
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSort(arr, l, m, comparator);
            mergeSort(arr, m + 1, r, comparator);
            merge(arr, l, m, r, comparator);
        }
    }

    private static <T> void merge(T[] arr, int l, int m, int r, Comparator<T> comparator) {
        T[] L = Arrays.copyOfRange(arr, l, m + 1);
        T[] R = Arrays.copyOfRange(arr, m + 1, r + 1);

        int i = 0, j = 0, k = l;
        while (i < L.length && j < R.length) {
            if (comparator.compare(L[i], R[j]) <= 0) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }
        while (i < L.length)
            arr[k++] = L[i++];
        while (j < R.length)
            arr[k++] = R[j++];
    }

    // ----------------- QuickSort -----------------
    public static <T extends Comparable<T>> void quickSort(T[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static <T extends Comparable<T>> void quickSort(T[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static <T extends Comparable<T>> int partition(T[] arr, int low, int high) {
        T pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j].compareTo(pivot) < 0) { // Use compareTo directly
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    /**
     * Sorts an array using QuickSort with a custom Comparator.
     *
     * @param arr        array to be sorted
     * @param comparator Comparator for custom sorting logic
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator) {
        quickSort(arr, 0, arr.length - 1, comparator);
    }

    private static <T> void quickSort(T[] arr, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pi = partition(arr, low, high, comparator);
            quickSort(arr, low, pi - 1, comparator);
            quickSort(arr, pi + 1, high, comparator);
        }
    }

    private static <T> int partition(T[] arr, int low, int high, Comparator<T> comparator) {
        T pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparator.compare(arr[j], pivot) < 0) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    // ----------------- HeapSort (Comparable<T> version) -----------------
    public static <T extends Comparable<T>> void heapSort(T[] arr) {
        int n = arr.length;

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Extract elements from heap one by one
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    private static <T extends Comparable<T>> void heapify(T[] arr, int n, int i) {
        int largest = i; // Root
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left].compareTo(arr[largest]) > 0) {
            largest = left;
        }

        if (right < n && arr[right].compareTo(arr[largest]) > 0) {
            largest = right;
        }

        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }

    // ----------------- HeapSort (Comparator<T> version) -----------------
    public static <T> void heapSort(T[] arr, Comparator<T> comparator) {
        int n = arr.length;

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i, comparator);
        }

        // Extract elements from heap one by one
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0, comparator);
        }
    }

    private static <T> void heapify(T[] arr, int n, int i, Comparator<T> comparator) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && comparator.compare(arr[left], arr[largest]) > 0) {
            largest = left;
        }

        if (right < n && comparator.compare(arr[right], arr[largest]) > 0) {
            largest = right;
        }

        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest, comparator);
        }
    }

    /**
     * Utility function to swap two elements in an array.
     *
     * @param arr array containing elements to be swapped
     * @param i   first index
     * @param j   second index
     */
    private static <T> void swap(T[] arr, int i, int j) {
        if (i != j) {
            T temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
}
