package airport.gates;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BestFitGateStrategy implements GateStrategy {

    @Override
    public Optional<Gate> chooseGate(Flight flight, List<Gate> gates) {
        return gates.stream()
                .filter(gate -> gate.canHandle(flight))
                .min(Comparator.comparing(Gate::maxSize).thenComparingInt(Gate::walkingMinutes));
    }
}