/**
 * An unmodifiable point in the three-dimensional space. The coordinates are specified by exactly three doubles (its
 * <code>x</code>, <code>y</code>, and <code>z</code> values).
 */
public class ThreeDPoint implements Point {
    private double x;
    private double y;
    private double z;

    public ThreeDPoint(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @return the (x,y,z) coordinates of this point as a <code>double[]</code>.
     */
    @Override
    public double[] coordinates() {
        double[] arr = new double[3];
        arr[0] = x;
        arr[1] = y;
        arr[2] = z;
        return arr;
    }

    @Override
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
