//Jacobs Version 2025-02-26
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

    public SortManager(String[] args) {
        System.out.println("Received arguments: " + String.join(" ", args));

        boolean fileProvided = false, compareProvided = false, sortProvided = false;

        for (String s : args) {
            if (s.startsWith("-f") || s.startsWith("-F")) {
                if (s.length() > 2) {
                    fileName = s.substring(2);
                    fileProvided = true;
                } else {
                    System.err.println("Error: Missing file name after '-f'. Please provide a valid file path.");
                    return;
                }
            } else if (s.startsWith("-t") || s.startsWith("-T")) {
                if (s.length() > 2) {
                    compareType = Character.toUpperCase(s.charAt(2));
                    if (compareType != 'H' && compareType != 'A' && compareType != 'V') {
                        System.err.println("Error: Invalid comparison type '" + compareType + "'. Use 'H' (Height), 'A' (Base Area), or 'V' (Volume).");
                        return;
                    }
                    compareProvided = true;
                } else {
                    System.err.println("Error: Missing comparison type after '-t'. Use 'H', 'A', or 'V'.");
                    return;
                }
            } else if (s.startsWith("-s") || s.startsWith("-S")) {
                if (s.length() > 2) {
                    sortType = Character.toUpperCase(s.charAt(2));
                    if ("BISMQH".indexOf(sortType) == -1) {
                        System.err.println("Error: Invalid sorting method '" + sortType + "'. Use 'B' (Bubble), 'I' (Insertion), 'S' (Selection), 'M' (Merge), 'Q' (Quick), or 'H' (Heap).");
                        return;
                    }
                    sortProvided = true;
                } else {
                    System.err.println("Error: Missing sorting method after '-s'. Use 'B', 'I', 'S', 'M', 'Q', or 'H'.");
                    return;
                }
            } else {
                System.err.println("Error: Unrecognized argument '" + s + "'. Use '-f', '-t', and '-s' with valid values.");
                return;
            }
        }

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

        shapes = FileManager.loadShapes(fileName);
        if (shapes == null || shapes.length == 0) {
            System.err.println("Error: No shapes loaded from file '" + fileName + "'. Ensure the file exists and contains valid shape data.");
            return;
        }

        sortShapes();
    }

    private void sortShapes() {
        Comparator<Shape> comparator = getComparator();
        if (comparator == null) {
            return;
        }

        Shape[] tempShapes = Arrays.copyOf(shapes, shapes.length);

        System.out.println("Executing " + getSortName(sortType) + " Sort...");

        long startTime = System.nanoTime();
        executeSort(tempShapes, comparator);
        long endTime = System.nanoTime();
        double elapsedTimeMs = (endTime - startTime) / 1_000_000.0;

        printKeySortedElements(tempShapes, elapsedTimeMs);
    }

    private void printKeySortedElements(Shape[] sortedShapes, double elapsedTimeMs) {
        int totalShapes = sortedShapes.length;

        System.out.println("\n------------------------------------------------");
        System.out.printf("%-10s %-30s %-15s%n", "Index", "FileName+Shape", getComparisonType());

        printFormattedOutput("First", sortedShapes[0]);

        for (int i = 1000; i < totalShapes; i += 1000) {
            printFormattedOutput(i + "-th", sortedShapes[i]);
        }

        printFormattedOutput("Last", sortedShapes[totalShapes - 1]);

        System.out.println("------------------------------------------------");
        System.out.printf("%s Sort run time was:\t%.4f milliseconds%n", getSortName(sortType), elapsedTimeMs);
    }

    private void printFormattedOutput(String index, Shape shape) {
        String shapeInfo = fileName + "+" + shape.getClass().getSimpleName();
        double value = getShapeComparisonValue(shape);
        System.out.printf("%-10s %-30s %-15.4f%n", index, shapeInfo, value);
    }

    private Comparator<Shape> getComparator() {
        switch (compareType) {
            case 'H': return Comparator.naturalOrder();
            case 'A': return new BaseAreaCompare();
            case 'V': return new VolumeCompare();
            default: return null;
        }
    }

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
