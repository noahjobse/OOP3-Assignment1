package managers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import shapes.*;

/**
 * Handles file reading and parsing for Shape objects.
 * Reads a specified file and converts each line into a Shape instance.
 * 
 * Original implementation by CRacicot, refactored by NJobse.
 */
public class FileManager {

    /**
     * Format:
     * <ShapeType> <Height> <SecondValue>
     * - Cylinder <Height> <Radius>
     * - Cone <Height> <Radius>
     * - Pyramid <Height> <EdgeLength>
     * - SquarePrism <Height> <EdgeLength>
     * - TriangularPrism <Height> <EdgeLength>
     * - PentagonalPrism <Height> <EdgeLength>
     * - OctagonalPrism <Height> <EdgeLength>
     * 
     * @param fileName the path to the file containing shape data
     * @return an array of Shape objects loaded from the file, or null if an error
     *         occurs
     */
    public static Shape[] loadShapes(String fileName) {
        Shape[] shapes = null;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            // Read the first line: Number of shapes in the file
            int numShapes = Integer.parseInt(br.readLine().trim());
            shapes = new Shape[numShapes];
            // Read and parse each shape line
            for (int i = 0; i < numShapes; i++) {
                String[] tokens = br.readLine().trim().split("\\s+"); // Split line by spaces
                String type = tokens[0]; // Extract shape type
                // Parse and round values
                double height = round(Double.parseDouble(tokens[1]));
                double secondValue = round(Double.parseDouble(tokens[2]));
                // Create shape instances based on type
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
                        System.err.println("Unknown shape type: " + type);
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
        return shapes;
    }

    /**
     * Rounds a double value to 2 decimal places.
     *
     * @param value the value to be rounded
     * @return rounded value
     */
    private static double round(double value) {
        return Math.round(value * 100.0) / 100.0; // Rounds to 2 decimal places
    }
}
