package airport.gates;

import java.time.LocalTime;

public class GateSelector {

    private final GateStrategy nearest = new NearestGateStrategy();
    private final GateStrategy rotating = new RotatingGateStrategy();
    private final GateStrategy bestFit = new BestFitGateStrategy();

    public GateStrategy select(Flight flight, LocalTime time) {
        if (isNight(time)) {
            return nearest;
        }
        if (isRushHour(time)) {
            return rotating;
        }
        if (flight.size() == AircraftSize.LARGE) {
            return nearest;
        }
        return bestFit;
    }

    private boolean isNight(LocalTime time) {
        return !time.isBefore(LocalTime.of(22, 0)) || time.isBefore(LocalTime.of(6, 0));
    }

    private boolean isRushHour(LocalTime time) {
        return isInRange(time, 7, 10) || isInRange(time, 17, 20);
    }

    private boolean isInRange(LocalTime time, int fromHour, int toHour) {
        return !time.isBefore(LocalTime.of(fromHour, 0)) && time.isBefore(LocalTime.of(toHour, 0));
    }
}