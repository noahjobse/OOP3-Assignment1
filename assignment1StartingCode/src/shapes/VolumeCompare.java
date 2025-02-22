package shapes;

import java.util.Comparator;

import sorts.Sort;

public class VolumeCompare implements Comparator<Shape> {

    @Override
    public int compare(Shape s1, Shape s2) {
        return Double.compare(s1.calcVolume(), s2.calcVolume());
    }

    /**
     * Sorts the shapes array based on the comparison type.
     * Uses bubble sort for sorting based on height, base area, or volume.
     */
    public static void sortShapes(Shape[] shapes, char compareType, char sortType) {
        if (sortType == 'b' || sortType == 'B') { // Bubble sort
            if (compareType == 'h' || compareType == 'H') {
                Sort.bubbleSort(shapes); // Sort by height (default compareTo)
            } else if (compareType == 'a' || compareType == 'A') {
                BaseAreaCompare bac = new BaseAreaCompare();
                Sort.bubbleSort(shapes, bac); // Sort by base area
            } else if (compareType == 'v' || compareType == 'V') {
                VolumeCompare vc = new VolumeCompare();
                Sort.bubbleSort(shapes, vc); // Sort by volume
            }
        }
    }
}
