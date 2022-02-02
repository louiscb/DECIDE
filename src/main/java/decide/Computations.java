package decide;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Computations {

    public static final String NO_LAUNCH = "NO";
    public static final String YES_LAUNCH = "YES";
    Parameters params;

    /**
    * Class constructor, given the parameters it should contain. 
    *
    * @param params is the provided parameters.
    */
    public Computations (Parameters params) {
        if (params.NUMPOINTS > 100 || params.NUMPOINTS < 2)
            throw new IllegalArgumentException("NUMPOINTS cannot be greater than 100 or less than 2");

        this.params = params;
    }

    /**
    * Performs the necessary computations that are required in order to set the 
    * launch decision.
    */
    public void shouldLaunch() {
        generateCMV();
        generatePUM();
        generateFUV();
        launch();
    }

    /**
    * Sets the decision for LAUNCH using the The Final Unlocking Vector (FUV).
    * he decision to launch requires that all elements in the FUV be true, i.e. 
    * LAUNCH should be set to true if and only if FUV[i] is true for all i, 0 ≤ i ≤ 14.
    */
    void launch() {
        for (int i = 0; i < params.FUV.length; i++) {
            if (!params.FUV[i]) {
                params.LAUNCH = NO_LAUNCH;
                return;
            }
        }
        params.LAUNCH = YES_LAUNCH;
    }

    /**
    * Generates the Conditions Met Vector (CMV) based on the evaluation 
    * of each Launch Interceptor Condition (LIC).
    */
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

    /**
    * Generates the Preliminary Unlocking Matrix (PUM), using The Conditions Met
    * Vector (CMV) and Logical Connector Matrix (LCM).
    * CMV is used in conjunction with the LCM to form the PUM. The entries in the
    * LCM represent the logical connectors to be used between pairs of LICs to
    * determine the corresponding entry in the PUM, i.e. LCM[i,j] represents the
    * boolean operator to be applied to CMV[i] and CMV[j]. PUM[i,j] is set according
    * to the result of this operation. If LCM[i,j] is NOTUSED, then PUM[i,j] should
    * be set to true. If LCM[i,j] is ANDD, PUM[i,j] should be set to true only if
    * (CMV[i] AND CMV[j]) is true. If LCM[i,j] is ORR, PUM[i,j] should be set to
    * true if (CMV[i] OR CMV[j]) is true.
    * (Note that the LCM is symmetric, i.e. LCM[i,j]=LCM[j,i] for all i and j.)
    */
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

    /**
    * Generates the The Final Unlocking Vector (FUV), using the
    * Preliminary Unlocking Matrix (PUM).
    * FUV is generated from the PUM. The input PUV indicates whether the
    * corresponding LIC is to be considered as a factor in signaling interceptor
    * launch. FUV[i] should be set to true if PUV[i] is false (indicating that the
    * associated LIC should not hold back launch) or if all elements in PUM row i
    * are true.
    */
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

    /**
    * Returns whether if the Launch Interceptor Condition (LIC) 0 is met.
    * Used for setting the 0th index of the Conditions Met Vector (CMV).
    *
    * DECIDE parameters used:
    * LENGTH1
    *
    * Condition:
    * There exists at least one set of two consecutive data points that are a
    * distance greater than the length, LENGTH1, apart.
    * (0 ≤ LENGTH1)
    *
    * @return true if the condition is met otherwise return false
    */
    boolean evaluateLIC_0() {
        for (int i = 0; i < params.points.length-1; i++) {
            if (params.points[i].distanceTo(params.points[i + 1]) > params.LENGTH1)
                return true;
        }
        return false;
    }

    /**
    * Returns whether if the Launch Interceptor Condition (LIC) 1 is met.
    * Used for setting the 1th index of the Conditions Met Vector (CMV).
    *
    * DECIDE parameters used:
    * RADIUS1
    *
    * Condition:
    * There exists at least one set of three consecutive data points that cannot
    * all be contained within or on a circle of radius RADIUS1.
    * (0 ≤ RADIUS1)
    *
    * @return true if the condition is met otherwise return false
    */
    boolean evaluateLIC_1() {
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


    /**
    * Returns whether if the Launch Interceptor Condition (LIC) 2 is met.
    * Used for setting the 2th index of the Conditions Met Vector (CMV).
    *
    * DECIDE parameters used:
    * PI
    * EPSILON
    *
    * Condition:
    * There exists at least one set of three consecutive data points which form an
    * angle such that: angle < (PI − EPSILON) or angle > (PI + EPSILON)
    * The second of the three consecutive points is always the vertex of the angle.
    * If either the first point or the last point (or both) coincides with the
    * vertex, the angle is undefined and the LIC is not satisfied by those three
    * points.
    * (0 ≤ EPSILON < PI)
    *
    * @return true if the condition is met otherwise return false
    */
    boolean evaluateLIC_2(){
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

    /**
    * Returns whether if the Launch Interceptor Condition (LIC) 3 is met.
    * Used for setting the 3th index of the Conditions Met Vector (CMV).
    *
    * DECIDE parameters used:
    * AREA1
    *
    * Condition:
    * There exists at least one set of three consecutive data points that are the
    * vertices of a triangle with area greater than AREA1.
    * (0 ≤ AREA1)
    *
    * @return true if the condition is met otherwise return false
    */
    boolean evaluateLIC_3() {
        for (int i = 0; i < params.points.length - 2; i++) {
            Point a = params.points[i];
            Point b = params.points[i+1];
            Point c = params.points[i+2];
            if (MathsHelper.triangleArea(a,b,c) > params.AREA1)
                return true;
        }
        return false;
    }

    /**
    * Returns whether if the Launch Interceptor Condition (LIC) 4 is met.
    * Used for setting the 4th index of the Conditions Met Vector (CMV).
    *
    * DECIDE parameters used:
    * Q_PTS
    * QUADS
    *
    * Condition:
    * There exists at least one set of Q_PTS consecutive data points that lie in
    * more than QUADS quadrants. Where there is ambiguity as to which quadrant
    * contains a given point, priority of decision will be by quadrant number,
    * i.e., I, II, III, IV. For example, the data point (0,0) is in quadrant I,
    * the point (-l,0) is in quadrant II, the point (0,-l) is in quadrant III, the
    * point (0,1) is in quadrant I and the point (1,0) is in quadrant I.
    * (2 ≤ Q_PTS ≤ NUMPOINTS), (1 ≤ QUADS ≤ 3)
    *
    * @return true if the condition is met otherwise return false
    */
    boolean evaluateLIC_4() {
        if (! (2 <= params.Q_PTS || params.Q_PTS <= params.NUMPOINTS || 1 <= params.QUADS || params.QUADS <= 3))
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

    /**
    * Returns whether if the Launch Interceptor Condition (LIC) 5 is met.
    * Used for setting the 5th index of the Conditions Met Vector (CMV).
    *
    * Condition:
    * There exists at least one set of two consecutive data points, (X[i],Y[i])
    * and (X[j],Y[j]), such that X[j] - X[i] < 0. (where i = j-1)
    *
    * @return true if the condition is met otherwise return false
    */
    boolean evaluateLIC_5() {
        for (int i = 0; i < params.points.length; i++) {
            if (i + 1 == params.points.length)
                return false;
            if (params.points[i+1].getX() - params.points[i].getX() < 0)
                return true;
        }
        return false;
    }

    /**
    * Returns whether if the Launch Interceptor Condition (LIC) 6 is met.
    * Used for setting the 6th index of the Conditions Met Vector (CMV).
    *
    * DECIDE parameters used:
    * N_PTS
    * DIST
    *
    * Condition:
    * There exists at least one set of N_PTS consecutive data points such that at
    * least one of the points lies a distance greater than DIST from the line
    * joining the first and last of these N_PTS points. If the first and last
    * points of these N_PTS are identical, then the calculated distance to compare
    * with DIST will be the distance from the coincident point to all other points
    * of the N PTS consecutive points. The condition is not met when NUMPOINTS < 3.
    * (3 ≤ N_PTS ≤ NUMPOINTS), (0 ≤ DIST)
    *
    * @return true if the condition is met otherwise return false
    */
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

    /**
    * Returns whether if the Launch Interceptor Condition (LIC) 7 is met.
    * Used for setting the 7th index of the Conditions Met Vector (CMV).
    *
    * DECIDE parameters used:
    * K_PTS
    * LENGTH1
    *
    * Condition:
    * There exists at least one set of two data points separated by exactly K_PTS
    * consecutive intervening points that are a distance greater than the
    * length, LENGTH1, apart. The condition is not met when NUMPOINTS < 3.
    * (1 ≤ K_PTS ≤ (NUMPOINTS − 2))
    *
    * @return true if the condition is met otherwise return false
    */
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

    /**
    * Returns whether if the Launch Interceptor Condition (LIC) 8 is met.
    * Used for setting the 8th index of the Conditions Met Vector (CMV).
    *
    * DECIDE parameters used:
    * A_PTS
    * B_PTS
    * RADIUS1
    *
    * Condition:
    * There exists at least one set of three data points separated by exactly A_PTS
    * and B_PTS consecutive intervening points, respectively, that cannot be
    * contained within or on a circle of radius RADIUS1. The condition is not met
    * when NUMPOINTS < 5.
    * 1 ≤ A_PTS, 1 ≤ B_PTS
    * A_PTS + B_PTS ≤ (NUMPOINTS − 3)
    *
    * @return true if the condition is met otherwise return false
    */
    boolean evaluateLIC_8() {
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

    /**
    * Returns whether if the Launch Interceptor Condition (LIC) 9 is met.
    * Used for setting the 9th index of the Conditions Met Vector (CMV).
    *
    * DECIDE parameters used:
    * C_PTS
    * D_PTS
    * PI
    * EPSILON
    *
    * Condition:
    * There exists at least one set of three data points separated by exactly C_PTS
    * and D_PTS consecutive intervening points, respectively, that form an angle
    * such that: angle < (PI − EPSILON) or angle > (PI + EPSILON)
    * The second point of the set of three points is always the vertex of the
    * angle. If either the first point or the last point (or both) coincide with
    * the vertex, the angle is undefined and the LIC is not satisfied by those
    * three points. When NUMPOINTS < 5, the condition is not met.
    * 1 ≤ C_PTS, 1 ≤ D_PTS
    * C_PTS + D_PTS ≤ NUMPOINTS − 3
    *
    * @return true if the condition is met otherwise return false
    */
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

    /**
    * Returns whether if the Launch Interceptor Condition (LIC) 10 is met.
    * Used for setting the 10th index of the Conditions Met Vector (CMV).
    *
    * DECIDE parameters used:
    * E_PTS
    * F_PTS
    * AREA1
    *
    * Condition:
    * There exists at least one set of three data points separated by exactly E_PTS
    * and F_PTS consecutive intervening points, respectively, that are the
    * vertices of a triangle with area greater than AREA1. The condition is not met
    * when NUMPOINTS < 5.
    * 1 ≤ E_PTS,1 ≤ F_PTS
    * E_PTS + F_PTS ≤ NUMPOINTS − 3
    *
    * @return true if the condition is met otherwise return false
    */
    boolean evaluateLIC_10() {
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

    /**
    * Returns whether if the Launch Interceptor Condition (LIC) 11 is met.
    * Used for setting the 11th index of the Conditions Met Vector (CMV).
    *
    * DECIDE parameters used:
    * G_PTS
    *
    * Condition:
    * There exists at least one set of two data points, (X[i],Y[i]) and (X[j],Y[j]),
    * separated by exactly G_PTS consecutive intervening points, such that
    * X[j] - X[i] < 0. (where i < j) The condition is not met when NUMPOINTS < 3.
    * 1 ≤ G_PTS ≤ NUMPOINTS − 2
    *
    * @return true if the condition is met otherwise return false
    */
    boolean evaluateLIC_11() {
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

    /**
    * Returns whether if the Launch Interceptor Condition (LIC) 12 is met.
    * Used for setting the 12th index of the Conditions Met Vector (CMV).
    *
    * DECIDE parameters used:
    * K_PTS
    * LENGTH1
    * LENGTH2
    *
    * Condition:
    * There exists at least one set of two data points, separated by exactly K_PTS
    * consecutive intervening points, which are a distance greater than the length,
    * LENGTH1, apart. In addition, there exists at least one set of two data points
    * (which can be the same or different from the two data points just mentioned),
    * separated by exactly K_PTS consecutive intervening points, that are a distance
    * less than the length, LENGTH2, apart. Both parts must be true for the LIC to
    * be true. The condition is not met when NUMPOINTS < 3.
    * 0 ≤ LENGTH2
    *
    * @return true if the condition is met otherwise return false
    */
    boolean evaluateLIC_12(){
        boolean greaterThan = false;
        boolean lessThan = false;
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

    /**
    * Returns whether if the Launch Interceptor Condition (LIC) 13 is met.
    * Used for setting the 13th index of the Conditions Met Vector (CMV).
    *
    * DECIDE parameters used:
    * A_PTS
    * B_PTS
    * RADIUS1
    * RADIUS2
    *
    * Condition:
    * There exists at least one set of three data points, separated by exactly A_PTS
    * and B_PTS consecutive intervening points, respectively, that cannot be
    * contained within or on a circle of radius RADIUS1. In addition, there exists
    * at least one set of three data points (which can be the same or different from
    * the three data points just mentioned) separated by exactly A_PTS and B_PTS
    * consecutive intervening points, respectively, that can be contained in or on a
    * circle of radius RADIUS2. Both parts must be true for the LIC to be true.
    * The condition is not met when NUMPOINTS < 5.
    * 0 ≤ RADIUS2
    *
    * @return true if the condition is met otherwise return false
    */
    boolean evaluateLIC_13() {
        boolean radius1 = false;
        boolean radius2 = false;
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

    /**
    * Returns whether if the Launch Interceptor Condition (LIC) 14 is met.
    * Used for setting the 14th index of the Conditions Met Vector (CMV).
    *
    * DECIDE parameters used:
    * E_PTS
    * F_PTS
    * AREA1
    * AREA2
    *
    * Condition:
    * There exists at least one set of three data points, separated by exactly E_PTS
    * and F_PTS consecutive intervening points, respectively, that are the vertices
    * of a triangle with area greater than AREA1. In addition, there exist three
    * data points (which can be the same or different from the three data points
    * just mentioned) separated by exactly E_PTS and F_PTS consecutive intervening
    * points, respectively, that are the vertices of a triangle with area less than
    * AREA2. Both parts must be true for the LIC to be true. The condition is not
    * met when NUMPOINTS < 5.
    * 0 ≤ AREA2
    *
    * @return true if the condition is met otherwise return false
    */
    boolean evaluateLIC_14() {
        if (params.points.length < 5)
            return false;

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
