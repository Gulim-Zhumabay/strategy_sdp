package airport.gates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GateTest {

    @Test
    void handlesFlightOfTheSameSize() {
        Gate gate = new Gate("A1", 5, AircraftSize.MEDIUM);

        assertTrue(gate.canHandle(new Flight("AB101", AircraftSize.MEDIUM)));
    }

    @Test
    void handlesSmallerFlight() {
        Gate gate = new Gate("A1", 5, AircraftSize.LARGE);

        assertTrue(gate.canHandle(new Flight("AB101", AircraftSize.SMALL)));
    }

    @Test
    void doesNotHandleBiggerFlight() {
        Gate gate = new Gate("A1", 5, AircraftSize.SMALL);

        assertFalse(gate.canHandle(new Flight("AB101", AircraftSize.LARGE)));
    }

    @Test
    void rejectsNegativeWalkingTime() {
        assertThrows(IllegalArgumentException.class, () -> new Gate("A1", -1, AircraftSize.SMALL));
    }
}