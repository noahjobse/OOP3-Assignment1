package shapes;

/*
 * Class representing a Triangular Prism.
 * @author CRacicot
 */
public class TriangularPrism extends Shape {
    private double edgeLength;

    public TriangularPrism(double height, double edgeLength) {
        super(height);
        this.edgeLength = edgeLength;
    }

    public double getEdgeLength() {
        return edgeLength;
    }

    @Override
    public double calcBaseArea() {
        return (Math.sqrt(3) / 4) * edgeLength * edgeLength;
    }

    @Override
    public double calcVolume() {
        return calcBaseArea() * getHeight();
    }

    @Override
    public String toString() {
        return "TriangularPrism [Height=" + getHeight() + ", Edge Length=" + edgeLength + ", Base Area=" + calcBaseArea() + ", Volume=" + calcVolume() + "]";
    }
}
