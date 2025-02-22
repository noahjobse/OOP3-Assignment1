package managers;

import java.util.Comparator;
import java.util.Arrays;
import shapes.Shape;
import shapes.VolumeCompare;
import sorts.Sort;
import shapes.BaseAreaCompare;

/**
 * Manages sorting of Shape objects based on user-defined parameters.
 * Parses command-line arguments to determine sorting type and method.
 * Measures and reports execution time for a single run.
 */
public class SortManager {
    private Shape[] shapes;
    private String fileName;
    private char compareType;
    private char sortType;

    /**
     * Constructor: Parses command-line arguments and initiates sorting.
     *
     * @param args Command-line arguments specifying file, comparison type, and
     *             sorting algorithm.
     */
    public SortManager(String[] args) {
        for (String s : args) {
            System.out.println(s);
            if (s.startsWith("-f") || s.startsWith("-F")) {
                fileName = s.substring(2);
            } else if (s.startsWith("-t") || s.startsWith("-T")) {
                compareType = s.substring(2).charAt(0);
            } else if (s.startsWith("-s") || s.startsWith("-S")) {
                sortType = s.substring(2).charAt(0);
            }
        }
        shapes = FileManager.loadShapes(fileName);
        sortShapes();
    }

    /**
     * Determines the sorting criteria and algorithm, then sorts the shapes.
     * Measures execution time and reports it.
     */
    private void sortShapes() {
        if (shapes == null || shapes.length == 0) {
            System.err.println("No shapes loaded to sort.");
            return;
        }

        Comparator<Shape> comparator = getComparator();
        if (comparator == null) {
            return;
        }

        Shape[] tempShapes = Arrays.copyOf(shapes, shapes.length); // Copy array to preserve original

        long startTime = System.nanoTime();

        executeSort(tempShapes, comparator); // Run the selected sorting algorithm

        long endTime = System.nanoTime();
        double elapsedTimeMs = (endTime - startTime) / 1_000_000.0; // Convert to milliseconds

        System.out.println("\nSorting Completed.");
        System.out.println("Execution Time: " + elapsedTimeMs + " ms");

        // Print the sorting criteria and sorted shapes
        System.out.println("\nSorted by: " + getSortingCriteria());
        System.out.println("Sorted Shapes:");
        for (Shape s : tempShapes) {
            System.out.println(s);
        }
    }

    /**
     * Gets the appropriate comparator based on the user input.
     */
    private Comparator<Shape> getComparator() {
        switch (compareType) {
            case 'h':
            case 'H':
                return Comparator.naturalOrder(); // Uses Shape's compareTo() for height
            case 'a':
            case 'A':
                return new BaseAreaCompare(); // Sort by Base Area
            case 'v':
            case 'V':
                return new VolumeCompare(); // Sort by Volume
            default:
                System.err.println("Invalid compare type: " + compareType);
                return null;
        }
    }

    /**
     * Gets the sorting criteria as a string.
     */
    private String getSortingCriteria() {
        switch (compareType) {
            case 'h':
            case 'H':
                return "Height";
            case 'a':
            case 'A':
                return "Base Area";
            case 'v':
            case 'V':
                return "Volume";
            default:
                return "Unknown";
        }
    }

    /**
     * Executes the selected sorting algorithm.
     */
    private void executeSort(Shape[] arr, Comparator<Shape> comparator) {
        switch (sortType) {
            case 'b':
            case 'B':
                Sort.bubbleSort(arr, comparator);
                break;
            case 'i':
            case 'I':
                Sort.insertionSort(arr, comparator);
                break;
            case 's':
            case 'S':
                Sort.selectionSort(arr, comparator);
                break;
            case 'm':
            case 'M':
                Sort.mergeSort(arr, comparator);
                break;
            case 'q':
            case 'Q':
                Sort.quickSort(arr, comparator);
                break;
            case 'h':
            case 'H':
                Sort.heapSort(arr, comparator);
                break;
            default:
                System.err.println("Invalid sort type: " + sortType);
        }
    }
}
