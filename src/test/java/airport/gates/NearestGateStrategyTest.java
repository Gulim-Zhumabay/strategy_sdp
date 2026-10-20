package airport.gates;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NearestGateStrategyTest {

    private final GateStrategy strategy = new NearestGateStrategy();

    private final Gate large = new Gate("A1", 3, AircraftSize.LARGE);
    private final Gate medium = new Gate("A2", 8, AircraftSize.MEDIUM);
    private final Gate small = new Gate("A3", 1, AircraftSize.SMALL);
    private final List<Gate> gates = List.of(large, medium, small);

    @Test
    void picksClosestGateAmongAllThatFit() {
        Flight flight = new Flight("AB101", AircraftSize.SMALL);

        assertEquals(Optional.of(small), strategy.chooseGate(flight, gates));
    }

    @Test
    void ignoresGatesThatAreTooSmall() {
        Flight flight = new Flight("AB102", AircraftSize.MEDIUM);

        assertEquals(Optional.of(large), strategy.chooseGate(flight, gates));
    }

    @Test
    void returnsEmptyWhenNoGateFits() {
        Flight flight = new Flight("AB103", AircraftSize.LARGE);

        assertEquals(Optional.empty(), strategy.chooseGate(flight, List.of(medium, small)));
    }
}