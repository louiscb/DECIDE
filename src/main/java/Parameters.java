public class Parameters {
    final double PI = 3.1415926535;
    final int NUMPOINTS = 100;
    final int NUMCONDITIONS = 15;
    double LENGTH1, RADIUS1, EPSILON, AREA1, DIST, LENGTH2, RADIUS2, AREA2;
    int Q_PTS, QUADS, N_PTS, K_PTS, A_PTS, B_PTS, C_PTS, D_PTS, E_PTS, F_PTS, G_PTS;
    enum Connectors {
        ORR,
        ANDD,
        NOTUSED
    };
    Point[] points; //= new Point[NUMPOINTS];
    boolean[] CMV;
    Connectors[][] LCM; //= new Connectors[NUMCONDITIONS][NUMCONDITIONS];
    boolean[][] PUM;
    boolean[] PUV;
    boolean[] FUV;

    private class Point {
        double x;
        double y;
        double getX() { return this.x; }
        double getY() { return this.y; }
    }
}
