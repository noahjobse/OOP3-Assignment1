package tests;

import shapes.Cone;
import sorts.Sort;

/**
 * Test class for Cone objects.
 * 
 * @author CRacicot
 */
public class TestCone {
    public static void main(String[] args) {
        // Create Cone objects
        Cone cone1 = new Cone(10, 5); // Height = 10, Radius = 5
        Cone cone2 = new Cone(7, 3); // Height = 7, Radius = 3
        Cone cone3 = new Cone(15, 4); // Height = 15, Radius = 4

        // Print details
        System.out.println("Cone 1: " + cone1);
        System.out.println("Cone 2: " + cone2);
        System.out.println("Cone 3: " + cone3);

        // Test calculations
        System.out.println("Base Area of Cone 1: " + cone1.calcBaseArea());
        System.out.println("Volume of Cone 1: " + cone1.calcVolume());
        System.out.println("Base Area of Cone 2: " + cone2.calcBaseArea());
        System.out.println("Volume of Cone 2: " + cone2.calcVolume());
        System.out.println("Base Area of Cone 3: " + cone3.calcBaseArea());
        System.out.println("Volume of Cone 3: " + cone3.calcVolume());

        // Test sorting by height using bubbleSort
        Cone[] cones = { cone1, cone2, cone3 };

        System.out.println("\nBefore Sorting:");
        for (Cone c : cones)
            System.out.println(c);

        Sort.selectionSort(cones); // Uses bubble sort from utilities.Sort

        System.out.println("\nAfter Sorting (by height):");
        for (Cone c : cones)
            System.out.println(c);
    }
}
