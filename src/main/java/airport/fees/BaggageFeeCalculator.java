package airport.fees;

import airport.baggage.Baggage;
import airport.rates.ExchangeRateProvider;

import java.util.EnumMap;
import java.util.Map;

public class BaggageFeeCalculator {

    private final Map<FeeType, FeeStrategy> strategies = new EnumMap<>(FeeType.class);

    public BaggageFeeCalculator(ExchangeRateProvider rates) {
        strategies.put(FeeType.STANDARD, new StandardFeeStrategy());
        strategies.put(FeeType.FREQUENT_FLYER, new FrequentFlyerFeeStrategy());
        strategies.put(FeeType.INTERNATIONAL, new InternationalFeeStrategy(rates));
    }

    public double calculate(FeeType type, Baggage baggage) {
        return new FeeContext(strategies.get(type)).calculate(baggage);
    }
}