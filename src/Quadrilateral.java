import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Quadrilateral implements TwoDShape, Positionable {

    public List<TwoDPoint> vertices;

    public Quadrilateral(List<TwoDPoint> vertices) {
        this.vertices = vertices;
    }

    /**
     * Sets the position of this quadrilateral according to the first four elements in the specified list of points. The
     * quadrilateral is formed on the basis of these four points taken in a clockwise manner on the two-dimensional
     * x-y plane. If the input list has more than four elements, the subsequent elements are ignored.
     *
     * @param points the specified list of points.
     */
    @Override
    public void setPosition(List<? extends Point> points) {
        if (points.size() < numSides())
        {
            throw new IllegalArgumentException();
        }
        else if (points.get(0).getClass() == TwoDPoint.class)
        {
            for (int i = 0; i < points.size(); i++)
            {
                if (i > numSides())
                {
                    break;
                }
                vertices.set(i, (TwoDPoint) points.get(i));
            }
        }
    }

    /**
     * Retrieve the position of an object as a list of points. The points are be retrieved and added to the returned
     * list in a clockwise manner on the two-dimensional x-y plane, starting with the point with the least x-value. If
     * two points have the same least x-value, then the clockwise direction starts with the point with the lower y-value.
     *
     * @return the retrieved list of points.
     */
    @Override
    public List<? extends TwoDPoint> getPosition() {
        double[] least = vertices.get(0).coordinates();
        int min = 0;
        for (int i = 1; i < vertices.size(); i++)
        {
            if ((least[0] > vertices.get(i).x) || (least[0] == vertices.get(i).x && least[1] > vertices.get(i).y))
            {
                least = vertices.get(i).coordinates();
                min = i;
            }
        }

        ArrayList<TwoDPoint> pos = new ArrayList<>(4);
        for (int i = min; i < vertices.size() + min; i++)
        {
            pos.add(vertices.get(i % vertices.size()));
        }
        return pos;
    }

    /**
     * @return the number of sides of this quadrilateral, which is always set to four
     */
    @Override
    public int numSides() {
        return 4;
    }

    /**
     * Checks whether or not a list of vertices forms a valid quadrilateral. The <i>trivial</i> quadrilateral, where all
     * four corner vertices are the same point, is considered to be an invalid quadrilateral.
     *
     * @param vertices the list of vertices to check against, where each vertex is a <code>Point</code> type.
     * @return <code>true</code> if <code>vertices</code> is a valid collection of points for a quadrilateral, and
     * <code>false</code> otherwise. For example, if three of the four vertices are in a straight line is invalid.
     */
    @Override
    public boolean isMember(List<? extends Point> vertices) {
        if (vertices.size() < numSides())
        {
            return false;
        }
        else if (!(vertices.get(0) instanceof TwoDPoint))
        {
            return false;
        }
        else {
            HashSet<Line> lines = new HashSet<>();
            for (int i = 0; i < vertices.size(); i++) {
                for (int j = 0; j < vertices.size(); j++) {
                    lines.add(new Line((TwoDPoint) vertices.get(i), (TwoDPoint) vertices.get(j)));
                    if (lines.size() > numSides()) {
                        return false;
                    }
                }
            }
            int x = 0;
            int y = 0;
            ArrayList<Double> slopes = new ArrayList<Double>(lines.size());
            for (Line a : lines)
            {
                slopes.add(a.slope());
            }

            for (double num : slopes)
            {
                if (num == slopes.get(0))
                {
                    x++;
                }
                else if (num == slopes.get(slopes.size() - 1))
                {
                    y++;
                }
            }
            return y == x && x == 2;
        }
    }

    /**
     * This method snaps each vertex of this quadrilateral to its nearest integer-valued x-y coordinate. For example, if
     * a corner is at (0.8, -0.1), it will be snapped to (1,0). The resultant quadrilateral will thus have all four
     * vertices in positions with integer x and y values. If the snapping procedure described above results in this
     * quadrilateral becoming invalid (e.g., all four corners collapse to a single point), then it is left unchanged.
     * Snapping is an in-place procedure, and the current instance is modified.
     */
    public void snap() {
        ArrayList<TwoDPoint> bac = new ArrayList<TwoDPoint>(4);
        for (int i = 0; i < vertices.size(); i++)
        {
            bac.add(vertices.get(i));
            vertices.get(i).x = Math.round(vertices.get(i).x);
            vertices.get(i).y = Math.round(vertices.get(i).y);
        }

        boolean isQuat = true;
        for (int i = 0; i < vertices.size() && isQuat; i++)
        {
            for (int j = i + 1; j < vertices.size() && isQuat; j++)
            {
                if (vertices.get(i).equals(vertices.get(j)))
                {
                    isQuat = true;
                }
            }
        }

        if (!isQuat)
        {
            vertices = bac;
        }
    }

    /**
     * @return the area of this quadrilateral
     */
    public double area() {
        return Math.abs(
                (vertices.get(1).x - vertices.get(0).x) * (vertices.get(3).y - vertices.get(0).y) -
                        (vertices.get(3).x - vertices.get(0).x) * (vertices.get(1).y - vertices.get(0).y));
    }

    /**
     * @return the perimeter (i.e., the total length of the boundary) of this quadrilateral
     */
    public double perimeter() {
        return sqrt(po(vertices.get(0).x - vertices.get(0).x) + po(vertices.get(0).y - vertices.get(1).y)) +
                sqrt(po(vertices.get(1).x - vertices.get(2).x) + po(vertices.get(1).y - vertices.get(2).y)) +
                sqrt(po(vertices.get(2).x - vertices.get(3).x) + po(vertices.get(2).y - vertices.get(3).y)) +
                sqrt(po(vertices.get(0).x - vertices.get(3).x) + po(vertices.get(0).y - vertices.get(3).y));
    }

    public int compareTo(TwoDShape o) {
        return this.area() > o.area() ? 1 : area() == o.area() ? 0 : -1;
    }

    static double po(double x)
    {
        return x * x;
    }
    static double sqrt(double x) {return Math.sqrt(x);}
}
