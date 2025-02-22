package managers;

import java.util.Comparator;

import shapes.Shape;
import shapes.VolumeCompare;
import sorts.*;
import shapes.BaseAreaCompare;

/**
 * Manages sorting of Shape objects based on user-defined parameters.
 * Parses command-line arguments to determine sorting type and method.
 * 
 * <p>
 * Original implementation by Chris, refactored by Noah.
 * </p>
 */
public class SortManager {
    // Attributes to store shapes, input filename, comparison type, and sorting
    // algorithm
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
                fileName = s.substring(2); // Extract filename
            } else if (s.startsWith("-t") || s.startsWith("-T")) {
                compareType = s.substring(2).charAt(0); // Extract comparison type (h, v, a)
            } else if (s.startsWith("-s") || s.startsWith("-S")) {
                sortType = s.substring(2).charAt(0); // Extract sorting algorithm (b, i, s, etc.)
            }
        }
        // Load shapes from the specified file
        shapes = FileManager.loadShapes(fileName);
        sortShapes();
    }

    /**
     * Determines the sorting criteria and algorithm, then sorts the shapes.
     */
    private void sortShapes() {
        // Ensure shapes are loaded before proceeding
        if (shapes == null || shapes.length == 0) {
            System.err.println("No shapes loaded to sort.");
            return;
        }

        Comparator<Shape> comparator = null;

        // Select the comparison method (Height, Base Area, or Volume)
        switch (compareType) {
            case 'h':
            case 'H':
                comparator = Comparator.naturalOrder(); // Uses Shape's compareTo() (Height-based sorting)
                break;
            case 'a':
            case 'A':
                comparator = new BaseAreaCompare(); // Sort by Base Area
                break;
            case 'v':
            case 'V':
                comparator = new VolumeCompare(); // Sort by Volume
                break;
            default:
                System.err.println("Invalid compare type: " + compareType);
                return;
        }

        // Select and execute the chosen sorting algorithm
        switch (sortType) {
            case 'b': // Bubble Sort
            case 'B':
                Sort.bubbleSort(shapes, comparator);
                break;
            case 'i': // Insertion Sort
            case 'I':
                Sort.insertionSort(shapes, comparator);
                break;
            case 's': // Selection Sort
            case 'S':
                Sort.selectionSort(shapes, comparator);
                break;

            case 'm': // Merge Sort
            case 'M':
                Sort.mergeSort(shapes, comparator);
                break;

            case 'q': // Quick Sort
            case 'Q':
                Sort.quickSort(shapes, comparator);
                break;

            case 'h': // Heap Sort
            case 'H':
                Sort.heapSort(shapes, comparator);
                break;

            default:
                System.err.println("Invalid sort type: " + sortType);
                return;
        }

        // Print the sorted list of shapes
        System.out.println("Sorted Shapes:");
        for (Shape s : shapes) {
            System.out.println(s);
        }
    }
}
