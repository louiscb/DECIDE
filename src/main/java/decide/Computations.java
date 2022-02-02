package decide;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Computations {

    public static final String NO_LAUNCH = "NO";
    public static final String YES_LAUNCH = "YES";
    Parameters params;

    public Computations (Parameters params) {
        if (params.NUMPOINTS > 100 || params.NUMPOINTS < 2)
            throw new IllegalArgumentException("NUMPOINTS cannot be greater than 100 or less than 2");

        this.params = params;
    }

    public void shouldLaunch() {
        generateCMV();
        generatePUM();
        generateFUV();
        launch();
    }

    void launch() {
        for (int i = 0; i < params.FUV.length; i++) {
            if (!params.FUV[i]) {
                params.LAUNCH = NO_LAUNCH;
                return;
            }
        }
        params.LAUNCH = YES_LAUNCH;
    }

    void generateCMV() {
        params.CMV[0] = evaluateLIC_0();
        params.CMV[1] = evaluateLIC_1();
        params.CMV[2] = evaluateLIC_2();
        params.CMV[3] = evaluateLIC_3();
        params.CMV[4] = evaluateLIC_4();
        params.CMV[5] = evaluateLIC_5();
        params.CMV[6] = evaluateLIC_6();
        params.CMV[7] = evaluateLIC_7();
        params.CMV[8] = evaluateLIC_8();
        params.CMV[9] = evaluateLIC_9();
        params.CMV[10] = evaluateLIC_10();
        params.CMV[11] = evaluateLIC_11();
        params.CMV[12] = evaluateLIC_12();
        params.CMV[13] = evaluateLIC_13();
        params.CMV[14] = evaluateLIC_14();
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

    boolean evaluateLIC_0() {
        if (!(0 <= params.LENGTH1)) return false;
        for (int i = 0; i < params.points.length-1; i++) {
            if (params.points[i].distanceTo(params.points[i + 1]) > params.LENGTH1)
                return true;
        }
        return false;
    }

    boolean evaluateLIC_1() {
        if (!(0 <= params.RADIUS1)) return false;
        for (int i = 0; i < params.points.length - 2; i++) {
            Point a = params.points[i];
            Point b = params.points[i+1];
            Point c = params.points[i+2];
            if(MathsHelper.minCircleRadius(a, b, c) > params.RADIUS1) {
                return true;
            }
        }
        return false;
    }

    boolean evaluateLIC_2(){
        if (!(0 <= params.EPSILON && params.EPSILON < params.PI)) return false;
        for (int i = 0; i < params.points.length-2; i++){
            Point a = params.points[i];
            Point b = params.points[i+1];
            Point c = params.points[i+2];
            if ((a.getX()==b.getX() && a.getY()==b.getY()) || (b.getX()==c.getX() && b.getY()==c.getY()))
                continue;
            double ab = a.distanceTo(b);
            double bc = b.distanceTo(c);
            double ac = a.distanceTo(c);
            double angle = Math.acos((Math.pow(ab,2)+Math.pow(ac,2)-Math.pow(bc,2))/(2*ab*ac));
            if (angle < params.PI - params.EPSILON || angle > params.PI - params.EPSILON )
                return true;
        }
        return false;
    }

    boolean evaluateLIC_3() {
        if (!(0 <= params.AREA1)) return false;
        for (int i = 0; i < params.points.length - 2; i++) {
            Point a = params.points[i];
            Point b = params.points[i+1];
            Point c = params.points[i+2];
            if (MathsHelper.triangleArea(a,b,c) > params.AREA1)
                return true;
        }
        return false;
    }

    boolean evaluateLIC_4() {
        if (! (2 <= params.Q_PTS && params.Q_PTS <= params.NUMPOINTS && 1 <= params.QUADS && params.QUADS <= 3))
            return false;

        HashMap<Integer, Integer> quadrantCount = new HashMap<>();
        Queue<Integer> currentConsecutiveQueue = new LinkedList<>();

        for (Point point: params.points) {
            if (currentConsecutiveQueue.size() >= params.Q_PTS && quadrantCount.keySet().size() > params.QUADS)
                return true;

            if (currentConsecutiveQueue.size() < params.Q_PTS) {
                currentConsecutiveQueue.add(point.getQuadrantNumber());
                int count = quadrantCount.getOrDefault(point.getQuadrantNumber(), 0);
                quadrantCount.put(point.getQuadrantNumber(), count + 1);
            } else {
                int removedQuadrantNumber = currentConsecutiveQueue.remove();
                int count = quadrantCount.get(removedQuadrantNumber);

                if (count - 1 <= 0)
                    quadrantCount.remove(removedQuadrantNumber);
                else
                    quadrantCount.put(removedQuadrantNumber, count - 1);
            }
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
        if (! (3 <= params.N_PTS && params.N_PTS <= params.NUMPOINTS && 0 <= params.DIST)) return false;
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
                    distToLine = (2.0 * MathsHelper.triangleArea(first, last, params.points[j])) / distFirstToLast;
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
        if (! (1 <= params.K_PTS && params.K_PTS <= params.NUMPOINTS -2)) return false;
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
        if (! (1 <= params.A_PTS && 1 <= params.B_PTS && params.A_PTS + params.B_PTS <= params.NUMPOINTS-3)) return false;
        if (params.points.length < 5) return false;
        for (int i = 0; i < params.points.length - params.A_PTS - params.B_PTS - 2; i++) {
            Point a = params.points[i];
            Point b = params.points[i + params.A_PTS + 1];
            Point c = params.points[i + params.A_PTS + params.B_PTS + 2];
            if(MathsHelper.minCircleRadius(a, b, c) > params.RADIUS1) {
                return true;
            }
        }
        return false;
    }

    boolean evaluateLIC_9(){
        if (params.C_PTS < 1 || params.D_PTS < 1 || params.C_PTS + params.D_PTS > params.points.length - 3
                || params.points.length < 5)
            return false;

        for (int i = 0; i < params.points.length-2 - params.C_PTS - params.D_PTS; i++){
            Point a = params.points[i];
            Point b = params.points[i+1 + params.C_PTS];
            Point c = params.points[i+2 + params.D_PTS];
            if ((a.getX()==b.getX() && a.getY()==b.getY()) || (b.getX()==c.getX() && b.getY()==c.getY()))
                continue;
            double ab = a.distanceTo(b);
            double bc = b.distanceTo(c);
            double ac = a.distanceTo(c);
            double angle = Math.acos((Math.pow(ab,2)+Math.pow(ac,2)-Math.pow(bc,2))/(2*ab*ac));
            if (angle < params.PI - params.EPSILON || angle > params.PI - params.EPSILON )
                return true;
        }
        return false;

    }

    boolean evaluateLIC_10() {
        if (! (1 <= params.E_PTS && 1 <= params.F_PTS && params.E_PTS + params.F_PTS <= params.NUMPOINTS-3)) return false;
        if (params.points.length < 5) return false;
        for (int i = 0; i < params.points.length - params.E_PTS - params.F_PTS - 2; i++) {
            Point a = params.points[i];
            Point b = params.points[i + params.E_PTS + 1];
            Point c = params.points[i + params.E_PTS + params.F_PTS + 2];
            if (MathsHelper.triangleArea(a,b,c) > params.AREA1)
                return true;
        }
        return false;
    }

    boolean evaluateLIC_12(){
        boolean greaterThan = false;
        boolean lessThan = false;
        if (! (0 <= params.LENGTH2)) return false;
        if (params.NUMPOINTS < 3)
            return false;
        for (int i = 0; i < params.points.length - params.K_PTS - 1; i++){
            Point point1 = params.points[i];
            Point point2 = params.points[i + params.K_PTS + 1];
            if (point1.distanceTo(point2) > params.LENGTH1)
                greaterThan = true;
            if (point1.distanceTo(point2) < params.LENGTH2)
                lessThan = true;
            if (greaterThan && lessThan)
                return true;
        }
        return false;
    }

    boolean evaluateLIC_11() {
        if (! (1 <= params.G_PTS && params.G_PTS <= params.NUMPOINTS - 2)) return false;
        if(params.NUMPOINTS < 3) return false;
        int j = 0;
        for (int i = 0; i < params.points.length - (params.G_PTS + 1); i++) {
            j = i + params.G_PTS + 1; // (i < j) is always true
            if ((params.points[j].getX() - params.points[i].getX()) < 0) {
                return true;
            }
        }
        return false;
    }

    boolean evaluateLIC_13() {
        boolean radius1 = false;
        boolean radius2 = false;
        if (! (0 <= params.RADIUS2)) return false;
        if (params.points.length < 5) return false;
        for (int i = 0; i < params.points.length - params.A_PTS - params.B_PTS - 2; i++) {
            Point a = params.points[i];
            Point b = params.points[i + params.A_PTS + 1];
            Point c = params.points[i + params.A_PTS + params.B_PTS + 2];
            if (!radius1) {
                if(MathsHelper.minCircleRadius(a, b, c) > params.RADIUS1)
                    radius1 = true;
            }
            if (!radius2) {
                if(MathsHelper.minCircleRadius(a, b, c) <= params.RADIUS2)
                    radius2 = true;
            }
            if (radius1 && radius2) return true;
        }
        return false;
    }

    boolean evaluateLIC_14() {
        if (! (0 <= params.AREA2)) return false;
        if (params.points.length < 5) return false;

        boolean isLessThanArea2 = false;

        for (int i = 0; i < params.points.length - params.E_PTS - params.F_PTS - 2; i++) {
            Point a = params.points[i];
            Point b = params.points[i + params.E_PTS + 1];
            Point c = params.points[i + params.E_PTS + params.F_PTS + 2];

            if (MathsHelper.triangleArea(a, b, c) < params.AREA2) {
                isLessThanArea2 = true;
                break;
            }
        }

        return isLessThanArea2 && evaluateLIC_10();
    }
}
