package appDomain;

import managers.SortManager;

public class AppDriver {
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.out.println("No arguments provided. Running with default test values...");
                args = new String[]{"-fres/shapes2.txt", "-tV", "-sQ"}; 
                // "-fshapes.txt" -> File containing shapes
                // "-tV" -> Compare by Volume
                // "-sQ" -> Use QuickSort
            }

            new SortManager(args);
        } catch (Exception e) {
            System.err.println("Unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
