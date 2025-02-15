package shapes;
/**
 * Class representing a Prism.
 * @author CRacicot
 */
public abstract class Prism extends Shape
{
	//attribute
	private double side;
	
	/**
	 * Creates a Prism with specified height and side values.
	 * @param height height
	 * @param side side
	 */
	
	public Prism(double height, double side) {
		super(height);
		this.side = side;
	}

	/**
	 * Returns the side.
	 * @return side
	 */

	public double getSide()
	{
		return side;
	}

	@Override
	public abstract double calcBaseArea();
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public double calcVolume()
	{
		return calcBaseArea() * getHeight();
	}
}
