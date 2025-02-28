package managers;

import java.util.Comparator;
import java.util.Arrays;
import shapes.Shape;
import shapes.VolumeCompare;
import sorts.Sort;
import shapes.BaseAreaCompare;

public class SortManager {
    private Shape[] shapes;
    private String fileName;
    private char compareType = '\0';
    private char sortType = '\0';

    /**
     * Main constructor that takes all CLI arguments, parses them,
     * and triggers the sorting if valid arguments are provided.
     */
    public SortManager(String[] args) {
        System.out.println("Received from CLI: " + String.join(" ", args));

        boolean fileProvided = false, compareProvided = false, sortProvided = false;

        for (String s : args) {
            // Normalize possible en-dashes or similar (optional but can help if copy/paste from Word)
            s = s.replace('–', '-').replace('—', '-');

            if (s.startsWith("-f") || s.startsWith("-F")) {
                // e.g. "-fshapes1.txt" or "-F\"C:\temp\shapes1.txt\""
                if (s.length() > 2) {
                    // Remove the first two characters "-f" or "-F"
                    String rawFile = s.substring(2);
                    // Strip leading/trailing quotes if present
                    rawFile = stripQuotes(rawFile);
                    fileName = rawFile;
                    fileProvided = true;
                } else {
                    System.err.println("Error! Missing file name after '-f'. Please provide a valid file path.");
                    return;
                }
            } else if (s.startsWith("-t") || s.startsWith("-T")) {
                // e.g. "-Tv", "-ta", "-tH", etc.
                if (s.length() > 2) {
                    char c = Character.toUpperCase(s.charAt(2));
                    if (c != 'H' && c != 'A' && c != 'V') {
                        System.err.println("Error! Invalid comparison type '" + c + "'. Use 'H' (Height), 'A' (Base Area), or 'V' (Volume).");
                        return;
                    }
                    compareType = c;
                    compareProvided = true;
                } else {
                    System.err.println("Error! Missing comparison type after '-t'. Use 'H', 'A', or 'V'.");
                    return;
                }
            } else if (s.startsWith("-s") || s.startsWith("-S")) {
                // e.g. "-sB", "-sQ", "-sM", etc.
                if (s.length() > 2) {
                    char c = Character.toUpperCase(s.charAt(2));
                    // Allowed methods: B, I, S, M, Q, H
                    if ("BISMQH".indexOf(c) == -1) {
                        System.err.println("Error! Invalid sorting method '" + c 
                            + "'. Use 'B' (Bubble), 'I' (Insertion), 'S' (Selection), "
                            + "'M' (Merge), 'Q' (Quick), or 'H' (Heap).");
                        return;
                    }
                    sortType = c;
                    sortProvided = true;
                } else {
                    System.err.println("Error! Missing sorting method after '-s'. Use 'B', 'I', 'S', 'M', 'Q', or 'H'.");
                    return;
                }
            } else {
                // Unrecognized argument
                System.err.println("Error: Unrecognized argument '" + s 
                                   + "'. Valid flags are '-f', '-t', '-s' (or uppercase).");
                return;
            }
        }

        // Validate that all required flags were provided
        if (!fileProvided) {
            System.err.println("Error: Missing required file name argument '-f'.");
            return;
        }
        if (!compareProvided) {
            System.err.println("Error: Missing required comparison type argument '-t'. Use 'H', 'A', or 'V'.");
            return;
        }
        if (!sortProvided) {
            System.err.println("Error: Missing required sorting method argument '-s'. Use 'B', 'I', 'S', 'M', 'Q', or 'H'.");
            return;
        }

        // Load shapes
        shapes = FileManager.loadShapes(fileName);
        if (shapes == null || shapes.length == 0) {
            System.err.println("Error: No shapes loaded from file '" + fileName + "'. "
                + "Ensure the file exists and contains valid shape data.");
            return;
        }

        // Perform sorting (and print results)
        runSorting();
    }

    /**
     * Runs sorting and prints the results along with benchmarking time.
     */
    private void runSorting() {
        double elapsedTimeMs = benchmarkSorting(); // measure time
        if (elapsedTimeMs == -1) return; // exit if sorting fails

        // Sort (again, so we can show final sorted array)
        sortShapes();
        System.out.printf("Sorting algorithm: %s | Time taken: %.4f milliseconds%n", 
                          getSortName(sortType), elapsedTimeMs);
    }

