import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;
    private int arrIndex = 0;

    public BruteCollinearPoints(Point[] points) {     // finds all line segments containing 4 points
        if (points == null) {
            throw new java.lang.IllegalArgumentException("No argument in BruteCollinearPoints arguments");
        }

        Arrays.sort(points);

        segments = new LineSegment[points.length];
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double slopeA = points[i].slopeTo(points[j]);

                for (int k = j + 1; k < points.length; k++) {
                    double slopeB = points[j].slopeTo(points[k]);

                    if (slopeA != slopeB)
                        continue;

                    for (int m = k + 1; m < points.length; m++) {
                        double slopeC = points[k].slopeTo(points[m]);

                        if (slopeA != slopeC)
                            continue;

                        LineSegment newLineSeg = new LineSegment(points[i], points[m]);
                        segments[arrIndex++] = newLineSeg;
                    }
                }
            }
        }
    }

    public int numberOfSegments() {                  // the number of line segments
        return arrIndex;
    }

    public LineSegment[] segments() {                // the line segments
        return segments;
    }

    public static void main(String[] args) {
        Point a = new Point(0, 0);
        Point b = new Point(1, 1);
        Point c = new Point(2, 2);
        Point d = new Point(3, 3);
        Point e = new Point(4, 4);
        Point f = new Point(2, 0);
        Point g = new Point(2, -4);
        Point h = new Point(2, -6);
        Point hi = new Point(4, -2);
        Point j = new Point(5, -4);
        Point k = new Point(6, -6);
        Point l = new Point(4, 5);
        Point m = new Point(4, 10);
        Point[] arr = {d, a, c, b, h, f, g};

        BruteCollinearPoints test = new BruteCollinearPoints(arr);
        LineSegment[] seg = test.segments();
        System.out.println(test.numberOfSegments());
        for (int i = 0; i < test.numberOfSegments(); i++) {
            System.out.println(seg[i].toString());
        }
    }
}
