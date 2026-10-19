package airport.rates;

public class SimulatedExchangeRateProvider implements ExchangeRateProvider {

    private static final double RATE = 450.0;

    @Override
    public double usdRate() {
        return RATE;
    }
}