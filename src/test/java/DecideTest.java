import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DecideTest {
    @Test 
    void test_evaluate_LIC_0_consecutive() {
        Decide decide = new Decide();
        decide.params.LENGTH1 = 2;

        // Put each point on the diagonal line y = x so that the distance between them is sqrt(2).
        for (int i = 0; i < decide.params.points.length; i++) {
            decide.params.points[i] = decide.new Point(i,i);
        }
        // Put one point closer to the next so that the distance between them <= LENGTH1.
        decide.params.points[35] = decide.new Point(35.9, 35.9);
        assertTrue(decide.evaluateLIC_0());
    }

    @Test 
    void test_evaluate_LIC_0_non_consecutive() {
       Decide decide = new Decide();
        decide.params.LENGTH1 = 1;

        // Put each point on the diagonal line y = x so that the distance between them is sqrt(2) which is greater than LENGTH1.
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
}