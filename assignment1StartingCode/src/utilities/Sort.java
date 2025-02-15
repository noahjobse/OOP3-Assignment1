package utilities;

import java.util.Comparator;
import shapes.Shape;

public class Sort {

/**
 * Sorts an array using the bubble sort algorithm.
 *
 * @param arr array to be sorted
 */
public static <T extends Comparable<T>> void bubbleSort(T[] arr)
{
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
        if (!swapped) break;
    }
}

/**
 * Sorts an array using bubble sort with a custom Comparator.
 *
 * @param arr array to be sorted
 * @param c Comparator for custom sorting logic
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

        if (!swapped) break;
    }
}
}
