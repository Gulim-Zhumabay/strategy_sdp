package airport.fees;

import airport.baggage.Baggage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FeeStrategiesTest {

    static Stream<FeeStrategy> allStrategies() {
        return Stream.of(
                new StandardFeeStrategy(),
                new FrequentFlyerFeeStrategy(),
                new InternationalFeeStrategy(() -> 500.0)
        );
    }

    @ParameterizedTest
    @MethodSource("allStrategies")
    void feeIsNeverNegative(FeeStrategy strategy) {
        double[] weights = {0.1, 5, 20, 23, 32, 50, 100};

        for (double weight : weights) {
            assertTrue(strategy.calculate(new Baggage(weight)) >= 0);
        }
    }
}