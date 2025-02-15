package shapes;

/*
 * Class representing a Square Prism.
 */
public class SquarePrism extends Shape {
    private double edgeLength;

    public SquarePrism(double height, double edgeLength) {
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
        return calcBaseArea() * getHeight();
    }

    @Override
    public String toString() {
        return "SquarePrism [Height=" + getHeight() + ", Edge Length=" + edgeLength + ", Base Area=" + calcBaseArea() + ", Volume=" + calcVolume() + "]";
    }
}
