import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments;
    private int arrIndex = 0;

    public FastCollinearPoints(Point[] points) {     // finds all line segments containing 4 points or more points
        segments = new LineSegment[points.length * points.length];
        Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            // Need to make a copy of the point array
            Point[] cpy = points.clone();

            // Sort the array base on slope on point p
            Arrays.sort(cpy, points[i].slopeOrder());

            // Loop through array finding 4 or more matching slopes with point p
            double prevSlope = Double.NaN;
            int consecutiveSlopes = 0;
            Point start = points[i];
            Point end = null;
            for (int j = 0; j < cpy.length; j++) {

                // Skip point because it is the same point
                if (points[i].compareTo(cpy[j]) == 0) {
                    System.out.println("Skip because same point");
                    continue;
                }

                // Get the slope from the origin to to another point
                double curSlope = points[i].slopeTo(cpy[j]);
                System.out.println("Origin: " + start.toString() + " CurSlope: " + curSlope + " PrevSlope: " + prevSlope + " Consecutive: " + consecutiveSlopes);

                // If first run through then set the slope to one slope because we have one slope calculated
                if (Double.isNaN(prevSlope)) {
                    prevSlope = curSlope;
                }

                // If the current slope doesn't match that means we either have a collinear or don't
                if (curSlope != prevSlope || j == cpy.length - 1) {

                    // There would be 3 slopes in total if the points are collinear
                    if (consecutiveSlopes >= 3) {
                        if (j == cpy.length - 1 && curSlope == prevSlope) {
                            System.out.println("here");
                            segments[arrIndex++] = new LineSegment(start, cpy[j]);
                        }

                        // We get the point from the start to the end
                        // Thinking: If we compare the start and end and if the start is greater than the end we don't connect because it's already been connected earlier in the loop
                        else {
                            if (cpy[j - 1].compareTo(start) != -1) {
                                segments[arrIndex++] = new LineSegment(start, cpy[j - 1]);
                                System.out.println(segments[arrIndex - 1].toString());
                            }
                        }
                    }

                    consecutiveSlopes = 1;
                } else if (curSlope == prevSlope) {
                    consecutiveSlopes++;
                }
                prevSlope = curSlope;
            }

            // Printing to debug
            for (int j = 0; j < points.length; j++) {
                System.out.println(cpy[j].toString());
            }

            System.out.println();


        }
    }

    public int numberOfSegments() {                  // the number of line segments
        return arrIndex;
    }

    public LineSegment[] segments() {                // the line segments
        return segments;
    }

    public static void main(String[] args) {
        Point origin = new Point(0, 0);
        Point a = new Point(1, 1);
        Point b = new Point(2, 2);
        Point c = new Point(3, 3);
        Point d = new Point(-1, 0);
        Point e = new Point(-2, -2);

        Point[] points = {origin, a, b, c, d, e};

        FastCollinearPoints test = new FastCollinearPoints(points);
        System.out.println(test.numberOfSegments());
    }
}
