package shapes;

/*
 * Class representing a Cone.
 * @author CRacicot
 */
public class Cone extends Shape {
    private double radius;

    /**
     * Creates a Cone with the specified height and radius.
     * @param height Cone's height
     * @param radius Cone's radius
     */
    public Cone(double height, double radius) {
        super(height);
        this.radius = radius;
    }

    /**
     * Returns the Cone's radius.
     * @return radius
     */
    public double getRadius() {
        return radius;
    }
    
    @Override
    public double calcBaseArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calcVolume() {
        return calcBaseArea() * getHeight() / 3;
    }

    @Override
    public String toString() {
        return "Cone [Height=" + getHeight() + ", Radius=" + radius + ", Base Area=" + calcBaseArea() + ", Volume=" + calcVolume() + "]";
    }
}
