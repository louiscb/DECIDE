import java.lang.Math;
public class Decide {
    Parameters params = new Parameters();
    public static void main(String[] args) {
        
    }

    boolean evaluateLIC_0() {
        boolean satisifed = false;
        for (int i = 0; i < params.points.length; i++) {
            if (i + 1 == params.points.length)
                break;
            Point point = params.points[i];
            Point nextPoint = params.points[i + 1];
            if (point.distanceTo(nextPoint) <= params.LENGTH1) {
                satisifed = true;
                break;
            }
        }
        return satisifed;
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

    boolean launch(boolean[] FUV) {
        for (int i=0; i<FUV.length; i++) {
            if (!FUV[i]) {
                System.out.println("NO");
                return false;
            }
        }
        System.out.println("YES");
        return true;
    }
}
