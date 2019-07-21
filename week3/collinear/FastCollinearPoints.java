import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.*;
import java.lang.*;
public class FastCollinearPoints {
    private ArrayList<LineSegment> segments;
    private Point lastLargestPoint = null;
    private double lastSlopeToLargest = Double.NaN;

    public FastCollinearPoints(Point[] points) {     // finds all line segments containing 4 points or more points
        segments = new ArrayList<LineSegment>();

        // Sort the array in natural order first to maintain stability of sorting slopes later
        Arrays.sort(points);

        // Need to make a copy of the point array
        Point[] cpy = points.clone();

        // Loop through the copy array
        for (int i = 0; i < cpy.length; i++) {
            // Origin is the current item
            Point origin = points[i];

            // Sort the array base on slope on point p
            Arrays.sort(cpy, origin.slopeOrder());

            // Loop through array finding 4 or more matching slopes with point p
            findConsecutiveSlopes(cpy, origin);
        }
    }

    private void findConsecutiveSlopes(Point[] points, Point origin) {

        int consecutiveSlopes = 0;
        Point largestPoint = null;
        double prevSlope = Double.NaN;

        for (int i = 0; i < points.length; i++) {
            // Skip point because it is the same point(?)

            // Get the slope from the origin to to another point
            double curSlope = origin.slopeTo(points[i]);

            // If first run through then set the slope to one slope because we have one slope calculated
            if (Double.isNaN(prevSlope)) {
                prevSlope = curSlope;
                largestPoint = points[i];
            }

            // Only want to compare points that greater than the origin because we don't want duplicates
            if (origin.compareTo(points[i]) == 1 && i != points.length - 1)
                continue;
            
            // If the current slope doesn't match that means we either have a collinear or don't
            if (curSlope != prevSlope) {
                // There would be 3 slopes in total if the points are collinear
                if (consecutiveSlopes >= 3) {
                    // We get the point from the start to the end
                    // Thinking: If we compare the start and end and if the start is greater than the end we don't connect because it's already been connected earlier in the loop
                    if (!isStartGreaterEqualToEnd(origin, largestPoint) && !isSubsegment(prevSlope, largestPoint)) {
                        makeCollinearSegment(origin, largestPoint);
                        lastLargestPoint = largestPoint;
                        lastSlopeToLargest = prevSlope;
                        consecutiveSlopes = 1;
                    }
                }
                
                // If there is no collinear reset consecutive counter
                else {
                    consecutiveSlopes = 1;
                }

                largestPoint = points[i];
            }

            // If the slopes match
            else {
                // 
                if (points[i].compareTo(largestPoint) == 1) {
                    largestPoint = points[i];
                }
                consecutiveSlopes++;
            }

            // If last element in the loop
            if (i == points.length - 1 && consecutiveSlopes >= 3) {
                if (!isStartGreaterEqualToEnd(origin, largestPoint) && !isSubsegment(prevSlope, largestPoint)) {
                    makeCollinearSegment(origin, largestPoint);
                    lastLargestPoint = largestPoint;
                    lastSlopeToLargest = prevSlope;
                }

                consecutiveSlopes = 0;
            }
            
            System.out.println("Origin: " + origin.toString() + " In question " + points[i].toString() + " Prev slope: " + prevSlope + " Cur slope: " + curSlope + " Consective: " + consecutiveSlopes);

            prevSlope = curSlope;
        }

        System.out.println();
    }

    private boolean isSubsegment(double curSlope, Point largestPoint) {
        if (Double.isNaN(lastSlopeToLargest) && lastLargestPoint == null) {
            return false;
        }

        System.out.println("lastSlopeToLargest: " + lastSlopeToLargest + " lastLargestPoint: " + lastLargestPoint.toString() + " curSlope: " + curSlope + " largestPoint" + largestPoint.toString());
        // ***FLOATING POINT COMPARISON ERROR FOR SOME NUMBERS***
        if (Double.compare(lastSlopeToLargest, curSlope) == 0 && largestPoint.compareTo(lastLargestPoint) == 0) {
            return true;
        }

        return false;
    }

    private boolean isStartGreaterEqualToEnd(Point start, Point end) {
        if (end.compareTo(start) == 1 || end.compareTo(start) == 0)
            return false;
        
        return true;
    }

    private void makeCollinearSegment(Point start, Point end) {
        segments.add(new LineSegment(start, end));
        System.out.println("Made " + start.toString() + " -> " + end.toString());
    }

    public int numberOfSegments() {                  // the number of line segments
        return segments.size();
    }

    public ArrayList<LineSegment> segments() {                // the line segments
        return segments;
    }

    public static void main(String[] args) {
        /*Point origin = new Point(0, 0);
        Point a = new Point(1, 1);
        Point b = new Point(2, 2);
        Point c = new Point(3, 3);
        Point d = new Point(4, 4);
        Point e = new Point(-1, 0);
        Point f = new Point(-2, 0);
        Point g = new Point(-3,0);
        Point h = new Point(-3,1);
        Point hi = new Point(-3,2);
        Point j = new Point(-3,3);
        Point k = new Point(-3,4);

        Point[] points = {origin, a, b, c, d, e};

        FastCollinearPoints test = new FastCollinearPoints(points);
        System.out.println("Number of segments: " + test.numberOfSegments());

        for (LineSegment segment : test.segments()) {
            StdOut.println(segment);
        }*/
        
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
    
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        
        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        
    }
}
