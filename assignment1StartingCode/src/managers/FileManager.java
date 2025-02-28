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
     * Reads a file and loads Shape objects based on formatted shape data.
     * 
     * Format:
     * &lt;ShapeType&gt; &lt;Height&gt; &lt;SecondValue&gt;
     * - Cylinder &lt;Height&gt; &lt;Radius&gt;
     * - Cone &lt;Height&gt; &lt;Radius&gt;
     * - Pyramid &lt;Height&gt; &lt;EdgeLength&gt;
     * - SquarePrism &lt;Height&gt; &lt;EdgeLength&gt;
     * - TriangularPrism &lt;Height&gt; &lt;EdgeLength&gt;
     * - PentagonalPrism &lt;Height&gt; &lt;EdgeLength&gt;
     * - OctagonalPrism &lt;Height&gt; &lt;EdgeLength&gt;
     * 
     * @param fileName the path to the file containing shape data
     * @return an array of Shape objects loaded from the file, or null if an error occurs
     * 
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
                if (tokens.length < 3) {
                    System.err.println("Invalid line format at line " + (i + 2) + ": " + String.join(" ", tokens));
                    continue; // Skip malformed lines
                }

                String type = tokens[0]; // Extract shape type

                // Parse values safely
                double height;
                double secondValue;
                try {
                    height = Double.parseDouble(tokens[1]);
                    secondValue = Double.parseDouble(tokens[2]);
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing numeric values at line " + (i + 2) + ": " + e.getMessage());
                    continue; // Skip this shape and move to the next
                }

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
                        System.err.println("Unknown shape type at line " + (i + 2) + ": " + type);
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing shape count: " + e.getMessage());
        }
        return shapes;
    }

    /**
     * Rounds a double value to 2 decimal places.
     *
     * @param value the value to be rounded
     * @return the rounded value
     */
    private static double round(double value) {
        return Math.round(value * 100.0) / 100.0; // Rounds to 2 decimal places
    }
}
