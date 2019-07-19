import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments;
    private int arrIndex = 0;

    public FastCollinearPoints(Point[] points) {     // finds all line segments containing 4 points or more points
        for (int i = 0; i < points.length; i++) {
            Point[] slopesToCurr = new Point[points.length - 1];
            int slopesToCurrIndex = 0;

            // Need to make a copy of the point array
            // Need to somehow remove point p (points[i]) from array every iteration to only have slope to point p
            // Need to have the loop to loop around the array to go through every point

            Arrays.sort(points, points[i].slopeOrder());

            for (int j = 0; j < points.length; j++) {
                System.out.println(points[j].toString());
            }

            System.out.println();

            // Check for 3 or more adjacent points for same slope

        }
    }

    public int numberOfSegments() {                  // the number of line segments
        return segments.length;
    }

    public LineSegment[] segments() {                // the line segments
        return segments;
    }

    public static void main(String[] args) {
        Point origin = new Point(0, 0);
        Point a = new Point(1, 1);
        Point b = new Point(2, 2);
        Point c = new Point(-1, 0);

        Point[] points = {origin, a, b, c};

        FastCollinearPoints test = new FastCollinearPoints(points);
    }
}
