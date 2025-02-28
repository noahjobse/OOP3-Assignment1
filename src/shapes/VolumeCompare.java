package shapes;

import java.util.Comparator;

import sorts.Sort;

/**
 * Class representing a Comparator for comparing shapes based on their volume.
 * 
 * @author CRacicot
 */
public class VolumeCompare implements Comparator<Shape> {

    @Override
    public int compare(Shape s1, Shape s2) {
        // Compare in descending order: larger volume comes first
        return Double.compare(s2.calcVolume(), s1.calcVolume());
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
