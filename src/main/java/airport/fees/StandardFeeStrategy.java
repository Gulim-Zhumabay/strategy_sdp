package airport.fees;

import airport.baggage.Baggage;

public class StandardFeeStrategy implements FeeStrategy {

    private static final double FREE_LIMIT_KG = 23;
    private static final double PRICE_PER_EXTRA_KG = 2000;

    @Override
    public double calculate(Baggage baggage) {
        double extraKg = Math.max(0, baggage.weightKg() - FREE_LIMIT_KG);
        return extraKg * PRICE_PER_EXTRA_KG;
    }
}