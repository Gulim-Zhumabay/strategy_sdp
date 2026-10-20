package airport.fees;

import airport.baggage.Baggage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FeeContextTest {

    private static final double DELTA = 0.001;

    private final Baggage heavyBag = new Baggage(35);

    @Test
    void usesStrategyFromConstructor() {
        FeeContext context = new FeeContext(baggage -> 123);

        assertEquals(123, context.calculate(heavyBag), DELTA);
    }

    @Test
    void passesBaggageToStrategy() {
        FeeContext context = new FeeContext(baggage -> baggage.weightKg() * 2);

        assertEquals(70, context.calculate(heavyBag), DELTA);
    }

    @Test
    void setStrategyReplacesStrategyAtRuntime() {
        FeeContext context = new FeeContext(new StandardFeeStrategy());
        assertEquals(24000, context.calculate(heavyBag), DELTA);

        context.setStrategy(new FrequentFlyerFeeStrategy());

        assertEquals(15000, context.calculate(heavyBag), DELTA);
    }
}