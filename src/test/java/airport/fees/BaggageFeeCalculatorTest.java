package airport.fees;

import airport.baggage.Baggage;
import airport.rates.ExchangeRateProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BaggageFeeCalculatorTest {

    private static final double DELTA = 0.001;

    private final ExchangeRateProvider fixedRate = () -> 500.0;
    private final BaggageFeeCalculator calculator = new BaggageFeeCalculator(fixedRate);

    @Test
    void standardIsFreeBelowLimit() {
        assertEquals(0, calculator.calculate(FeeType.STANDARD, new Baggage(10)), DELTA);
    }

    @Test
    void standardIsFreeExactlyAtLimit() {
        assertEquals(0, calculator.calculate(FeeType.STANDARD, new Baggage(23)), DELTA);
    }

    @Test
    void standardChargesPerExtraKilogram() {
        assertEquals(4000, calculator.calculate(FeeType.STANDARD, new Baggage(25)), DELTA);
    }

    @Test
    void frequentFlyerIsFreeExactlyAtHigherLimit() {
        assertEquals(0, calculator.calculate(FeeType.FREQUENT_FLYER, new Baggage(32)), DELTA);
    }

    @Test
    void frequentFlyerPaysFlatFeeOverLimit() {
        assertEquals(15000, calculator.calculate(FeeType.FREQUENT_FLYER, new Baggage(35)), DELTA);
    }

    @Test
    void frequentFlyerFlatFeeDoesNotGrowWithWeight() {
        assertEquals(15000, calculator.calculate(FeeType.FREQUENT_FLYER, new Baggage(50)), DELTA);
    }

    @Test
    void internationalChargesOnlyBaseFeeUpToLimit() {
        assertEquals(15000, calculator.calculate(FeeType.INTERNATIONAL, new Baggage(20)), DELTA);
    }

    @Test
    void internationalAddsExtraKilogramsOverLimit() {
        assertEquals(35000, calculator.calculate(FeeType.INTERNATIONAL, new Baggage(25)), DELTA);
    }

    @Test
    void internationalUsesTheRateFromProvider() {
        BaggageFeeCalculator other = new BaggageFeeCalculator(() -> 400.0);

        assertEquals(12000, other.calculate(FeeType.INTERNATIONAL, new Baggage(20)), DELTA);
    }
}