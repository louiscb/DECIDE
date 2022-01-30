import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DecideTest {
    @Test 
    void test_evaluate_LIC_0_consecutive() {
        Decide decide = new Decide();
        decide.params.LENGTH1 = 2;

        // Put each point on the diagonal line y = x so that the distance between them is sqrt(2) < LENGTH1.
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = decide.new Point(i,i);
        }
        // Put (35,35) on (34, 34) so that the distance to the next point (36, 36) is > LENGTH1.
        decide.params.points[35] = decide.new Point(34, 34);
        assertTrue(decide.evaluateLIC_0());
    }

    @Test 
    void test_evaluate_LIC_0_non_consecutive() {
        Decide decide = new Decide();
        decide.params.LENGTH1 = 2;

        // Put each point on the diagonal line y = x so that the distance between is sqrt(2) < LENGTH1.
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = decide.new Point(i,i);
        }
        assertFalse(decide.evaluateLIC_0());
    }

    @Test 
    void test_evaluate_LIC_1_true() {
        Decide decide = new Decide();
        decide.params.RADIUS1 = 1;
       // set all three consecutive points near each other, within a radius less than 1.
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = decide.new Point(i*0.01,i*0.015);
        }
        // change the last three consecutive points so that RADIUS1 is to small 
        decide.params.points[decide.params.points.length-3] = decide.new Point(0,0);
        decide.params.points[decide.params.points.length-2] = decide.new Point(5,0);
        decide.params.points[decide.params.points.length-1] = decide.new Point(10,5);
        
        assertTrue(decide.evaluateLIC_1());
    }

    @Test 
    void test_evaluate_LIC_1_false() {
        Decide decide = new Decide();
        decide.params.RADIUS1 = 1;
         // set all three consecutive points near each other, within a radius less than 1. 
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = decide.new Point(i*0.01,i*0.015);
        }
        assertFalse(decide.evaluateLIC_1());
    }

    @Test
    void test_evaluate_LIC_3_true() {
        Decide decide = new Decide();
        decide.params.AREA1 = 5;
        // Set all points to (0,0)
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = decide.new Point(0,0);
        }
        // Update two points so that the area of the triangle will be > AREA1
        decide.params.points[1] = decide.new Point(3,3);
        decide.params.points[2] = decide.new Point(5,0);
        assertTrue(decide.evaluateLIC_3());
    }

    @Test
    void test_evaluate_LIC_3_false() {
        Decide decide = new Decide();
        decide.params.AREA1 = 5;
        // Set all points to (0,0)
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = decide.new Point(0,0);
        }
        assertFalse(decide.evaluateLIC_3());
    }

    @Test
    void test_evaluate_LIC_5_false() {
        Decide decide = new Decide();
        decide.params.LENGTH1 = 2;
        // Put points on (0,0), (1,0), ..., (99,0).
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = decide.new Point(i,0);
        }
        assertFalse(decide.evaluateLIC_5());
    }

    @Test
    void test_evaluate_LIC_5_true() {
        Decide decide = new Decide();
        decide.params.LENGTH1 = 2;
        // Put points on (0,0), (1,0), ..., (99,0).
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = decide.new Point(i,0);
        }
        // Move (4,0) to (2.5,0) so that its x-coordinate is lesser than that of the point preceding it.
        decide.params.points[4] = decide.new Point(2.5,0);
        assertTrue(decide.evaluateLIC_5());
    }

    @Test
    void test_evaluate_LIC_7_true() {
        Decide decide = new Decide();
        decide.params.K_PTS = 2;
        decide.params.LENGTH1= 1;
        // Set all points to (0,0)
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = decide.new Point(0,0);
        }
        // Update two points so that they have a distance greater than length and are separated by K_PTS
        decide.params.points[3] = decide.new Point(50,0);
        decide.params.points[6] = decide.new Point(50,50);
        assertTrue(decide.evaluateLIC_7());
    }

    @Test
    void test_evaluate_LIC_7_false() {
        Decide decide = new Decide();
        decide.params.K_PTS = 2;
        decide.params.LENGTH1= 50;
        // Set all points to (0,0)
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = decide.new Point(0,0);
        }
        // Update two points, but there distance is not greater than LENGTH1
        decide.params.points[3] = decide.new Point(1,1);
        decide.params.points[6] = decide.new Point(2,2);
        assertFalse(decide.evaluateLIC_7());
    }

    @Test
    void test_evaluate_LIC_8_true() {
        Decide decide = new Decide();
        decide.params.A_PTS = 2;
        decide.params.B_PTS = 3;
        decide.params.RADIUS1 = 5;
        // Set all points to (0,0)
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = decide.new Point(0,0);
        }
        // Update two points so that three points can not put within the RADIUS1
        decide.params.points[3] = decide.new Point(50,0);
        decide.params.points[7] = decide.new Point(50,50);
        assertTrue(decide.evaluateLIC_8());
    }

    @Test
    void test_evaluate_LIC_8_false() {
        Decide decide = new Decide();
        decide.params.A_PTS = 2;
        decide.params.B_PTS = 3;
        decide.params.RADIUS1 = 5;
        // Set all points to (0,0)
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = decide.new Point(0,0);
        }
        // Update two points but three points are still within the RADIUS1
        decide.params.points[3] = decide.new Point(1,0);
        decide.params.points[7] = decide.new Point(1,1);
        assertFalse(decide.evaluateLIC_8());
    }

    @Test
    void test_evaluate_LIC_10_true() {
        Decide decide = new Decide();
        decide.params.E_PTS = 25;
        decide.params.F_PTS = 50;
        decide.params.AREA1 = 5;
        // Set all points to (0,0)
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = decide.new Point(0,0);
        }
        // Create points that are separated by E_PTS and F_PTS points and that form a triangle with area 8 > AREA1.
        decide.params.points[10] = decide.new Point(50,50);
        decide.params.points[10 + decide.params.E_PTS + 1] = decide.new Point(54,50);
        decide.params.points[10 + decide.params.E_PTS + decide.params.F_PTS + 2] = decide.new Point(54,54);
        assertTrue(decide.evaluateLIC_10());
    }

    @Test
    void test_evaluate_LIC_10_false_1() {
        Decide decide = new Decide();
        decide.params.E_PTS = 25;
        decide.params.F_PTS = 50;
        decide.params.AREA1 = 5;
        // Set all points to (0,0)
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = decide.new Point(0,0);
        }
        // Create points that are NOT separated by E_PTS and F_PTS points and that form a triangle with area 8 > AREA1.
        decide.params.points[10] = decide.new Point(50,50);
        decide.params.points[10 + decide.params.E_PTS + 3] = decide.new Point(54,50);
        decide.params.points[10 + decide.params.E_PTS + decide.params.F_PTS + 5] = decide.new Point(54,54);
        assertFalse(decide.evaluateLIC_10());   
    }

    @Test
    void test_evaluate_LIC_10_false_2() {
        Decide decide = new Decide();
        decide.params.E_PTS = 25;
        decide.params.F_PTS = 50;
        decide.params.AREA1 = 5;
        // Set all points to (0,0)
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = decide.new Point(0,0);
        }
        // Create points that are separated by E_PTS and F_PTS points and that form a triangle with area 2 < AREA1.
        decide.params.points[10] = decide.new Point(50,50);
        decide.params.points[10 + decide.params.E_PTS + 1] = decide.new Point(52,50);
        decide.params.points[10 + decide.params.E_PTS + decide.params.F_PTS + 2] = decide.new Point(52,52);
        assertFalse(decide.evaluateLIC_10());   
    }

    // Meets conditions both for radius 1 and radius 2
    @Test
    void test_evaluate_LIC_13_true() {
        Decide decide = new Decide();
        decide.params.A_PTS = 10;
        decide.params.B_PTS =30;
        decide.params.RADIUS1 = 20;
        decide.params.RADIUS2 = 10;
        // Set all points to (0.1*i,0.1*i).  
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = decide.new Point(0.1*i,0.1*i);
        }
        decide.params.points[11] = decide.new Point(50,0);
        decide.params.points[42] = decide.new Point(50,25);
    }

    // Meets only condition for radius 1
    @Test
    void test_evaluate_LIC_13_false_1() {
         Decide decide = new Decide();
         decide.params.A_PTS = 10;
         decide.params.B_PTS = 30;
         decide.params.RADIUS1 = 10;
         decide.params.RADIUS2 = 0.1;
         // Set all points to (i,i).  
         for (int i = 0; i < decide.params.points.length; i++) {
             decide.params.points[i] = decide.new Point(i,i);
         }
    }

    // Meets only condition for radius 2
    @Test
    void test_evaluate_LIC_13_false_2() {
         Decide decide = new Decide();
         decide.params.A_PTS = 20;
         decide.params.B_PTS = 30;
         decide.params.RADIUS1 = 1000;
         decide.params.RADIUS2 = 100;
         // Set all points to (2i,2i).  
         for (int i = 0; i < decide.params.points.length; i++) {
             decide.params.points[i] = decide.new Point(i,i);
         }
    }

    // None of the conditions are met
    @Test
    void test_evaluate_LIC_13_false_3() {
         Decide decide = new Decide();
         decide.params.A_PTS = 2;
         decide.params.B_PTS = 3;
         decide.params.RADIUS1 = 1000;
         decide.params.RADIUS2 = 0.1;
         // Set all points to (2i,2i).  
         for (int i = 0; i < decide.params.points.length; i++) {
             decide.params.points[i] = decide.new Point(i,i);
         }
    }

    @Test
    void test_launch_true() {
        Decide decide = new Decide();
        for (int i = 0; i < decide.params.FUV.length; i++) {
            decide.params.FUV[i] = true;
        }
        assertTrue(decide.launch());
    }

    @Test
    void test_launch_false() {
        Decide decide = new Decide();
        for (int i = 0; i < decide.params.FUV.length; i++) {
            decide.params.FUV[i] = true;
        }
        decide.params.FUV[0] = false; 
        assertFalse(decide.launch());
    }

    @Test
    void test_generate_pum_elements() { 
        Decide decide = new Decide();
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
        Decide decide = new Decide();
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
    void test_triangle_area() {
        Decide decide = new Decide();
        assertEquals(decide.triangleArea(decide.new Point(1,2), decide.new Point(3,6), decide.new Point(8,9)), 7);
        assertEquals(decide.triangleArea(decide.new Point(1,2), decide.new Point(4,8), decide.new Point(16,32)), 0);
    }

}