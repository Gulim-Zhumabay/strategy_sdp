package airport.gates;

import java.util.List;
import java.util.Optional;

public class RotatingGateStrategy implements GateStrategy {

    private int next = 0;

    @Override
    public Optional<Gate> chooseGate(Flight flight, List<Gate> gates) {
        List<Gate> suitable = gates.stream()
                .filter(gate -> gate.canHandle(flight))
                .toList();
        if (suitable.isEmpty()) {
            return Optional.empty();
        }
        Gate chosen = suitable.get(next % suitable.size());
        next++;
        return Optional.of(chosen);
    }
}