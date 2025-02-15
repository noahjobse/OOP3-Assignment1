package shapes;

/*
 * Class representing a Pyramid.
 * @author CRacicot
 */
public class Pyramid extends Shape {
    private double edgeLength;

    public Pyramid(double height, double edgeLength) {
        super(height);
        this.edgeLength = edgeLength;
    }

    public double getEdgeLength() {
        return edgeLength;
    }

    @Override
    public double calcBaseArea() {
        return edgeLength * edgeLength;
    }

    @Override
    public double calcVolume() {
        return (calcBaseArea() * getHeight()) / 3;
    }

    @Override
    public String toString() {
        return "Pyramid [Height=" + getHeight() + ", Edge Length=" + edgeLength + ", Base Area=" + calcBaseArea() + ", Volume=" + calcVolume() + "]";
    }
}