import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Triangle implements TwoDShape, Positionable {

    List<TwoDPoint> vertices;

    public Triangle(List<TwoDPoint> vertices) {
        this.vertices = vertices;
    }

    /**
     * Sets the position of this triangle according to the first three elements in the specified list of points. The
     * triangle is formed on the basis of these three points taken in a clockwise manner on the two-dimensional
     * x-y plane. If the input list has more than three elements, the subsequent elements are ignored.
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

        List<TwoDPoint> pos = new ArrayList<TwoDPoint>(4);
        for (int i = min; i < vertices.size() + min; i++)
        {
            pos.add(vertices.get(i % vertices.size()));
        }
        return pos;
    }

    /**
     * @return the number of sides of this triangle, which is always set to three
     */
    @Override
    public int numSides() {
        return 3;
    }

    /**
     * Checks whether or not a list of vertices forms a valid triangle. The <i>trivial</i> triangle, where all three
     * corner vertices are the same point, is considered to be an invalid triangle.
     *
     * @param vertices the list of vertices to check against, where each vertex is a <code>Point</code> type.
     * @return <code>true</code> if <code>vertices</code> is a valid collection of points for a triangle, and
     * <code>false</code> otherwise. For example, three vertices are in a straight line is invalid.
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
                    if (lines.size() > 3)
                    {
                        return false;
                    }
                }
            }
            return lines.size() == 3; // TODO
        }
    }

    /**
     * This method snaps each vertex of this triangle to its nearest integer-valued x-y coordinate. For example, if
     * a corner is at (0.8, -0.1), it will be snapped to (1,0). The resultant triangle will thus have all four
     * vertices in positions with integer x and y values. If the snapping procedure described above results in this
     * triangle becoming invalid (e.g., all corners collapse to a single point), then it is left unchanged. Snapping is
     * an in-place procedure, and the current instance is modified.
     */
    public void snap() {
        for (int i = 0; i < vertices.size(); i++)
        {
            vertices.get(i).x = Math.round(vertices.get(i).x);
            vertices.get(i).y = Math.round(vertices.get(i).y);
        }
    }

    /**
     * @return the area of this triangle
     */
    public double area() {
        return Math.abs(
                (vertices.get(2).x - vertices.get(0).x) * (vertices.get(1).y - vertices.get(0).y) -
                (vertices.get(1).x - vertices.get(0).x) * (vertices.get(2).y - vertices.get(0).y)
                ) / 2;
    }

    /**
     * @return the perimeter (i.e., the total length of the boundary) of this triangle
     */
    public double perimeter() {
        return Math.sqrt(
                po(vertices.get(0).x - vertices.get(1).x) + po(vertices.get(0).y - vertices.get(1).y))
                + Math.sqrt(po(vertices.get(0).x - vertices.get(2).x) + po(vertices.get(0).y - vertices.get(2).y))
                + Math.sqrt(po(vertices.get(1).x - vertices.get(2).x) + po(vertices.get(1).y - vertices.get(2).y));
    }

    public int compareTo(TwoDShape o) {
        return this.area() > o.area() ? 1 : area() == o.area() ? 0 : -1;
    }

    static double po(double x)
    {
        return x * x;
    }
}
