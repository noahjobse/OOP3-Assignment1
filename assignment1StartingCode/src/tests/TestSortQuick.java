package tests;

import managers.SortManager;

/**
 * Test class for sorting shapes using Quick Sort.
 * 
 * @author NJobse
 */
public class TestSortQuick {
    public static void main(String[] args) {
        System.out.println("\nRunning Tests: QuickSort (Using Comparable and Comparator)");
        // Test sorting by height using Comparable
        System.out.println("\nSorting by Height:");
        runTest(new String[] {
                "-fres/shapes1.txt",
                "-Th", // Compare by height
                "-Sq" // QuickSort
        });
        // Test sorting by base area using Comparator
        System.out.println("\nSorting by Base Area:");
        runTest(new String[] {
                "-fres/shapes1.txt",
                "-Ta", // Compare by base area
                "-Sq" // QuickSort
        });
        // Test sorting by volume using Comparator
        System.out.println("\nSorting by Volume:");
        runTest(new String[] {
                "-fres/shapes1.txt",
                "-Tv", // Compare by volume
                "-Sq" // QuickSort
        });
    }

    private static void runTest(String[] arguments) {
        new SortManager(arguments);
    }
}
