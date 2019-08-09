import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.LinkedList;

public class KdTree {
    // construct an empty set of points
    private Node root;
    private Node lastInsertedNode;
    private int size;
    private int num;

    public KdTree() {
        size = 0;
        root = null;
        lastInsertedNode = null;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException("The argument cannot be null");
        // If root is null then create the first node
        root = put(root, p, true);
    }

    private Node put(Node x, Point2D point, boolean isVertical) {
        if (x == null) {
            Node newNode = new Node();
            newNode.point = point;
            newNode.left = null;
            newNode.right = null;

            if (isVertical)
                newNode.rectangle = new RectHV(point.x(), 0, point.x(), 1);

            else
                newNode.rectangle = new RectHV(0, point.y(), 1, point.y());

            lastInsertedNode = newNode;
            size++;
            return newNode;
        }

        if (x.point.compareTo(point) == 0) {
            // System.out.println("Dupe prevented");
            return x;
        }

        if (isVertical) {
            double nodeKey = x.point.x();
            double pointKey = point.x();

            if (pointKey < nodeKey) {
                x.left = put(x.left, point, false);

                if (x.rectangle.intersects(lastInsertedNode.rectangle)) {
                    lastInsertedNode.rectangle = new RectHV(lastInsertedNode.rectangle.xmin(),
                                                            lastInsertedNode.rectangle.ymin(),
                                                            x.rectangle.xmax(),
                                                            lastInsertedNode.rectangle.ymax());
                }
            }

            else {
                x.right = put(x.right, point, false);

                if (x.rectangle.intersects(lastInsertedNode.rectangle)) {
                    lastInsertedNode.rectangle = new RectHV(x.rectangle.xmin(),
                                                            lastInsertedNode.rectangle.ymin(),
                                                            lastInsertedNode.rectangle.xmax(),
                                                            lastInsertedNode.rectangle.ymax());
                }
            }
        }

        else {
            double nodeKey = x.point.y();
            double pointKey = point.y();

            if (pointKey < nodeKey) {
                x.left = put(x.left, point, true);

                if (x.rectangle.intersects(lastInsertedNode.rectangle)) {
                    lastInsertedNode.rectangle = new RectHV(lastInsertedNode.rectangle.xmin(),
                                                            lastInsertedNode.rectangle.ymin(),
                                                            lastInsertedNode.rectangle.xmax(),
                                                            x.rectangle.ymax());
                }
            }

            else {
                x.right = put(x.right, point, true);

                if (x.rectangle.intersects(lastInsertedNode.rectangle)) {
                    lastInsertedNode.rectangle = new RectHV(lastInsertedNode.rectangle.xmin(),
                                                            x.rectangle.ymin(),
                                                            lastInsertedNode.rectangle.xmax(),
                                                            lastInsertedNode.rectangle.ymax());
                }
            }
        }

        return x;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException("The argument cannot be null");

        Node trav = root;

        while (trav != null) {
            if (p.compareTo(trav.point) == 0)
                return true;

            if (isHorizontal(trav.rectangle)) {
                if (p.y() < trav.point.y())
                    trav = trav.left;

                else
                    trav = trav.right;
            }

            else {
                if (p.x() < trav.point.x())
                    trav = trav.left;

                else
                    trav = trav.right;
            }
        }

        return false;
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setXscale(0, 1.0);
        StdDraw.setYscale(0, 1.0);

        inorderDraw(root);
    }

    private void inorderDraw(Node x) {
        if (x == null)
            return;

        inorderDraw(x.left);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        x.point.draw();

        StdDraw.setPenRadius();
        if (isHorizontal(x.rectangle))
            StdDraw.setPenColor(StdDraw.BLUE);

        else
            StdDraw.setPenColor(StdDraw.RED);

        x.rectangle.draw();

        inorderDraw(x.right);
    }

    private boolean isHorizontal(RectHV rectangle) {
        double slope = (rectangle.ymax() - rectangle.ymin()) / (rectangle.xmax() - rectangle
                .xmin());

        return slope == 0;
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new java.lang.IllegalArgumentException("The argument cannot be null");

        LinkedList<Point2D> candidates = new LinkedList<Point2D>();
        getRange(root, rect, candidates);
        return candidates;
    }

