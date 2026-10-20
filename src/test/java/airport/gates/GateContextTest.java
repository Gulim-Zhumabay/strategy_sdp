package airport.gates;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GateContextTest {

    private final Flight flight = new Flight("AB101", AircraftSize.MEDIUM);
    private final Gate large = new Gate("A1", 3, AircraftSize.LARGE);
    private final Gate medium = new Gate("A2", 8, AircraftSize.MEDIUM);
    private final List<Gate> gates = List.of(large, medium);

    @Test
    void usesStrategyFromConstructor() {
        GateContext context = new GateContext((f, g) -> Optional.of(medium));

        assertEquals(Optional.of(medium), context.assignGate(flight, gates));
    }

    @Test
    void setStrategyReplacesStrategyAtRuntime() {
        GateContext context = new GateContext(new NearestGateStrategy());
        assertEquals(Optional.of(large), context.assignGate(flight, gates));

        context.setStrategy(new BestFitGateStrategy());

        assertEquals(Optional.of(medium), context.assignGate(flight, gates));
    }
}