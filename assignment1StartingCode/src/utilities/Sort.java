package utilities;

import java.util.Comparator;

public class Sort {

    /**
     * Sorts an array using the bubble sort algorithm.
     *
     * @param arr array to be sorted
     */
    public static <T extends Comparable<T>> void bubbleSort(T[] arr) {
        int n = arr.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].compareTo(arr[j + 1]) > 0) {
                    // Swap elements
                    T temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
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
                    // Swap elements
                    T temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped)
                break;
        }
    }

    /**
     * Sorts an array using the insertion sort algorithm.
     *
     * @param arr array to be sorted
     */
    public static <T extends Comparable<T>> void insertionSort(T[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            T key = arr[i];
            int j = i - 1;
            // Shift elements greater than key to one position ahead
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
            // Shift elements greater than key (using comparator) to one position ahead
            while (j >= 0 && c.compare(arr[j], key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    /**
     * Sorts an array using the selection sort algorithm.
     *
     * @param arr array to be sorted
     */
    public static <T extends Comparable<T>> void selectionSort(T[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            // Find the index of the minimum element
            for (int j = i + 1; j < n; j++) {
                if (arr[j].compareTo(arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            // Swap the found minimum element with the first element
            T temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
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
            // Find the index of the minimum element based on the comparator
            for (int j = i + 1; j < n; j++) {
                if (c.compare(arr[j], arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            // Swap the found minimum element with the first element
            T temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }
}
