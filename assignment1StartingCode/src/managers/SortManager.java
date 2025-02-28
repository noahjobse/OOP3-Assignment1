// Jacobs v 2025-02-27
package managers;

import java.util.Comparator;
import java.util.Arrays;
import shapes.Shape;
import shapes.VolumeCompare;
import sorts.Sort;
import shapes.BaseAreaCompare;

public class SortManager {
    private Shape[] shapes; // Array to store Shape objects
    private String fileName; // Name of the input file containing shape data
    private char compareType = '\0'; // Stores user-selected comparison type (H, A, V)
    private char sortType = '\0'; // Stores user-selected sorting algorithm (B, I, S, M, Q, H)

    /**
     * Constructor that takes in command-line arguments and sets up sorting parameters.
     * It checks for valid arguments and ensures all required inputs are provided.
     */
    public SortManager(String[] args) {
        System.out.println("Received from " + String.join(" ", args));

        boolean fileProvided = false, compareProvided = false, sortProvided = false;

        // Loop through all command-line arguments to extract sorting parameters
        for (String s : args) {
            if (s.startsWith("-f") || s.startsWith("-F")) { // File name
                if (s.length() > 2) {
                    fileName = s.substring(2);
                    fileProvided = true;
                } else {
                    System.err.println("Error! Missing file name after '-f'. Please provide a valid file path.");
                    return;
                }
            } else if (s.startsWith("-t") || s.startsWith("-T")) { // Comparison type (H, A, V)
                if (s.length() > 2) {
                    compareType = Character.toUpperCase(s.charAt(2));
                    if (compareType != 'H' && compareType != 'A' && compareType != 'V') {
                        System.err.println("Error! Invalid comparison type '" + compareType + "'. Use 'H' (Height), 'A' (Base Area), or 'V' (Volume).");
                        return;
                    }
                    compareProvided = true;
                } else {
                    System.err.println("Error! Missing comparison type after '-t'. Use 'H', 'A', or 'V'.");
                    return;
                }
            } else if (s.startsWith("-s") || s.startsWith("-S")) { // Sorting algorithm (B, I, S, M, Q, H)
                if (s.length() > 2) {
                    sortType = Character.toUpperCase(s.charAt(2));
                    if ("BISMQH".indexOf(sortType) == -1) {
                        System.err.println("Error! Invalid sorting method '" + sortType + "'. Use 'B' (Bubble), 'I' (Insertion), 'S' (Selection), 'M' (Merge), 'Q' (Quick), or 'H' (Heap).");
                        return;
                    }
                    sortProvided = true;
                } else {
                    System.err.println("Error! Missing sorting method after '-s'. Use 'B', 'I', 'S', 'M', 'Q', or 'H'.");
                    return;
                }
            } else {
                System.err.println("Error! Unrecognized argument '" + s + "'. Use '-f', '-t', and '-s' with valid values.");
                return;
            }
        }

        // Ensure all required arguments are provided before proceeding
        if (!fileProvided) {
            System.err.println("Error! Missing required file name argument '-f'.");
            return;
        }
        if (!compareProvided) {
            System.err.println("Error! Missing required comparison type argument '-t'. Use 'H', 'A', or 'V'.");
            return;
        }
        if (!sortProvided) {
            System.err.println("Error! Missing required sorting method argument '-s'. Use 'B', 'I', 'S', 'M', 'Q', or 'H'.");
            return;
        }

        // Load shapes from file into an array
        shapes = FileManager.loadShapes(fileName);
        if (shapes == null || shapes.length == 0) {
            System.err.println("Error! No shapes loaded from file '" + fileName + "'. Ensure the file exists and contains valid shape data.");
            return;
        }

        runSorting();  // Call the sorting execution method
    }

    /**
     * Runs sorting and prints the results along with benchmarking time.
     */
    private void runSorting() {
        double elapsedTimeMs = benchmarkSorting(); // Measure sorting time
        if (elapsedTimeMs == -1) return; // Exit if sorting fails

        sortShapes(); // Perform actual sorting
        System.out.printf("Sorting algorithm: %s | Time taken: %.4f milliseconds%n", getSortName(sortType), elapsedTimeMs);
    }

    /**
     * Sorts the shapes without measuring time.
     */
    private void sortShapes() {
        Comparator<Shape> comparator = getComparator();
        if (comparator == null) return;

        Shape[] tempShapes = Arrays.copyOf(shapes, shapes.length); // Copy to avoid modifying original array
        executeSort(tempShapes, comparator); // Apply the selected sorting algorithm
        printKeySortedElements(tempShapes); // Print sorted elements
    }

    /**
     * Benchmarks sorting time but does not modify sorting logic.
     */
    public double benchmarkSorting() {
        if (shapes == null || shapes.length == 0) {
            System.err.println("Error: No shapes to sort.");
            return -1;
        }

        Comparator<Shape> comparator = getComparator();
        if (comparator == null) return -1;

        Shape[] tempShapes = Arrays.copyOf(shapes, shapes.length);

        long startTime = System.nanoTime();
        executeSort(tempShapes, comparator); // Sorting process
        long endTime = System.nanoTime();

        return (endTime - startTime) / 1_000_000.0; // Convert to milliseconds
    }

    /**
     * Prints important elements from the sorted list.
     */
    private void printKeySortedElements(Shape[] sortedShapes) {
        int totalShapes = sortedShapes.length;

        System.out.println("\n------------------------------------------------");
        System.out.printf("%-10s %-30s %-15s%n", "Index", "FileName+Shape", getComparisonType());

        printFormattedOutput("First", sortedShapes[0]); // First element

        for (int i = 1000; i < totalShapes; i += 1000) {
            printFormattedOutput(i + "-th", sortedShapes[i]); // Every 1000th element
        }

        printFormattedOutput("Last", sortedShapes[totalShapes - 1]); // Last element

        System.out.println("------------------------------------------------");
    }

    private void printFormattedOutput(String index, Shape shape) {
        String shapeInfo = fileName + "+" + shape.getClass().getSimpleName();
        double value = getShapeComparisonValue(shape);
        System.out.printf("%-10s %-30s %-15.4f%n", index, shapeInfo, value);
    }

    /**
     * Returns the appropriate comparator based on user selection.
     */
    private Comparator<Shape> getComparator() {
        switch (compareType) {
            case 'H': return Comparator.naturalOrder();
            case 'A': return new BaseAreaCompare();
            case 'V': return new VolumeCompare();
            default: return null;
        }
    }

    /**
     * Executes the chosen sorting algorithm.
     */
    private void executeSort(Shape[] arr, Comparator<Shape> comparator) {
        switch (sortType) {
            case 'B': Sort.bubbleSort(arr, comparator); break;
            case 'I': Sort.insertionSort(arr, comparator); break;
            case 'S': Sort.selectionSort(arr, comparator); break;
            case 'M': Sort.mergeSort(arr, comparator); break;
            case 'Q': Sort.quickSort(arr, comparator); break;
            case 'H': Sort.heapSort(arr, comparator); break;
            default: System.err.println("Error: Unknown sorting algorithm '" + sortType + "'.");
        }
    }

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

    private String getComparisonType() {
        switch (compareType) {
            case 'H': return "Height";
            case 'A': return "Base Area";
            case 'V': return "Volume";
            default: return "Unknown";
        }
    }

    private double getShapeComparisonValue(Shape shape) {
        switch (compareType) {
            case 'H': return shape.getHeight();
            case 'A': return shape.calcBaseArea();
            case 'V': return shape.calcVolume();
            default: return 0;
        }
    }
}
