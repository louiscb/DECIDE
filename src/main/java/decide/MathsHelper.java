package decide;

import java.util.Arrays;

public class MathsHelper {

    public static double triangleArea(Point a, Point b, Point c) {
        return Math.abs(0.5*(a.getX()* (b.getY()-c.getY()) +
                b.getX()* (c.getY() - a.getY()) +
                c.getX()* (a.getY() - b.getY())));
    }

    public static double minCircleRadius(Point a, Point b, Point c) {
        //Find the minimum radius required for a circle so that a, b and c to lie in or on it.
        double ab = a.distanceTo(b);
        double ac = a.distanceTo(c);
        double bc = b.distanceTo(c);

        if(ab == 0 && ac == 0 && bc == 0){ //The points coincide
            return -1;
        }
        else if (triangleArea(a, b, c) == 0) { // Check if a , b and c are collinear
            return Math.max(ab, Math.max(ac, bc)) * 0.5;
        }
        //Check if the triangle that a, b and c form is obtuse or right.
        // If that is the case the circle has the longest triangle edge as diameter.
        double[] array = {ab, ac, bc};
        Arrays.sort(array); // results in array[2] being the longest edge in the triangle.
        if (Math.pow(array[2],2) >= Math.pow(array[1],2) + Math.pow(array[0],2)) { //if obtuse
            return array[2]*0.5;
        }
        //Otherwise calculate the radius of the circum-circle for the points (where all the points are on the circle).
        double x1 = a.getX(), x2 = b.getX(), x3 = c.getX();
        double y1 = a.getY(), y2 = b.getY(), y3 = c.getY();
        double x1Squ = Math.pow(x1,2), x2Sqr = Math.pow(x2,2), x3Squ = Math.pow(x3,2);
        double y1Squ = Math.pow(y1,2), y2Squ = Math.pow(y2,2), y3Squ = Math.pow(y3,2);
        double A = x1*(y2-y3) - y1*(x2-x3) + x2*y3 - x3*y2;
        double B = (x1Squ + y1Squ) * (y3-y2) + (x2Sqr + y2Squ) * (y1-y3) + (x3Squ + y3Squ) * (y2-y1);
        double C = (x1Squ + y1Squ) * (x2-x3) + (x2Sqr + y2Squ) * (x3-x1) + (x3Squ  + y3Squ) * (x1-x2);
        double D = (x1Squ + y1Squ) * (x3*y2 - x2*y3) + (x2Sqr + y2Squ) * (x1*y3 - x3*y1) + (x3Squ  + y3Squ) * (x2*y1 - x1*y2);

        return Math.sqrt( ( Math.pow(B,2) + Math.pow(C,2) - 4*A*D ) / (4*Math.pow(A,2)) ); // return radius
    }
}
