//Jacobs version

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
     * @param args Command-line arguments specifying file, comparison type, and sorting algorithm.
     */
    public SortManager(String[] args) {
        for (String s : args) {
            if (s.startsWith("-f") || s.startsWith("-F")) {
                fileName = s.substring(2);
            } else if (s.startsWith("-t") || s.startsWith("-T")) {
                compareType = Character.toUpperCase(s.charAt(2)); // Ensure uppercase for consistency
            } else if (s.startsWith("-s") || s.startsWith("-S")) {
                sortType = Character.toUpperCase(s.charAt(2)); // Ensure uppercase for sorting method
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

        System.out.println("Executing " + getSortName(sortType) + " Sort..."); // Debugging output

        long startTime = System.nanoTime();
        executeSort(tempShapes, comparator); // Run the selected sorting algorithm
        long endTime = System.nanoTime();
        double elapsedTimeMs = (endTime - startTime) / 1_000_000.0; // Convert to milliseconds

        // Print sorted elements in the desired format
        printKeySortedElements(tempShapes, elapsedTimeMs);
        
        
    }

    /**
     * Prints first, last, and every 1000th sorted element in the required format.
     */

    private void printKeySortedElements(Shape[] sortedShapes, double elapsedTimeMs) {
        int totalShapes = sortedShapes.length;

        System.out.println("\n------------------------------------------------");
        System.out.printf("%-10s %-30s %-15s%n", "Index", "FileName+Shape", getComparisonType());

        // First element
        printFormattedOutput("First", sortedShapes[0]);

        // Every 1000th element
        for (int i = 1000; i < totalShapes; i += 1000) {
            printFormattedOutput(i + "-th", sortedShapes[i]);
        }

        // Last element
        printFormattedOutput("Last", sortedShapes[totalShapes - 1]);

        System.out.println("------------------------------------------------");
        System.out.printf("%s Sort run time was:\t%.4f milliseconds%n", getSortName(sortType), elapsedTimeMs);
    }

    /**
     * Prints a formatted output line based on the required structure.
     */
    private void printFormattedOutput(String index, Shape shape) {
        String shapeInfo = fileName + "+" + shape.getClass().getSimpleName();
        double value = getShapeComparisonValue(shape);
        System.out.printf("%-10s %-30s %-15.4f%n", index, shapeInfo, value);
    }
    
    

    /**
     * Gets the appropriate comparator based on the user input.
     */
    private Comparator<Shape> getComparator() {
        switch (compareType) {
            case 'H':
                return Comparator.naturalOrder(); // Uses Shape's compareTo() for height
            case 'A':
                return new BaseAreaCompare(); // Sort by Base Area
            case 'V':
                return new VolumeCompare(); // Sort by Volume
            default:
                System.err.println("Invalid compare type: " + compareType);
                return null;
        }
    }

    /**
     * Executes the selected sorting algorithm.
     */
    private void executeSort(Shape[] arr, Comparator<Shape> comparator) {
        switch (sortType) {
            case 'B':
                Sort.bubbleSort(arr, comparator);
                break;
            case 'I':
                Sort.insertionSort(arr, comparator);
                break;
            case 'S':
                Sort.selectionSort(arr, comparator);
                break;
            case 'M':
                Sort.mergeSort(arr, comparator);
                break;
            case 'Q':
                Sort.quickSort(arr, comparator);
                break;
            case 'H':
                Sort.heapSort(arr, comparator);
                break;
            default:
                System.err.println("Invalid sort type: " + sortType);
        }
    }

    /**
     * Gets the sorting algorithm's name from the user input.
     */
    private String getSortName(char sortType) {
        switch (sortType) {
            case 'B': return "Bubble";
            case 'I': return "Insertion";
            case 'S': return "Selection";
            case 'M': return "Merge";
            case 'Q': return "Quick";
            case 'H': return "Heap";
            default: return "Unknown";
        }
    }

    /**
     * Gets the comparison type as a string.
     */
    private String getComparisonType() {
        switch (compareType) {
            case 'H': return "Height";
            case 'A': return "Base Area";
            case 'V': return "Volume";
            default: return "Unknown";
        }
    }

    /**
     * Gets the corresponding shape value based on the comparison type.
     */
    private double getShapeComparisonValue(Shape shape) {
        switch (compareType) {
            case 'H': return shape.getHeight();
            case 'A': return shape.calcBaseArea();
            case 'V': return shape.calcVolume();
            default: return 0;
        }
    }
    
    
}