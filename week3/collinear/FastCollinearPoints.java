import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints 
{
    private ArrayList<LineSegment> segments;

    // finds all line segments containing 4 points or more points
    public FastCollinearPoints(Point[] points) 
    {     // finds all line segments containing 4 points or more points
        segments = new ArrayList<LineSegment>();

        // Check for any null inputs
        validateInput(points);

        // Sort the array in natural order first to maintain stability of sorting slopes later
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);

        // Check if the array contain duplicate points
        checkDuplicates(sortedPoints);

        // Need to make a copy of the point array
        Point[] cpy = sortedPoints.clone();

        // Loop through the copy array
        for (int i = 0; i < cpy.length; i++) {
            // Origin is the current item
            Point origin = points[i];

            // Sort the array base on slope on point p
            Arrays.sort(cpy, origin.slopeOrder());

            findConsecutiveSlopes(cpy, origin);
        }
    }

    // Finds consecutive slopes from the point origin and forms collinear if found
    private void findConsecutiveSlopes(Point[] points, Point origin) 
    {
        int consecutiveSlopes = 0;
        Point largestPoint = null;
        Point smallestPoint = null;
        double prevSlope = Double.NaN;

        for (int i = 0; i < points.length; i++) {
            
            // Get the slope from the origin to to another point
            double curSlope = origin.slopeTo(points[i]);

            // If first run through then set the slope to one consec. slope because we have one slope calculated
            if (Double.isNaN(prevSlope)) {
                prevSlope = curSlope;
                largestPoint = points[i];
                smallestPoint = points[i];
            }
            
            // If the current slope doesn't match that means we either have a collinear or don't
            if (curSlope != prevSlope) {
                // There would be 3 slopes in total if the points are collinear
                if (consecutiveSlopes >= 3) {
                    if (isOriginSmallest(origin, smallestPoint))
                        makeCollinearSegment(origin, largestPoint);
                }

                // If no collinear, reset for next possible collinear
                consecutiveSlopes = 1;
                smallestPoint = points[i];
                largestPoint = points[i];
            }

            // If the slopes match
            else {
                if (points[i].compareTo(largestPoint) > 0)
                    largestPoint = points[i];

                if (points[i].compareTo(smallestPoint) < 0)
                    smallestPoint = points[i];
                    
                consecutiveSlopes++;
            }

            // If last element in the loop check for collinear for the last element
            if (i == points.length - 1 && consecutiveSlopes >= 3) {
                if (isOriginSmallest(origin, smallestPoint)) 
                    makeCollinearSegment(origin, largestPoint);

                consecutiveSlopes = 0;
            }
            
            prevSlope = curSlope;
        }
    }

    private boolean isOriginSmallest(Point origin, Point test) 
    {
        if (origin.compareTo(test) < 0 || origin.compareTo(test) == 0)
            return true;

        return false;
    }

    private void makeCollinearSegment(Point start, Point end) 
    {
        segments.add(new LineSegment(start, end));
    }

    private void validateInput(Point[] points) 
    {
        if (points == null)
            throw new java.lang.IllegalArgumentException("The arguement cannot be null");

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new java.lang.IllegalArgumentException("The arguement cannot cannot contain a null");
        }
    }

    private void checkDuplicates(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new java.lang.IllegalArgumentException("No duplicate points allowed in the input");
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() 
    {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() 
    {
        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) 
    {
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
