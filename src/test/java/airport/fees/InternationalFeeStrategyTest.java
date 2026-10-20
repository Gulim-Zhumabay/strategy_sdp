package airport.fees;

import airport.baggage.Baggage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InternationalFeeStrategyTest {

    private static final double DELTA = 0.001;

    private static InternationalFeeStrategy withRate(double rate) {
        return new InternationalFeeStrategy(() -> rate);
    }

    @Test
    void chargesBaseFeeBelowLimit() {
        assertEquals(15000, withRate(500).calculate(new Baggage(10)), DELTA);
    }

    @Test
    void chargesBaseFeeAtLimit() {
        assertEquals(15000, withRate(500).calculate(new Baggage(20)), DELTA);
    }

    @Test
    void addsExtraKilogramsInDollars() {
        assertEquals(23000, withRate(500).calculate(new Baggage(22)), DELTA);
    }

    @Test
    void usesRateFromProvider() {
        assertEquals(3000, withRate(100).calculate(new Baggage(20)), DELTA);
    }
}