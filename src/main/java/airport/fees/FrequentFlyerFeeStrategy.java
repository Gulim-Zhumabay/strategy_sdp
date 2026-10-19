package airport.fees;

import airport.baggage.Baggage;

public class FrequentFlyerFeeStrategy implements FeeStrategy {

    private static final double FREE_LIMIT_KG = 32;
    private static final double FLAT_FEE = 15000;

    @Override
    public double calculate(Baggage baggage) {
        if (baggage.weightKg() <= FREE_LIMIT_KG) {
            return 0;
        }
        return FLAT_FEE;
    }
}