    private void getRange(Node x, RectHV rect, LinkedList<Point2D> list) {
        if (x == null)
            return;

        if (rect.contains(x.point))
            list.add(x.point);

        if (rect.intersects(x.rectangle)) {
            // Search both sides
            getRange(x.left, rect, list);
            getRange(x.right, rect, list);
        }

        else {
            // Search on left or right depending where the rectangle lies left/right or top/bottom
            if (isHorizontal(x.rectangle)) {
                if (rect.ymax() < x.point.y())
                    getRange(x.left, rect, list);

                else
                    getRange(x.right, rect, list);
            }

            else {
                if (rect.xmax() < x.point.x())
                    getRange(x.left, rect, list);

                else
                    getRange(x.right, rect, list);
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException("The argument cannot be null");

        if (isEmpty())
            return null;

        Point2D champion = root.point;
        Double championDist = p.distanceTo(root.point);
        return closest(root, p, champion, championDist);
    }

    private Point2D closest(Node x, Point2D p, Point2D champion, Double champDist) {

        if (x == null)
            return champion;

        Point2D contender;

        // is x's rectange < champDist then search for point in left/right subtree
        if (p.distanceTo(x.point) < champDist) {
            champion = x.point;
            champDist = p.distanceTo(x.point);
            //System.out.println("New Champ: " + x.point.toString() + " Dist: " + champDist);
        }

        // If horizontal compare only using y coord
        if (isHorizontal(x.rectangle)) {
            if (p.y() < x.rectangle.ymax()) {
                contender = closest(x.left, p, champion, p.distanceTo(champion));
                if (p.distanceTo(contender) < champDist) {
                    if (x.rectangle.distanceTo(p) > p.distanceTo(contender))
                        return contender;

                    champion = contender;
                    champDist = p.distanceTo(champion);
                }

                contender = closest(x.right, p, champion, p.distanceTo(champion));
                if (p.distanceTo(contender) < champDist)
                    return contender;
            }

            else {
                contender = closest(x.right, p, champion, p.distanceTo(champion));
                if (p.distanceTo(contender) < champDist) {
                    if (x.rectangle.distanceTo(p) > p.distanceTo(contender))
                        return contender;

                    champion = contender;
                    champDist = p.distanceTo(champion);
                }

                contender = closest(x.left, p, champion, p.distanceTo(champion));
                if (p.distanceTo(contender) < champDist)
                    return contender;
            }
        }

        // If vertical compare only using x coord
        else {
            if (p.x() < x.rectangle.xmax()) {
                contender = closest(x.left, p, champion, p.distanceTo(champion));
                if (p.distanceTo(contender) < champDist) {
                    if (x.rectangle.distanceTo(p) > p.distanceTo(contender))
                        return contender;

                    champion = contender;
                    champDist = p.distanceTo(champion);
                }

                contender = closest(x.right, p, champion, p.distanceTo(champion));
                if (p.distanceTo(contender) < champDist)
                    return contender;
            }

            else {
                contender = closest(x.right, p, champion, p.distanceTo(champion));
                if (p.distanceTo(contender) < champDist) {
                    if (x.rectangle.distanceTo(p) > p.distanceTo(contender))
                        return contender;

                    champion = contender;
                    champDist = p.distanceTo(champion);
                }

                contender = closest(x.left, p, champion, p.distanceTo(champion));
                if (p.distanceTo(contender) < champDist)
                    return contender;
            }
        }

        // if a better champion is found don't search the other subtree
        // if did not find a better point search the other tree

        return champion;

        // Point2D contender;
        //
        // if (x == null) {
        //     return champion;
        // }
        //
        // if (p.distanceTo(x.point) < champDist) {
        //     champion = x.point;
        //     champDist = p.distanceTo(x.point);
        // }
        //
        // if (p.y() < x.point.y() || p.x() < x.point.x()) {
        //
        //     if (x.left != null && x.left.rectangle.distanceTo(p) < champDist) {
        //         contender = closest(x.left, p, champion, champDist);
        //         if (p.distanceTo(contender) < champDist) {
        //             return contender;
        //         }
        //     }
        //
        //     if (x.right != null && x.right.rectangle.distanceTo(p) < champDist) {
        //         contender = closest(x.right, p, champion, champDist);
        //         if (p.distanceTo(contender) < champDist) {
        //             return contender;
        //         }
        //     }
        // }
        //
        // else {
        //     if (x.right != null && x.right.rectangle.distanceTo(p) < champDist) {
        //         contender = closest(x.right, p, champion, champDist);
        //         if (p.distanceTo(contender) < champDist) {
        //             return contender;
        //         }
        //     }
        //
        //     if (x.left != null && x.left.rectangle.distanceTo(p) < champDist) {
        //         contender = closest(x.left, p, champion, champDist);
        //         if (p.distanceTo(contender) < champDist) {
        //             return contender;
        //         }
        //     }
        // }
        //
        // return champion;
    }

    private boolean isOtherRecCloser(Node x, Point2D p, double distance) {
        if (x == null)
            return false;

        if (x.rectangle.distanceTo(p) < distance)
            return true;

        return false;
    }

    private static class Node {
        public Point2D point;
        public Node left;
        public Node right;
        public RectHV rectangle;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
