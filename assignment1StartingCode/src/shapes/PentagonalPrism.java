package shapes;

/**
 * Class representing a Pentagonal Prism.
 * 
 * @author CRacicot
 */
public class PentagonalPrism extends Shape {
    private double edgeLength;

    public PentagonalPrism(double height, double edgeLength) {
        super(height);
        this.edgeLength = edgeLength;
    }

    public double getEdgeLength() {
        return edgeLength;
    }

    @Override
    public double calcBaseArea() {
        return (5 * edgeLength * edgeLength * Math.tan(Math.toRadians(54))) / 4;
    }

    @Override
    public double calcVolume() {
        return calcBaseArea() * getHeight();
    }

    @Override
    public String toString() {
        return "PentagonalPrism [Height=" + getHeight() + ", Edge Length=" + edgeLength + ", Base Area="
                + calcBaseArea() + ", Volume=" + calcVolume() + "]";
    }
}
