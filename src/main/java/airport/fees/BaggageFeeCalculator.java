package airport.fees;

import airport.baggage.Baggage;
import airport.rates.ExchangeRateProvider;

public class BaggageFeeCalculator {

    private final ExchangeRateProvider rates;

    public BaggageFeeCalculator(ExchangeRateProvider rates) {
        this.rates = rates;
    }

    public double calculate(FeeType type, Baggage baggage) {
        double weight = baggage.weightKg();
        switch (type) {
            case STANDARD:
                if (weight <= 23) {
                    return 0;
                }
                return (weight - 23) * 2000;
            case FREQUENT_FLYER:
                if (weight <= 32) {
                    return 0;
                }
                return 15000;
            case INTERNATIONAL:
                double usd = 30;
                if (weight > 20) {
                    usd += (weight - 20) * 8;
                }
                return usd * rates.usdRate();
            default:
                throw new IllegalArgumentException("Unknown fee type: " + type);
        }
    }
}