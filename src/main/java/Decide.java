import java.lang.Math;
import java.util.Arrays;

public class Decide {
    Parameters params = new Parameters();
    public static void main(String[] args) {
        Decide decide = new Decide();
        System.out.println(decide.triangleArea(decide.new Point(0, 0), decide.new Point(1,0), decide.new Point(2,2)));
        
    }

    boolean evaluateLIC_0() {
        for (int i = 0; i < params.points.length-1; i++) {
            if (params.points[i].distanceTo(params.points[i + 1]) > params.LENGTH1)
                return true;
        }
        return false;
    }

    boolean evaluateLIC_1() {
        for (int i = 0; i < params.points.length - 2; i++) {
            Point a = params.points[i];
            Point b = params.points[i+1];
            Point c = params.points[i+2];
            if(minCircleRadius(a, b, c) > params.RADIUS1) {
                return true;
            }
        }
        return false;
    }

    boolean evaluateLIC_3() {
        for (int i = 0; i < params.points.length - 2; i++) {
            Point a = params.points[i];
            Point b = params.points[i+1];
            Point c = params.points[i+2];
            if (triangleArea(a,b,c) > params.AREA1)
                return true;
        }
        return false;
    }

    boolean evaluateLIC_5() {
        for (int i = 0; i < params.points.length; i++) {
            if (i + 1 == params.points.length)
                return false;
            if (params.points[i+1].getX() - params.points[i].getX() < 0)
                return true;
        }
        return false;
    }

    boolean evaluateLIC_6() {
        if(params.NUMPOINTS < 3) return false;
        Point first, last;
        double distToLine, distFirstToLast;
        int lastIndex;
        for (int i = 0; i < params.points.length - (params.N_PTS - 1); i++) {
            lastIndex = i + params.N_PTS - 1;
            for (int j = i+1; j < lastIndex; j++) {
                first = params.points[i];
                last = params.points[lastIndex];
                distFirstToLast = first.distanceTo(last);
                if(distFirstToLast > 0) { // if the first and last points do not coincide
                    distToLine = (2.0 * triangleArea(first, last, params.points[j])) / distFirstToLast;
                    if(distToLine > params.DIST) {
                        return true;
                    }
                }
                else if(first.distanceTo(params.points[j]) > params.DIST) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean evaluateLIC_7(){
        if (params.NUMPOINTS < 3)
            return false;
        for (int i = 0; i < params.points.length - params.K_PTS - 1; i++){
            Point point1 = params.points[i];
            Point point2 = params.points[i + params.K_PTS + 1];
            if (point1.distanceTo(point2) > params.LENGTH1)
                return true; 
        }
        return false;
        
    }

    boolean evaluateLIC_8() {
        if (params.points.length < 5) return false;
        for (int i = 0; i < params.points.length - params.A_PTS - params.B_PTS - 2; i++) {
            Point a = params.points[i];
            Point b = params.points[i + params.A_PTS + 1];
            Point c = params.points[i + params.A_PTS + params.B_PTS + 2];
            if(minCircleRadius(a, b, c) > params.RADIUS1) {
                return true;
            }
        }
        return false;
    }

    boolean evaluateLIC_10() {
        if (params.points.length < 5) return false;
        for (int i = 0; i < params.points.length - params.E_PTS - params.F_PTS - 2; i++) {
            Point a = params.points[i];
            Point b = params.points[i + params.E_PTS + 1];
            Point c = params.points[i + params.E_PTS + params.F_PTS + 2];
            if (triangleArea(a,b,c) > params.AREA1)
                return true;
        }
        return false;
    }

    boolean evaluateLIC_13() {
        boolean radius1 = false;
        boolean radius2 = false;
        if (params.points.length < 5) return false;
        for (int i = 0; i < params.points.length - params.A_PTS - params.B_PTS - 2; i++) {
            Point a = params.points[i];
            Point b = params.points[i + params.A_PTS + 1];
            Point c = params.points[i + params.A_PTS + params.B_PTS + 2];
            if (!radius1) {
                if(minCircleRadius(a, b, c) > params.RADIUS1) 
                    radius1 = true;
            }
            if (!radius2) {
                if(minCircleRadius(a, b, c) <= params.RADIUS2) 
                    radius2 = true;
            }
            if (radius1 && radius2) return true;           
        }
        return false;
    }

    void generatePUM() { 
        for (int i = 0; i < params.PUM.length; i++) {
            for (int j = 0; j < params.PUM.length; j++) {
                if (i == j) continue; // ignore diagonal PUM elements
                params.PUM[i][j] = (params.LCM[i][j] == Parameters.Connectors.ANDD && params.CMV[i] && params.CMV[j])
                                   || (params.LCM[i][j] == Parameters.Connectors.ORR && (params.CMV[i] || params.CMV[j]))
                                   || (params.LCM[i][j] == Parameters.Connectors.NOTUSED);
            }
        }
    }

    void generateFUV() {
        for (int i = 0; i < params.FUV.length; i++) {
            boolean[] row = params.PUM[i];
            if (!params.PUV[i]) 
                params.FUV[i] = true;
            else {
                params.FUV[i] = true;
                for (boolean b : row) 
                    if (!b) {
                        params.FUV[i] = false;
                        break;
                    }
            }
        }
    }

    public class Point {
        private double x;
        private double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        void printPoint() { 
            System.out.println("(" + this.x + "," + this.y + ")");
        }

        double getX() { return this.x; }
        double getY() { return this.y; }

        double distanceTo(Point b) {
            return Math.hypot(this.getX() - b.getX(), this.getY() - b.getY());
        }
    }

    double triangleArea(Point a, Point b, Point c) {
        return Math.abs(0.5*(a.getX()* (b.getY()-c.getY()) + 
                            b.getX()* (c.getY() - a.getY()) + 
                            c.getX()* (a.getY() - b.getY())));
    }

    double minCircleRadius(Point a, Point b, Point c) {
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

    boolean launch() {
        for (int i = 0; i < params.FUV.length; i++) {
            if (!params.FUV[i]) {
                return false;
            }
        }
        return true;
    }
}
