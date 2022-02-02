package decide;

import java.util.concurrent.ThreadLocalRandom;

public class Parameters {

    final double PI = 3.1415926535;
    final int NUMCONDITIONS = 15;

    double LENGTH1, RADIUS1, EPSILON, AREA1, DIST, LENGTH2, RADIUS2, AREA2;
    int NUMPOINTS, Q_PTS, QUADS, N_PTS, K_PTS, A_PTS, B_PTS, C_PTS, D_PTS, E_PTS, F_PTS, G_PTS;
    String LAUNCH;

    enum Connectors {
        ORR,
        ANDD,
        NOTUSED
    };

    Point[] points;

    boolean[] CMV = new boolean[NUMCONDITIONS];
    Connectors[][] LCM = new Connectors[NUMCONDITIONS][NUMCONDITIONS];
    boolean[][] PUM = new boolean[NUMCONDITIONS][NUMCONDITIONS];
    boolean[] PUV = new boolean[NUMCONDITIONS];
    boolean[] FUV = new boolean[NUMCONDITIONS];

    /**
    * Class constructor.
    * Sets the parameters and inputs to default values and generates 100 random points
    */
    public Parameters() {
        // Default set of parameters for test runs
        this.LENGTH1 = 1;
        this.LENGTH2 = 5;
        this.RADIUS1 = 1;
        this.RADIUS2 = 1;
        this.EPSILON = 2;
        this.AREA1 = 5;
        this.AREA2 = 5;
        this.Q_PTS = 10;
        this.QUADS = 3;
        this.NUMPOINTS = 100;
        this.DIST = 1;
        this.N_PTS = 3;
        this.K_PTS = 2;
        this.A_PTS = 2;
        this.B_PTS = 3;
        this.C_PTS = 1;
        this.D_PTS = 1;
        this.E_PTS = 25;
        this.F_PTS = 50;
        this.G_PTS = 1;

        points = new Point[NUMPOINTS];

        // Randomly generate points
        for (int i = 0; i < points.length; i++) {
            int randomX = ThreadLocalRandom.current().nextInt(-100, 100);
            int randomY = ThreadLocalRandom.current().nextInt(-100, 100);
            points[i] = new Point(randomX, randomY);
        }

        // Set All LCM to And
        for (int i = 0; i < NUMCONDITIONS; i++) {
            for (int j = 0; j < NUMCONDITIONS; j++) {
                LCM[i][j] = Connectors.ANDD;
            }
        }

        // Set all PUV to true, all LICs are considered
        for (int i = 0; i < NUMCONDITIONS; i++) {
            PUV[i] = true;
        }
    }
}
