package shapes;

import java.util.Comparator;

/**
 * Class representing a Comparator for comparing shapes based on their base
 * area.
 * 
 * @author CRacicot
 */
public class BaseAreaCompare implements Comparator<Shape> {

	@Override
	public int compare(Shape s1, Shape s2) {
		if (s1.calcBaseArea() > s2.calcBaseArea())
			return -1;
		if (s1.calcBaseArea() < s2.calcBaseArea())
			return +1;
		return 0;
	}
}
