import java.util.ArrayList;
import java.util.List;

/**
 * An unmodifiable point in the standard two-dimensional Euclidean space. The coordinates of such a point is given by
 * exactly two doubles specifying its <code>x</code> and <code>y</code> values.
 */
public class TwoDPoint implements Point {
    public double x;
    public double y; //These two were originally private

    public TwoDPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the coordinates of this point as a <code>double[]</code>.
     */
    @Override
    public double[] coordinates() {
        double[] arr = new double[2];
        arr[0] = x;
        arr[1] = y;
        return arr;
    }

    public boolean equals(Object o)
    {
        if (o instanceof TwoDPoint)
        {
            return ((TwoDPoint) o).x == this.x && ((TwoDPoint) o).y == this.y;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns a list of <code>TwoDPoint</code>s based on the specified array of doubles. A valid argument must always
     * be an even number of doubles so that every pair can be used to form a single <code>TwoDPoint</code> to be added
     * to the returned list of points.
     *
     * @param coordinates the specified array of doubles.
     * @return a list of two-dimensional point objects.
     * @throws IllegalArgumentException if the input array has an odd number of doubles.
     */
    public static List<TwoDPoint> ofDoubles(double... coordinates) throws IllegalArgumentException {
        if (coordinates.length % 2 == 1)
        {
            throw new IllegalArgumentException();
        }
        else
        {
            ArrayList<TwoDPoint> arr = new ArrayList<>(coordinates.length / 2 + 1);
            for (int i = 0; i < coordinates.length; i += 2)
            {
                arr.add(new TwoDPoint(coordinates[i], coordinates[i + 1]));
            }
            return arr;
        }
    }

    public int compareTo(Point o) {
        double thisd = 0;
        for (double x : coordinates())
        {
            thisd += Math.pow(x,2);
        }
        thisd = Math.sqrt(thisd);
        double othrd = 0;
        for (double x : o.coordinates())
        {
            othrd += Math.pow(x,2);
        }
        othrd = Math.sqrt(othrd);
        return thisd > othrd ? 1 : thisd == othrd ? 0 : -1;
    }
}
