package airport.gates;

import java.util.List;
import java.util.Optional;

public interface GateStrategy {

    Optional<Gate> chooseGate(Flight flight, List<Gate> gates);
}