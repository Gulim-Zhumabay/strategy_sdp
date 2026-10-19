package airport.fees;

import airport.baggage.Baggage;
import airport.rates.ExchangeRateProvider;

public class InternationalFeeStrategy implements FeeStrategy {

    private static final double BASE_FEE_USD = 30;
    private static final double FREE_LIMIT_KG = 20;
    private static final double PRICE_PER_EXTRA_KG_USD = 8;

    private final ExchangeRateProvider rates;

    public InternationalFeeStrategy(ExchangeRateProvider rates) {
        this.rates = rates;
    }

    @Override
    public double calculate(Baggage baggage) {
        double extraKg = Math.max(0, baggage.weightKg() - FREE_LIMIT_KG);
        double usd = BASE_FEE_USD + extraKg * PRICE_PER_EXTRA_KG_USD;
        return usd * rates.usdRate();
    }
}