package shapes;

/**
 * Represents a Shape object.
 * 
 * @author CRacicot
 */
public abstract class Shape implements Comparable<Shape> {
    private double height;

    /**
     * Creates a Shape with the specific height.
     * 
     * @param height height of Shape
     */
    public Shape(double height) {
        super();
        this.height = height;
    }

    /**
     * Returns the height.
     * 
     * @return height
     */
    public double getHeight() {
        return height;
    }

    @Override
    public int compareTo(Shape other) {
        if (this.height < other.height)
            return 1; // Reverse order (descending)
        if (this.height > other.height)
            return -1; // Reverse order (descending)
        return 0;
    }

    /**
     * DOUBLE CHECK
     * Returns the calculated base area.
     * base area
     **/
    public abstract double calcBaseArea();

    public abstract double calcVolume();

    @Override
    public String toString() {
        return "Shape [getHeight()=" + getHeight() + ", calcBaseArea()=" + calcBaseArea() + ", calcVolume()="
                + calcVolume() + "]";
    }

}
