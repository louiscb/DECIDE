package decide;

import org.junit.jupiter.api.Test;

import static decide.Computations.NO_LAUNCH;
import static decide.Computations.YES_LAUNCH;
import static org.junit.jupiter.api.Assertions.*;

class ComputationsTest {

    // Use default parameters for testing
    Parameters parameters = new Parameters();

    @Test 
    void test_evaluate_LIC_0_consecutive() {
        Computations decide = new Computations(parameters);
        decide.params.LENGTH1 = 2;

        // Put each point on the diagonal line y = x so that the distance between them is sqrt(2) < LENGTH1.
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(i,i);
        }
        // Put (35,35) on (34, 34) so that the distance to the next point (36, 36) is > LENGTH1.
        decide.params.points[35] = new Point(34, 34);
        assertTrue(decide.evaluateLIC_0());
    }

    @Test 
    void test_evaluate_LIC_0_non_consecutive() {
        Computations decide = new Computations(parameters);
        decide.params.LENGTH1 = 2;

        // Put each point on the diagonal line y = x so that the distance between is sqrt(2) < LENGTH1.
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(i,i);
        }
        assertFalse(decide.evaluateLIC_0());
    }

    @Test
    void test_evaluate_LIC_0_false_params_LENGTH1() {
        Computations decide = new Computations(parameters);
        decide.params.LENGTH1 = -1;
        assertFalse(decide.evaluateLIC_0());
    }

    @Test 
    void test_evaluate_LIC_1_true() {
        Computations decide = new Computations(parameters);
        decide.params.RADIUS1 = 1;
       // set all three consecutive points near each other, within a radius less than 1.
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(0,0);
        }
        // change the last three consecutive points so that RADIUS1 is to small 
        decide.params.points[decide.params.points.length-3] = new Point(0,0);
        decide.params.points[decide.params.points.length-2] = new Point(5,0);
        decide.params.points[decide.params.points.length-1] = new Point(10,5);
        
        assertTrue(decide.evaluateLIC_1());
    }

    @Test 
    void test_evaluate_LIC_1_false() {
        Computations decide = new Computations(parameters);
        decide.params.RADIUS1 = 1;
         // set all three consecutive points near each other, within a radius less than 1. 
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(i*0.01,i*0.015);
        }
        assertFalse(decide.evaluateLIC_1());
    }

    @Test
    void test_evaluate_LIC_1_false_params_RADIUS1() {
        Computations decide = new Computations(parameters);
        decide.params.RADIUS1 = -1;
        assertFalse(decide.evaluateLIC_1());
    }

    @Test
    void test_evaluate_LIC_2_true() {
        Computations decide = new Computations(parameters);
        decide.params.EPSILON = 2;
         // Set all points to (0,0)
         for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(0,0);
        }
        //Change three consecutive points so that they form an angle < (PI-EPSILON)
        decide.params.points[10] = new Point(50,50);
        decide.params.points[11] = new Point(51,100);
        decide.params.points[12] = new Point(52,20);
        assertTrue(decide.evaluateLIC_2());
    }

    @Test
    //Should be false since the three points with index 0, 1 and 3 forms an angle < (PI-EPSILON)
    //but the points are not consecutive
    void test_evaluate_LIC_2_false_1() {
        Computations decide = new Computations(parameters);
        decide.params.EPSILON = 2;
        decide.params.points[0] = new Point(50,50);
        decide.params.points[1] = new Point(51,51);
        decide.params.points[2] = new Point(52,52);
        decide.params.points[3] = new Point(50.1,50.1);
        decide.params.points[4] = new Point(52,0);
        //set the rest of the points to (52,0)
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(52,0);
        }
        assertFalse(decide.evaluateLIC_2());
    }

    @Test
    //Should be false since point a and point b, or point b and point c always have the same coordinates
    void test_evaluate_LIC_2_false_2() {
        Computations decide = new Computations(parameters);
        decide.params.EPSILON = 2;
         // Set all points to (0,0)
         for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(0,0);
        }
        decide.params.points[10] = new Point(50,50);
        decide.params.points[11] = new Point(50,50);
        decide.params.points[12] = new Point(50.2,20);
        decide.params.points[13] = new Point(50.2,20);
        assertFalse(decide.evaluateLIC_2());
    }

    @Test
    void test_evaluate_LIC_2_false_params_EPSILON() {
        Computations decide = new Computations(parameters);
        decide.params.EPSILON = 5;
        assertFalse(decide.evaluateLIC_2());
    }

    @Test
    void test_evaluate_LIC_3_true() {
        Computations decide = new Computations(parameters);
        decide.params.AREA1 = 5;
        // Set all points to (0,0)
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(0,0);
        }
        // Update two points so that the area of the triangle will be > AREA1
        decide.params.points[1] = new Point(3,3);
        decide.params.points[2] = new Point(5,0);
        assertTrue(decide.evaluateLIC_3());
    }

    @Test
    void test_evaluate_LIC_3_false() {
        Computations decide = new Computations(parameters);
        decide.params.AREA1 = 5;
        // Set all points to (0,0)
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(0,0);
        }
        assertFalse(decide.evaluateLIC_3());
    }

    @Test
    void test_evaluate_LIC_3_false_params_AREA1() {
        Computations decide = new Computations(parameters);
        decide.params.AREA1 = -1;
        assertFalse(decide.evaluateLIC_3());
    }

    @Test
    void test_evaluate_LIC_4_true() {
        Computations decide = new Computations(parameters);

        // Put points on (0,0), (-1,1), (-2, -2), (3, -3)
        int[] triangle = {0, 0, -1, 1, -2, -2, 3, -3};
        int j = 0;
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(triangle[j], triangle[j+1]);
            j = (j + 2) % triangle.length;
        }

        decide.params.QUADS = 3;
        decide.params.Q_PTS = 50;

        assertTrue(decide.evaluateLIC_4());
    }

    @Test
    void test_evaluate_LIC_4_false() {
        Computations decide = new Computations(parameters);

        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(i, i);
        }

        decide.params.QUADS = 3;
        decide.params.Q_PTS = 50;

        assertFalse(decide.evaluateLIC_4());
    }

    @Test
    void test_evaluate_LIC_4_false_params_Q_PTS() {
        Computations decide = new Computations(parameters);
        decide.params.Q_PTS = -1;
        assertFalse(decide.evaluateLIC_4());
    }

    @Test
    void test_evaluate_LIC_4_false_params_QUADS() {
        Computations decide = new Computations(parameters);
        decide.params.QUADS = 5;
        assertFalse(decide.evaluateLIC_4());
    }

    @Test
    void test_evaluate_LIC_5_false() {
        Computations decide = new Computations(parameters);
        decide.params.LENGTH1 = 2;
        // Put points on (0,0), (1,0), ..., (99,0).
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(i,0);
        }
        assertFalse(decide.evaluateLIC_5());
    }

    @Test
    void test_evaluate_LIC_5_true() {
        Computations decide = new Computations(parameters);
        decide.params.LENGTH1 = 2;
        // Put points on (0,0), (1,0), ..., (99,0).
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(i,0);
        }
        // Move (4,0) to (2.5,0) so that its x-coordinate is lesser than that of the point preceding it.
        decide.params.points[4] = new Point(2.5,0);
        assertTrue(decide.evaluateLIC_5());
    }

    @Test
    void test_evaluate_LIC_6_true() {
        Computations decide = new Computations(parameters);
        decide.params.N_PTS = 3;// three consecutive
        decide.params.DIST = 1;
        // set all three consecutive points on the same line which results in the pointToLine distance being 0. 
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(0,0);
        }
        // using the last four point 
        // create two sets of three consecutive points where the distance from the 
        // the middle point to the line joining the first and the third is 2 which is greater than DIST. 
        decide.params.points[decide.params.NUMPOINTS - 4] = new Point(0,0);
        decide.params.points[decide.params.NUMPOINTS - 3] = new Point(2,2);
        decide.params.points[decide.params.NUMPOINTS - 2] = new Point(4,0);
        decide.params.points[decide.params.NUMPOINTS - 1] = new Point(6,2);

        assertTrue(decide.evaluateLIC_6());

        // using the last three point 
        // create a set of three consecutive points where the first and third coincide
        // this results in the distance between the first and the second being 2 which is greater than DIST. 
        decide.params.points[decide.params.NUMPOINTS - 3] = new Point(0,0);
        decide.params.points[decide.params.NUMPOINTS - 2] = new Point(2,0);
        decide.params.points[decide.params.NUMPOINTS - 1] = new Point(0,0);

        assertTrue(decide.evaluateLIC_6());
    }

    @Test
    void test_evaluate_LIC_6_false() {
        Computations decide = new Computations(parameters);
        decide.params.N_PTS = 3;
        decide.params.DIST = 5;
        // set all points to (0,0) which results in the pointToLine distance being 0. 
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(0,0);
        }
        // using the last four point 
        // create two sets of three consecutive points where the distance from the 
        // the middle point to the line joining the first and the third is 2 which is less than DIST. 
        decide.params.points[decide.params.NUMPOINTS - 4] = new Point(0,0);
        decide.params.points[decide.params.NUMPOINTS - 3] = new Point(2,2);
        decide.params.points[decide.params.NUMPOINTS - 2] = new Point(4,0);
        decide.params.points[decide.params.NUMPOINTS - 1] = new Point(6,2);

        assertFalse(decide.evaluateLIC_6());
    }

    @Test
    void test_evaluate_LIC_6_false_params_N_PTS() {
        Computations decide = new Computations(parameters);
        decide.params.N_PTS = 2;
        assertFalse(decide.evaluateLIC_6());
    }

    @Test
    void test_evaluate_LIC_6_false_params_DIST() {
        Computations decide = new Computations(parameters);
        decide.params.DIST = -1;
        assertFalse(decide.evaluateLIC_6());
    }

    @Test
    void test_evaluate_LIC_7_true() {
        Computations decide = new Computations(parameters);
        decide.params.K_PTS = 2;
        decide.params.LENGTH1= 1;
        // Set all points to (0,0)
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(0,0);
        }
        // Update two points so that they have a distance greater than length and are separated by K_PTS
        decide.params.points[3] = new Point(50,0);
        decide.params.points[6] = new Point(50,50);
        assertTrue(decide.evaluateLIC_7());
    }

    @Test
    void test_evaluate_LIC_7_false() {
        Computations decide = new Computations(parameters);
        decide.params.K_PTS = 2;
        decide.params.LENGTH1= 50;
        // Set all points to (0,0)
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(0,0);
        }
        // Update two points, but there distance is not greater than LENGTH1
        decide.params.points[3] = new Point(1,1);
        decide.params.points[6] = new Point(2,2);
        assertFalse(decide.evaluateLIC_7());
    }

    @Test
    void test_evaluate_LIC_7_false_params_K_PTS() {
        Computations decide = new Computations(parameters);
        decide.params.K_PTS = 0;
        assertFalse(decide.evaluateLIC_7());
    }

    @Test
    void test_evaluate_LIC_8_true() {
        Computations decide = new Computations(parameters);
        decide.params.A_PTS = 2;
        decide.params.B_PTS = 3;
        decide.params.RADIUS1 = 5;
        // Set all points to (0,0)
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(0,0);
        }
        // Update two points so that three points can not put within the RADIUS1
        decide.params.points[3] = new Point(50,0);
        decide.params.points[7] = new Point(50,50);
        assertTrue(decide.evaluateLIC_8());
    }

    @Test
    void test_evaluate_LIC_8_false() {
        Computations decide = new Computations(parameters);
        decide.params.A_PTS = 2;
        decide.params.B_PTS = 3;
        decide.params.RADIUS1 = 5;
        // Set all points to (0,0)
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(0,0);
        }
        // Update two points but three points are still within the RADIUS1
        decide.params.points[3] = new Point(1,0);
        decide.params.points[7] = new Point(1,1);
        assertFalse(decide.evaluateLIC_8());
    }

    @Test
    void test_evaluate_LIC_8_false_params_A_PTS() {
        Computations decide = new Computations(parameters);
        decide.params.A_PTS = 0;
        decide.params.B_PTS = 1;
        assertFalse(decide.evaluateLIC_8());
    }

    @Test
    void test_evaluate_LIC_8_false_params_B_PTS() {
        Computations decide = new Computations(parameters);
        decide.params.A_PTS = 1;
        decide.params.B_PTS = 0;
        assertFalse(decide.evaluateLIC_8());
    }

    @Test
    void test_evaluate_LIC_8_false_params_A_PTS_AND_B_PTS() {
        Computations decide = new Computations(parameters);
        decide.params.A_PTS = decide.params.NUMPOINTS + 1;
        decide.params.B_PTS = decide.params.NUMPOINTS + 1;
        assertFalse(decide.evaluateLIC_8());
    }

    @Test
    void test_evaluate_LIC_9_true() {
        Computations decide = new Computations(parameters);
        decide.params.EPSILON = 2;
        decide.params.C_PTS = 1;
        decide.params.D_PTS = 1;

        // Set all points to (0,0)
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(0,0);
        }
        //Change three consecutive points plus C_PTS and D_PTS so that they form an angle < (PI-EPSILON)
        decide.params.points[10] = new Point(50,50);
        decide.params.points[12] = new Point(51,100);
        decide.params.points[13] = new Point(52,20);
        assertTrue(decide.evaluateLIC_9());
    }

    @Test
    void test_evaluate_LIC_9_false() {
        Computations decide = new Computations(parameters);
        decide.params.EPSILON = 2;
        decide.params.C_PTS = 1;
        decide.params.D_PTS = 1;

        // Set all points to (0,0)
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(0,0);
        }
        //Change three consecutive points plus C_PTS and D_PTS so that they form an angle > (PI-EPSILON)
        decide.params.points[10] = new Point(50,50);
        decide.params.points[12] = new Point(50,50);
        decide.params.points[13] = new Point(50,50);

        assertFalse(decide.evaluateLIC_9());
    }

    @Test
    void test_evaluate_LIC_9_false_params_C_PTS() {
        Computations decide = new Computations(parameters);
        decide.params.C_PTS = 0;
        decide.params.D_PTS = 1;

        assertFalse(decide.evaluateLIC_9());
    }

    @Test
    void test_evaluate_LIC_9_false_params_D_PTS() {
        Computations decide = new Computations(parameters);
        decide.params.C_PTS = 1;
        decide.params.D_PTS = 0;

        assertFalse(decide.evaluateLIC_9());
    }

    @Test
    void test_evaluate_LIC_9_false_params_D_PTS_And_C_PTS() {
        Computations decide = new Computations(parameters);
        decide.params.C_PTS = 52;
        decide.params.D_PTS = 52;

        assertFalse(decide.evaluateLIC_9());
    }

    @Test
    void test_evaluate_LIC_10_true() {
        Computations decide = new Computations(parameters);
        decide.params.E_PTS = 25;
        decide.params.F_PTS = 50;
        decide.params.AREA1 = 5;
        // Set all points to (0,0)
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(0,0);
        }
        // Create points that are separated by E_PTS and F_PTS points and that form a triangle with area 8 > AREA1.
        decide.params.points[10] = new Point(50,50);
        decide.params.points[10 + decide.params.E_PTS + 1] = new Point(54,50);
        decide.params.points[10 + decide.params.E_PTS + decide.params.F_PTS + 2] = new Point(54,54);
        assertTrue(decide.evaluateLIC_10());
    }

    @Test
    void test_evaluate_LIC_10_false_1() {
        Computations decide = new Computations(parameters);
        decide.params.E_PTS = 25;
        decide.params.F_PTS = 50;
        decide.params.AREA1 = 5;
        // Set all points to (0,0)
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(0,0);
        }
        // Create points that are NOT separated by E_PTS and F_PTS points and that form a triangle with area 8 > AREA1.
        decide.params.points[10] = new Point(50,50);
        decide.params.points[10 + decide.params.E_PTS + 3] = new Point(54,50);
        decide.params.points[10 + decide.params.E_PTS + decide.params.F_PTS + 5] = new Point(54,54);
        assertFalse(decide.evaluateLIC_10());
    }

    @Test
    void test_evaluate_LIC_10_false_2() {
        Computations decide = new Computations(parameters);
        decide.params.E_PTS = 25;
        decide.params.F_PTS = 50;
        decide.params.AREA1 = 5;
        // Set all points to (0,0)
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(0,0);
        }
        // Create points that are separated by E_PTS and F_PTS points and that form a triangle with area 2 < AREA1.
        decide.params.points[10] = new Point(50,50);
        decide.params.points[10 + decide.params.E_PTS + 1] = new Point(52,50);
        decide.params.points[10 + decide.params.E_PTS + decide.params.F_PTS + 2] = new Point(52,52);
        assertFalse(decide.evaluateLIC_10());
    }

    @Test
    void test_evaluate_LIC_10_false_params_E_PTS() {
        Computations decide = new Computations(parameters);
        decide.params.E_PTS = 0;
        decide.params.F_PTS = 1;

        assertFalse(decide.evaluateLIC_10());
    }

    @Test
    void test_evaluate_LIC_10_false_params_F_PTS() {
        Computations decide = new Computations(parameters);
        decide.params.E_PTS = 1;
        decide.params.F_PTS = 0;

        assertFalse(decide.evaluateLIC_10());
    }

    @Test
    void test_evaluate_LIC_10_false_params_E_PTS_AND_F_PTS() {
        Computations decide = new Computations(parameters);
        decide.params.E_PTS = decide.params.NUMPOINTS + 3;
        decide.params.F_PTS = decide.params.NUMPOINTS + 3;

        assertFalse(decide.evaluateLIC_10());
    }

    @Test
    void test_evaluate_LIC_11_true() {
        Computations decide = new Computations(parameters);
        decide.params.G_PTS = 1;
        // Set all points to (0,0)
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(0,0);
        }
        // Create a set of two points (x_i,y_i) and (x_j,y_j), separated by G_PTS consecutive intervening points 
        // where (x_j - x_i) < 0  and i < j which should be true.
        decide.params.points[decide.params.points.length - (1 + decide.params.G_PTS + 1)] = new Point(10,1);
        decide.params.points[decide.params.points.length - 1] = new Point(0,0);
        assertTrue(decide.evaluateLIC_11());
    }

    @Test
    void test_evaluate_LIC_11_false() {
        Computations decide = new Computations(parameters);
        decide.params.G_PTS = 1;
        // Set all points to (0,0)
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(0,0);
        }
        // Create a set of two points (x_i,y_i) and (x_j,y_j) separated by G_PTS consecutive intervening points
        // where (x_j - x_i) > 0  and i < j which should be false.
        decide.params.points[decide.params.points.length - (1 + decide.params.G_PTS + 1)] = new Point(0,1);
        decide.params.points[decide.params.points.length - 1] = new Point(10,1);
        assertFalse(decide.evaluateLIC_11());
    }

    @Test
    void test_evaluate_LIC_11_false_params_G_PTS() {
        Computations decide = new Computations(parameters);
        decide.params.G_PTS = 0;
        assertFalse(decide.evaluateLIC_11());
    }

    @Test
    void test_evaluate_LIC_12_true_1() {
        Computations decide = new Computations(parameters);
        decide.params.K_PTS = 2;
        decide.params.LENGTH1= 1;
        decide.params.LENGTH2= 10;
        // Set all points to (0,0)
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(0,0);
        }
        // Update two points so that they have a distance greater than LENGTH1, but less than LENGTH2
        // and are separated by K_PTS
        decide.params.points[3] = new Point(2,2);
        decide.params.points[6] = new Point(4,4);
        assertTrue(decide.evaluateLIC_12());
        }

  
    @Test
    void test_evaluate_LIC_12_true_2() {
        Computations decide = new Computations(parameters);
        decide.params.K_PTS = 2;
        decide.params.LENGTH1= 2;
        decide.params.LENGTH2= 5;
         // Set all points to (0,0)
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(0,0);
        }
        // Update two points so that they have a distance greater than LENGTH1 and LENGTH2
        // and are separated by K_PTS
        decide.params.points[3] = new Point(2,2);
        decide.params.points[6] = new Point(10,10);
        // Update two points so that they have a distance less than LENGTH1 and LENGTH2
        // and are separated by K_PTS
        decide.params.points[12] = new Point(2,2);
        decide.params.points[15] = new Point(3,3);
        assertTrue(decide.evaluateLIC_12());
    }

    @Test
    void test_evaluate_LIC_12_false() {
        Computations decide = new Computations(parameters);
        decide.params.K_PTS = 2;
        decide.params.LENGTH1= 4;
        decide.params.LENGTH2= 5;
        int increase=0;
        //set all points so that they are all (including points that are K_PTS apart) separated by a distance 
        //that is greater than both LENGTH1 and LENGTH2
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(increase ,increase);
            increase=increase+10;
        }
        assertFalse(decide.evaluateLIC_12());
    }

    @Test
    void test_evaluate_LIC_12_false_params_LENGTH2() {
        Computations decide = new Computations(parameters);
        decide.params.LENGTH2 = -1;
        assertFalse(decide.evaluateLIC_12());
    }

    // Meets conditions both for radius 1 and radius 2
    @Test
    void test_evaluate_LIC_13_true() {
        Computations decide = new Computations(parameters);
        decide.params.A_PTS = 10;
        decide.params.B_PTS =30;
        decide.params.RADIUS1 = 20;
        decide.params.RADIUS2 = 10;
        // Set all points to (0.1*i,0.1*i).  
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = new Point(0.1*i,0.1*i);
        }
        decide.params.points[11] = new Point(50,0);
        decide.params.points[42] = new Point(50,25);

        assertTrue(decide.evaluateLIC_13());
    }

    // Meets only condition for radius 1
    @Test
    void test_evaluate_LIC_13_false_1() {
         Computations decide = new Computations(parameters);
         decide.params.A_PTS = 10;
         decide.params.B_PTS = 30;
         decide.params.RADIUS1 = 10;
         decide.params.RADIUS2 = 0.1;
         // Set all points to (i,i).  
         for (int i = 0; i < decide.params.points.length; i++) {
             decide.params.points[i] = new Point(i,i);
         }

        assertFalse(decide.evaluateLIC_13());
    }

    // Meets only condition for radius 2
    @Test
    void test_evaluate_LIC_13_false_2() {
         Computations decide = new Computations(parameters);
         decide.params.A_PTS = 20;
         decide.params.B_PTS = 30;
         decide.params.RADIUS1 = 1000;
         decide.params.RADIUS2 = 100;
         // Set all points to (2i,2i).  
         for (int i = 0; i < decide.params.points.length; i++) {
             decide.params.points[i] = new Point(i,i);
         }

        assertFalse(decide.evaluateLIC_13());
    }

    // None of the conditions are met
    @Test
    void test_evaluate_LIC_13_false_3() {
         Computations decide = new Computations(parameters);
         decide.params.A_PTS = 2;
         decide.params.B_PTS = 3;
         decide.params.RADIUS1 = 1000;
         decide.params.RADIUS2 = 0.1;
         // Set all points to (2i,2i).  
         for (int i = 0; i < decide.params.points.length; i++) {
             decide.params.points[i] = new Point(i,i);
         }

        assertFalse(decide.evaluateLIC_13());
    }

    @Test
    void test_evaluate_LIC_13_false_params_RADIUS2() {
        Computations decide = new Computations(parameters);
        decide.params.RADIUS2 = -1;
        assertFalse(decide.evaluateLIC_13());
    }

    @Test
    void test_evaluate_LIC_14_true() {
        Computations decide = new Computations(parameters);
        decide.params.E_PTS = 1;
        decide.params.F_PTS = 1;
        decide.params.AREA1 = 5;
        decide.params.AREA2 = 19;

        int[] triangle = {0, 6, 6, 6, 6, 0};
        int i = 0;
        // Set all points to (0,6), (6,6), (6,0) which gives area 18
        for (int j = 0; j < decide.params.points.length; j++) {
            decide.params.points[j] = new Point(triangle[i],triangle[i+1]);
            i = (i + 2) % triangle.length;
        }

        assertTrue(decide.evaluateLIC_14());
    }

    @Test
    void test_evaluate_LIC_14_false_due_to_area1() {
        Computations decide = new Computations(parameters);
        decide.params.E_PTS = 1;
        decide.params.F_PTS = 1;
        decide.params.AREA1 = 20;
        decide.params.AREA2 = 19;

        // sets points to (0,6) (6,6) (6,0) iteratively
        int[] triangle = {0, 6, 6, 6, 6, 0};
        int i = 0;
        for (int j = 0; j < decide.params.points.length; j++) {
            decide.params.points[j] = new Point(triangle[i],triangle[i+1]);
            i = (i + 2) % triangle.length;
        }

        assertFalse(decide.evaluateLIC_14());
    }

    @Test
    void test_evaluate_LIC_14_false_due_to_area2() {
        Computations decide = new Computations(parameters);
        decide.params.E_PTS = 1;
        decide.params.F_PTS = 1;
        decide.params.AREA1 = 5;
        decide.params.AREA2 = 17;

        int[] triangle = {0, 6, 6, 6, 6, 0};
        int i = 0;
        for (int j = 0; j < decide.params.points.length; j++) {
            decide.params.points[j] = new Point(triangle[i],triangle[i+1]);
            i = (i + 2) % triangle.length;
        }

        assertFalse(decide.evaluateLIC_14());
    }

    @Test
    void test_evaluate_LIC_14_false_params_AREA2() {
        Computations decide = new Computations(parameters);
        decide.params.AREA2 = -1;
        assertFalse(decide.evaluateLIC_14());
    }

    @Test
    void test_launch_true() {
        Computations decide = new Computations(parameters);

        for (int i = 0; i < decide.params.FUV.length; i++) {
            decide.params.FUV[i] = true;
        }

        decide.launch();

        assertEquals(YES_LAUNCH, parameters.LAUNCH);
    }

    @Test
    void test_launch_false() {
        Computations decide = new Computations(parameters);
        for (int i = 0; i < decide.params.FUV.length; i++) {
            decide.params.FUV[i] = true;
        }
        decide.params.FUV[0] = false;

        decide.launch();

        assertEquals(NO_LAUNCH, parameters.LAUNCH);
    }

    @Test
    void test_generate_pum_elements() { 
        Computations decide = new Computations(parameters);
        decide.params.LCM[0][1] = Parameters.Connectors.ANDD;
        decide.params.LCM[1][2] = Parameters.Connectors.ANDD;

        decide.params.LCM[0][2] = Parameters.Connectors.ORR;
        decide.params.LCM[0][3] = Parameters.Connectors.ORR;

        decide.params.LCM[2][3] = Parameters.Connectors.NOTUSED;

        decide.params.CMV[0] = false;
        decide.params.CMV[1] = true;
        decide.params.CMV[2] = true;
        decide.params.CMV[3] = false;
        decide.generatePUM();
        // false ANDD true == false
        assertFalse(decide.params.PUM[0][1]);
        // true ANDD true == true
        assertTrue(decide.params.PUM[1][2]);
        // false ORR true == true
        assertTrue(decide.params.PUM[0][2]);
        // false ORR false == false
        assertFalse(decide.params.PUM[0][3]);
        // _ NOTUSED _ == true
        assertTrue(decide.params.PUM[2][3]);
    }

    @Test
    void test_generate_FUV_elements() {
        Computations decide = new Computations(parameters);
        // Set elements in the first three rows of PUM to true
        for (int i = 0; i < 3; i++) 
            for (int j = 0; j < decide.params.PUM.length; j++)
                decide.params.PUM[i][j] = true;
        // Set first three PUV elements to true 
        for (int i = 0; i < 3 ; i++)
            decide.params.PUV[i] = true;
        // Set one element in third row of PUM to false
        decide.params.PUM[2][8] = false;
        // Set second element of PUV to false
        decide.params.PUV[1] = false;

        decide.generateFUV();
        // FUV[0] is true since PUV[0] is true and PUM[0][i] is true for all i 
        assertTrue(decide.params.FUV[0]);
        // FUV[1] is true since PUV[1] is false.
        assertTrue(decide.params.FUV[1]);
        // FUV[2] is false since PUV[2] is true and PUM[2][i] is not true for all i
        assertFalse(decide.params.FUV[2]);
    }

     @Test
     void shouldThrowExceptionForNumpoints() {
        parameters.NUMPOINTS = 101;
        assertThrows(Exception.class, () -> { Computations computations = new Computations(parameters);});
     }
}