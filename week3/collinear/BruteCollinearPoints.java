import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints 
{
    private ArrayList<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {     // finds all line segments containing 4 points
        
        validateInput(points);

        Point[] cpyArr = points.clone();

        Arrays.sort(cpyArr);

        checkDuplicates(cpyArr);

        segments = new ArrayList<LineSegment>();
        for (int i = 0; i < cpyArr.length; i++) {
            for (int j = i + 1; j < cpyArr.length; j++) {
                double slopeA = cpyArr[i].slopeTo(cpyArr[j]);

                for (int k = j + 1; k < cpyArr.length; k++) {
                    double slopeB = cpyArr[j].slopeTo(cpyArr[k]);

                    if (slopeA != slopeB)
                        continue;

                    for (int m = k + 1; m < cpyArr.length; m++) {
                        double slopeC = cpyArr[k].slopeTo(cpyArr[m]);

                        if (slopeA != slopeC)
                            continue;

                        LineSegment newLineSeg = new LineSegment(cpyArr[i], cpyArr[m]);
                        segments.add(newLineSeg);
                    }
                }
            }
        }
    }

    private void validateInput(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException("The arguement cannot be null");
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new java.lang.IllegalArgumentException("The arguement cannot cannot contain a null");
            }
        }
    }

    private void checkDuplicates(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new java.lang.IllegalArgumentException("No duplicate points allowed in the input");
                }
            }
        }
    }

    public int numberOfSegments() {                  // the number of line segments
        return segments.size();
    }

    public LineSegment[] segments() {                // the line segments
        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
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
