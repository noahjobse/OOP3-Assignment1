package shapes;

/**
 * Class representing a Cone.
 * 
 * @author CRacicot
 */
public class Cone extends Shape {
    private double radius;

    public Cone(double height, double radius) {
        super(height);
        this.radius = radius;
    }

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
        return "Cone [Height=" + getHeight() + ", Radius=" + radius + ", Base Area=" + calcBaseArea() + ", Volume="
                + calcVolume() + "]";
    }
}
