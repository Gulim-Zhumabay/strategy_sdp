package airport.fees;

import airport.baggage.Baggage;

public interface FeeStrategy {

    double calculate(Baggage baggage);
}