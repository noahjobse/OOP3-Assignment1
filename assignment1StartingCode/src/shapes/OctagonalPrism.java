package shapes;

/*
 * Class representing an Octagonal Prism.
 */
public class OctagonalPrism extends Shape {
    private double edgeLength;

    public OctagonalPrism(double height, double edgeLength) {
        super(height);
        this.edgeLength = edgeLength;
    }

    public double getEdgeLength() {
        return edgeLength;
    }

    @Override
    public double calcBaseArea() {
        return 2 * (1 + Math.sqrt(2)) * edgeLength * edgeLength;
    }

    @Override
    public double calcVolume() {
        return calcBaseArea() * getHeight();
    }

    @Override
    public String toString() {
        return "OctagonalPrism [Height=" + getHeight() + ", Edge Length=" + edgeLength + ", Base Area=" + calcBaseArea() + ", Volume=" + calcVolume() + "]";
    }
}
