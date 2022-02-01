package decide;

import org.junit.jupiter.api.Test;

import static decide.MathsHelper.minCircleRadius;
import static decide.MathsHelper.triangleArea;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MathsHelperTest {

    @Test
    void triangleAreaTest() {
        assertEquals(triangleArea(new Point(1,2), new Point(3,6), new Point(8,9)), 7);
        assertEquals(triangleArea(new Point(1,2), new Point(4,8), new Point(16,32)), 0);
    }

    @Test
    void minCircleRadiusTest() {
        Point a = new Point(-3, 4);
        Point b = new Point(4, 5);
        Point c = new Point(1, -4);
        assertEquals(minCircleRadius(a, b, c), 5);

        a = new Point(3, 5);
        b = new Point(2, 6);
        c = new Point(-4, -2);
        assertEquals(minCircleRadius(a, b, c), 5);

        // obtuse triangle, (longest triangle edge)*0.5 should be returned
        a = new Point(0, 0);
        b = new Point(4, 0);
        c = new Point(8, 2);
        double ab = a.distanceTo(b);
        double ac = a.distanceTo(c);
        double bc = b.distanceTo(c);
        double maxDist = Math.max(ab, Math.max(ac, bc));
        assertEquals(minCircleRadius(a, b, c), maxDist*0.5);

        // line
        a = new Point(0, 1);
        b = new Point(0, 2);
        c = new Point(0, 3);
        assertEquals(minCircleRadius(a, b, c), 1);

        // coincide
        a = new Point(0, 0);
        b = new Point(0, 0);
        c = new Point(0, 0);
        assertEquals(minCircleRadius(a, b, c), -1);
    }
}