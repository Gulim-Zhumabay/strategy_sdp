package airport.fees;

import airport.baggage.Baggage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FrequentFlyerFeeStrategyTest {

    private static final double DELTA = 0.001;

    private final FeeStrategy strategy = new FrequentFlyerFeeStrategy();

    @Test
    void isFreeBelowLimit() {
        assertEquals(0, strategy.calculate(new Baggage(20)), DELTA);
    }

    @Test
    void isFreeAtLimit() {
        assertEquals(0, strategy.calculate(new Baggage(32)), DELTA);
    }

    @Test
    void chargesFlatFeeJustOverLimit() {
        assertEquals(15000, strategy.calculate(new Baggage(32.1)), DELTA);
    }

    @Test
    void flatFeeStaysTheSameForVeryHeavyBag() {
        assertEquals(15000, strategy.calculate(new Baggage(60)), DELTA);
    }
}