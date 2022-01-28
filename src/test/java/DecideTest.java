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
    void test_launch_true() {
        Decide decide = new Decide();
        for (int i = 0; i < decide.params.FUV.length; i++) {
            decide.params.FUV[i] = true;
        }
        assertTrue(decide.launch(decide.params.FUV));
    }

    @Test
    void test_launch_false() {
        Decide decide = new Decide();
        for (int i = 0; i < decide.params.FUV.length; i++) {
            decide.params.FUV[i] = true;
        }
        decide.params.FUV[0] = false; 
        assertFalse(decide.launch(decide.params.FUV));
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
}