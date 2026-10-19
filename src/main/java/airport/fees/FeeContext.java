package airport.fees;

import airport.baggage.Baggage;

public class FeeContext {

    private FeeStrategy strategy;

    public FeeContext(FeeStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(FeeStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculate(Baggage baggage) {
        return strategy.calculate(baggage);
    }
}