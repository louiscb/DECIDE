import java.lang.Math;
public class Decide {
    Parameters params = new Parameters();
    public static void main(String[] args) {
        
    }

    boolean evaluateLIC_0() {
        for (int i = 0; i < params.points.length; i++) {
            if (i + 1 == params.points.length)
                return false;
            if (params.points[i].distanceTo(params.points[i + 1]) > params.LENGTH1)
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
