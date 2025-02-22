package tests;

import managers.SortManager;

/**
 * Test class for verifying Bubble Sort functionality on Shape objects.
 */
public class TestSortBubble {
    public static void main(String[] args) {
        System.out.println("\nSorting by Height:");
        runTest(new String[] {
                "-fres/shapes0.txt", // Load shapes from file
                "-Th", // Compare by height
                "-Sb" // Sort using Bubble Sort
        });

        System.out.println("\nSorting by Base Area:");
        runTest(new String[] {
                "-fres/shapes0.txt",
                "-Ta", // Compare by base area
                "-Sb" // Sort using Bubble Sort
        });

        System.out.println("\nSorting by Volume:");
        runTest(new String[] {
                "-fres/shapes1.txt",
                "-Tv", // Compare by volume
                "-Sb" // Sort using Bubble Sort
        });
    }

    /**
     * Runs a sorting test by initializing SortManager with the specified arguments.
     *
     * @param arguments Command-line arguments defining file input, comparison type,
     *                  and sorting method.
     */
    private static void runTest(String[] arguments) {
        System.out.println("\nExecuting SortManager with arguments: " + String.join(" ", arguments));
        new SortManager(arguments);
    }
}
