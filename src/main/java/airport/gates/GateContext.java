package airport.gates;

import java.util.List;
import java.util.Optional;

public class GateContext {

    private GateStrategy strategy;

    public GateContext(GateStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(GateStrategy strategy) {
        this.strategy = strategy;
    }

    public Optional<Gate> assignGate(Flight flight, List<Gate> gates) {
        return strategy.chooseGate(flight, gates);
    }
}