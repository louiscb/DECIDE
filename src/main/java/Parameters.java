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
    Decide.Point[] points = new Decide.Point[NUMPOINTS];

    boolean[] CMV = new boolean[NUMCONDITIONS];
    Connectors[][] LCM = new Connectors[NUMCONDITIONS][NUMCONDITIONS];
    boolean[][] PUM;
    boolean[] PUV = new boolean[NUMCONDITIONS];
    boolean[] FUV;
}
