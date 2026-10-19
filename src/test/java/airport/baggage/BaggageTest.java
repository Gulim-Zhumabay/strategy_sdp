package airport.baggage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BaggageTest {

    @Test
    void keepsWeight() {
        Baggage bag = new Baggage(20.5);

        assertEquals(20.5, bag.weightKg(), 0.001);
    }

    @Test
    void rejectsZeroWeight() {
        assertThrows(IllegalArgumentException.class, () -> new Baggage(0));
    }

    @Test
    void rejectsNegativeWeight() {
        assertThrows(IllegalArgumentException.class, () -> new Baggage(-3));
    }
}