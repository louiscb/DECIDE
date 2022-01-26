import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DecideTest {

    @Test
    void main() {
        String s = Decide.outputString();
        assertEquals("Hello world!", s);
    }
}