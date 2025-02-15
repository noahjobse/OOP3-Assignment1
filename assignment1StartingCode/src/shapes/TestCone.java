package shapes;

public class TestCone {
    public static void main(String[] args) {
        // Create Cone objects
        Cone cone1 = new Cone(10, 5);  // Height = 10, Radius = 5
        Cone cone2 = new Cone(7, 3);   // Height = 7, Radius = 3

        // Print details
        System.out.println("Cone 1: " + cone1);
        System.out.println("Cone 2: " + cone2);

        // Test calculations
        System.out.println("Base Area of Cone 1: " + cone1.calcBaseArea());
        System.out.println("Volume of Cone 1: " + cone1.calcVolume());
        System.out.println("Base Area of Cone 2: " + cone2.calcBaseArea());
        System.out.println("Volume of Cone 2: " + cone2.calcVolume());

        // Test sorting by height
        Cone[] cones = {cone1, cone2, new Cone(15, 4)};
        System.out.println("\nBefore Sorting:");
        for (Cone c : cones) System.out.println(c);

        java.util.Arrays.sort(cones);  // Uses compareTo() (sorts by height)

        System.out.println("\nAfter Sorting:");
        for (Cone c : cones) System.out.println(c);
    }
}
