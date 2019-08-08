import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> root;

    // construct an empty set of points
    public PointSET() {
        root = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return root.size() == 0;
    }

    // number of points in the set
    public int size() {
        return root.size();
    }

    public void insert(Point2D p) {
        if (!contains(p)) {
            root.add(p);
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return root.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        Iterator<Point2D> pointIterator = root.iterator();

        while (pointIterator.hasNext()) {
            pointIterator.next().draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        Iterable<Point2D> inside = new LinkedList<Point2D>();
        Iterator<Point2D> pointIterator = root.iterator();

        while (pointIterator.hasNext()) {
            Point2D point = pointIterator.next();
            if (rect.contains(point))
                ((LinkedList<Point2D>) inside).add(point);
        }

        return inside;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        Iterable<Point2D> inside = new LinkedList<Point2D>();
        Iterator<Point2D> pointIterator = root.iterator();

        Point2D closest = pointIterator.next();
        double closestDist = closest.distanceTo(p);

        while (pointIterator.hasNext()) {
            Point2D curPoint = pointIterator.next();
            double distToP = curPoint.distanceTo(p);
            if (distToP < closestDist) {
                closest = curPoint;
                closestDist = distToP;
            }
        }

        return closest;
    }

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}

