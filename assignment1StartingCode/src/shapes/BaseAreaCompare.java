package shapes;

import java.util.Comparator;

public class BaseAreaCompare implements Comparator<Shape> {

	@Override
	public int compare(Shape s1, Shape s2) {
		// In descending order, if s1 has a larger base area, it should come first,
		// so return -1 instead of +1.
		if (s1.calcBaseArea() > s2.calcBaseArea())
			return -1;
		if (s1.calcBaseArea() < s2.calcBaseArea())
			return +1;
		return 0;
	}
}
