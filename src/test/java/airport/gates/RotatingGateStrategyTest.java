package airport.gates;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RotatingGateStrategyTest {

    private final GateStrategy strategy = new RotatingGateStrategy();

    private final Gate large = new Gate("A1", 5, AircraftSize.LARGE);
    private final Gate medium = new Gate("A2", 5, AircraftSize.MEDIUM);
    private final Gate small = new Gate("A3", 5, AircraftSize.SMALL);
    private final List<Gate> gates = List.of(large, medium, small);

    @Test
    void cyclesThroughAllSuitableGates() {
        Flight flight = new Flight("AB101", AircraftSize.SMALL);

        assertEquals(Optional.of(large), strategy.chooseGate(flight, gates));
        assertEquals(Optional.of(medium), strategy.chooseGate(flight, gates));
        assertEquals(Optional.of(small), strategy.chooseGate(flight, gates));
        assertEquals(Optional.of(large), strategy.chooseGate(flight, gates));
    }

    @Test
    void skipsGatesThatAreTooSmall() {
        Flight flight = new Flight("AB102", AircraftSize.MEDIUM);

        assertEquals(Optional.of(large), strategy.chooseGate(flight, gates));
        assertEquals(Optional.of(medium), strategy.chooseGate(flight, gates));
        assertEquals(Optional.of(large), strategy.chooseGate(flight, gates));
    }

    @Test
    void returnsEmptyWhenNoGateFits() {
        Flight flight = new Flight("AB103", AircraftSize.LARGE);

        assertEquals(Optional.empty(), strategy.chooseGate(flight, List.of(medium, small)));
    }
}