    /**
     * Performs sorting but does not measure time (sorting only).
     */
    private void sortShapes() {
        Comparator<Shape> comparator = getComparator();
        if (comparator == null) return;

        // Copy shapes to preserve the original array
        Shape[] tempShapes = Arrays.copyOf(shapes, shapes.length);
        executeSort(tempShapes, comparator);
        printKeySortedElements(tempShapes);
    }

    /**
     * Benchmarks sorting time but does not modify the shapes field.
     */
    public double benchmarkSorting() {
        if (shapes == null || shapes.length == 0) {
            System.err.println("Error: No shapes to sort.");
            return -1;
        }

        Comparator<Shape> comparator = getComparator();
        if (comparator == null) return -1;

        // Copy shapes so we don't sort the original array in the benchmark
        Shape[] tempShapes = Arrays.copyOf(shapes, shapes.length);

        long startTime = System.nanoTime();
        executeSort(tempShapes, comparator);
        long endTime = System.nanoTime();

        return (endTime - startTime) / 1_000_000.0; // convert to milliseconds
    }

    /**
     * Prints the first, every 1000th, and last shapes (by sorted order).
     */
    private void printKeySortedElements(Shape[] sortedShapes) {
        int totalShapes = sortedShapes.length;

        System.out.println("\n------------------------------------------------");
        System.out.printf("%-10s %-30s %-15s%n", "Index", "FileName+Shape", getComparisonType());

        // First
        printFormattedOutput("First", sortedShapes[0]);

        // Every 1000th
        for (int i = 1000; i < totalShapes; i += 1000) {
            printFormattedOutput(i + "-th", sortedShapes[i]);
        }

        // Last
        if (totalShapes > 1) {
            printFormattedOutput("Last", sortedShapes[totalShapes - 1]);
        }

        System.out.println("------------------------------------------------");
    }

    private void printFormattedOutput(String index, Shape shape) {
        String shapeInfo = fileName + "+" + shape.getClass().getSimpleName();
        double value = getShapeComparisonValue(shape);
        System.out.printf("%-10s %-30s %-15.4f%n", index, shapeInfo, value);
    }

    /**
     * Returns a comparator object based on the -t or -T option:
     *   H -> naturalOrder() (by height, as shape implements Comparable)
     *   A -> BaseAreaCompare
     *   V -> VolumeCompare
     */
    private Comparator<Shape> getComparator() {
        switch (compareType) {
            case 'H': return Comparator.naturalOrder();
            case 'A': return new BaseAreaCompare();
            case 'V': return new VolumeCompare();
            default:
                System.err.println("Error: Unknown comparison type '" + compareType + "'.");
                return null;
        }
    }

    /**
     * Executes one of the sorting algorithms in Sort.java based on the -s or -S option.
     */
    private void executeSort(Shape[] arr, Comparator<Shape> comparator) {
        switch (sortType) {
            case 'B': Sort.bubbleSort(arr, comparator); break;
            case 'I': Sort.insertionSort(arr, comparator); break;
            case 'S': Sort.selectionSort(arr, comparator); break;
            case 'M': Sort.mergeSort(arr, comparator); break;
            case 'Q': Sort.quickSort(arr, comparator); break;
            case 'H': Sort.heapSort(arr, comparator); break;
            default:
                System.err.println("Error: Unknown sorting algorithm '" + sortType + "'.");
        }
    }

    /**
     * Maps the sortType char to a descriptive name.
     */
    private String getSortName(char sortType) {
        switch (sortType) {
            case 'B': return "Bubble";
            case 'I': return "Insertion";
            case 'S': return "Selection";
            case 'M': return "Merge";
            case 'Q': return "Quick";
            case 'H': return "Heap";
            default:  return "Unknown";
        }
    }

    /**
     * Maps the compareType char to the type of comparison.
     */
    private String getComparisonType() {
        switch (compareType) {
            case 'H': return "Height";
            case 'A': return "Base Area";
            case 'V': return "Volume";
            default:  return "Unknown";
        }
    }

    /**
     * Retrieves the relevant numeric value based on compareType.
     */
    private double getShapeComparisonValue(Shape shape) {
        switch (compareType) {
            case 'H': return shape.getHeight();
            case 'A': return shape.calcBaseArea();
            case 'V': return shape.calcVolume();
            default:  return 0;
        }
    }

    /**
     * Utility to remove leading or trailing quotes from a string.
     * For example, "\"C:\\temp\\shapes1.txt\"" => "C:\temp\shapes1.txt"
     */
    private String stripQuotes(String s) {
        if (s.length() >= 2) {
            if (s.startsWith("\"") && s.endsWith("\"")) {
                return s.substring(1, s.length() - 1);
            }
        }
        return s;
    }
}
