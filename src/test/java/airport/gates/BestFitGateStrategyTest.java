package airport.gates;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BestFitGateStrategyTest {

    private final GateStrategy strategy = new BestFitGateStrategy();

    private final Gate large = new Gate("A1", 3, AircraftSize.LARGE);
    private final Gate medium = new Gate("A2", 8, AircraftSize.MEDIUM);
    private final Gate smallFar = new Gate("A3", 9, AircraftSize.SMALL);
    private final Gate smallNear = new Gate("A4", 2, AircraftSize.SMALL);

    @Test
    void picksSmallestGateThatFits() {
        Flight flight = new Flight("AB101", AircraftSize.MEDIUM);

        assertEquals(Optional.of(medium), strategy.chooseGate(flight, List.of(large, medium, smallFar)));
    }

    @Test
    void usesWalkingTimeWhenSizesAreEqual() {
        Flight flight = new Flight("AB102", AircraftSize.SMALL);

        assertEquals(Optional.of(smallNear), strategy.chooseGate(flight, List.of(smallFar, smallNear, medium, large)));
    }

    @Test
    void returnsEmptyWhenNoGateFits() {
        Flight flight = new Flight("AB103", AircraftSize.LARGE);

        assertEquals(Optional.empty(), strategy.chooseGate(flight, List.of(medium, smallFar)));
    }
}