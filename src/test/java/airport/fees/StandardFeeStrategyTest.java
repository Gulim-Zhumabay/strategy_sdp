package airport.fees;

import airport.baggage.Baggage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StandardFeeStrategyTest {

    private static final double DELTA = 0.001;

    private final FeeStrategy strategy = new StandardFeeStrategy();

    @Test
    void isFreeBelowLimit() {
        assertEquals(0, strategy.calculate(new Baggage(15)), DELTA);
    }

    @Test
    void isFreeAtLimit() {
        assertEquals(0, strategy.calculate(new Baggage(23)), DELTA);
    }

    @Test
    void chargesEveryExtraKilogram() {
        assertEquals(2000, strategy.calculate(new Baggage(24)), DELTA);
    }

    @Test
    void chargesPartOfKilogram() {
        assertEquals(1000, strategy.calculate(new Baggage(23.5)), DELTA);
    }
}