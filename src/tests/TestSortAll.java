package tests;

import managers.SortManager;

/**
 * Test class for sorting algorithms.
 * 
 * @author NJobse
 */
public class TestSortAll {
    public static void main(String[] args) {
        String[] compareTypes = { "h", "a", "v" }; // Height, Base Area, Volume
        String[] sortTypes = { "b", "i", "s", "m", "q", "h" }; // Sorting algorithms

        for (String compare : compareTypes) {
            for (String sort : sortTypes) {
                System.out.println("Testing -T" + compare + " -S" + sort);
                String[] testArgs = { "-fres/shapes2.txt", "-T" + compare, "-S" + sort };
                new SortManager(testArgs); // Run sorting
                System.out.println("---------------------------------");
            }
        }
    }
}
