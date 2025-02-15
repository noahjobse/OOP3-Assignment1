package manager;

import java.util.Comparator;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import shapes.*;
import utilities.Sort;

public class SortManager {
    // Attributes
    private Shape[] shapes;
    private String fileName;
    private char compareType;
    private char sortType;

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
        loadShapes();
        sortShapes();
    }

    private void sortShapes() {
        if (shapes == null || shapes.length == 0) {
            System.err.println("No shapes loaded to sort.");
            return;
        }

        Comparator<Shape> comparator = null;

        // Select comparison method
        switch (compareType) {
            case 'h': case 'H':
                comparator = Comparator.naturalOrder(); // Uses compareTo() (height)
                break;
            case 'a': case 'A':
                comparator = new BaseAreaCompare();
                break;
            case 'v': case 'V':
                comparator = new VolumeCompare();
                break;
            default:
                System.err.println("Invalid compare type: " + compareType);
                return;
        }

        // Select sorting algorithm
        switch (sortType) {
            case 'b': case 'B':
                Sort.bubbleSort(shapes, comparator);
                break;
            case 'i': case 'I':
                Sort.insertionSort(shapes, comparator);
                break;
            case 's': case 'S':
                Sort.selectionSort(shapes, comparator);
                break;
            case 'm': case 'M':
                Sort.mergeSort(shapes, comparator);
                break;
            case 'q': case 'Q':
                Sort.quickSort(shapes, comparator);
                break;
            case 'x': case 'X': // Placeholder for extra sorting algorithm
                Sort.heapSort(shapes, comparator);
                break;
            default:
                System.err.println("Invalid sort type: " + sortType);
                return;
        }

        // Print sorted shapes
        System.out.println("Sorted Shapes:");
        for (Shape s : shapes) {
            System.out.println(s);
        }
    }

    private void loadShapes() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            // Read the first line: number of shapes
            int numShapes = Integer.parseInt(br.readLine().trim());
            shapes = new Shape[numShapes];

            // Read and parse each line
            for (int i = 0; i < numShapes; i++) {
                String[] tokens = br.readLine().trim().split("\\s+"); // Split by spaces
                String type = tokens[0]; // First token is the shape type
                double height = Double.parseDouble(tokens[1]);
                double secondValue = Double.parseDouble(tokens[2]);

                switch (type) {
                    case "Cylinder":
                        shapes[i] = new Cylinder(height, secondValue);
                        break;
                    case "Cone":
                        shapes[i] = new Cone(height, secondValue);
                        break;
                    case "Pyramid":
                        shapes[i] = new Pyramid(height, secondValue);
                        break;
                    case "SquarePrism":
                        shapes[i] = new SquarePrism(height, secondValue);
                        break;
                    case "TriangularPrism":
                        shapes[i] = new TriangularPrism(height, secondValue);
                        break;
                    case "PentagonalPrism":
                        shapes[i] = new PentagonalPrism(height, secondValue);
                        break;
                    case "OctagonalPrism":
                        shapes[i] = new OctagonalPrism(height, secondValue);
                        break;
                    default:
                        System.err.println("Unknown shape: " + type);
                        break;
                }
            }

            // Print loaded shapes for verification
            System.out.println("Loaded Shapes:");
            for (Shape s : shapes) {
                System.out.println(s);
            }

        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
