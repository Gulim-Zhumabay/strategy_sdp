package airport.gates;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;

class GateSelectorTest {

    private final GateSelector selector = new GateSelector();

    private GateStrategy select(AircraftSize size, int hour, int minute) {
        return selector.select(new Flight("AB101", size), LocalTime.of(hour, minute));
    }

    @Test
    void nightStartsAtTwentyTwo() {
        assertInstanceOf(NearestGateStrategy.class, select(AircraftSize.SMALL, 22, 0));
        assertInstanceOf(BestFitGateStrategy.class, select(AircraftSize.SMALL, 21, 59));
    }

    @Test
    void nightEndsAtSix() {
        assertInstanceOf(NearestGateStrategy.class, select(AircraftSize.SMALL, 5, 59));
        assertInstanceOf(BestFitGateStrategy.class, select(AircraftSize.SMALL, 6, 0));
    }

    @Test
    void nightUsesNearestAfterMidnight() {
        assertInstanceOf(NearestGateStrategy.class, select(AircraftSize.MEDIUM, 0, 0));
        assertInstanceOf(NearestGateStrategy.class, select(AircraftSize.MEDIUM, 3, 15));
    }

    @Test
    void morningRushUsesRotating() {
        assertInstanceOf(BestFitGateStrategy.class, select(AircraftSize.SMALL, 6, 59));
        assertInstanceOf(RotatingGateStrategy.class, select(AircraftSize.SMALL, 7, 0));
        assertInstanceOf(RotatingGateStrategy.class, select(AircraftSize.SMALL, 9, 59));
        assertInstanceOf(BestFitGateStrategy.class, select(AircraftSize.SMALL, 10, 0));
    }

    @Test
    void eveningRushUsesRotating() {
        assertInstanceOf(BestFitGateStrategy.class, select(AircraftSize.SMALL, 16, 59));
        assertInstanceOf(RotatingGateStrategy.class, select(AircraftSize.SMALL, 17, 0));
        assertInstanceOf(RotatingGateStrategy.class, select(AircraftSize.SMALL, 19, 59));
        assertInstanceOf(BestFitGateStrategy.class, select(AircraftSize.SMALL, 20, 0));
    }

    @Test
    void largeAircraftUsesNearestOutsideRush() {
        assertInstanceOf(NearestGateStrategy.class, select(AircraftSize.LARGE, 12, 0));
        assertInstanceOf(NearestGateStrategy.class, select(AircraftSize.LARGE, 10, 0));
    }

    @Test
    void otherFlightsUseBestFit() {
        assertInstanceOf(BestFitGateStrategy.class, select(AircraftSize.SMALL, 12, 0));
        assertInstanceOf(BestFitGateStrategy.class, select(AircraftSize.MEDIUM, 15, 0));
    }

    @Test
    void rushHourBeatsLargeAircraft() {
        assertInstanceOf(RotatingGateStrategy.class, select(AircraftSize.LARGE, 8, 0));
        assertInstanceOf(RotatingGateStrategy.class, select(AircraftSize.LARGE, 18, 0));
    }

    @Test
    void returnsTheSameRotatingStrategyEveryTime() {
        assertSame(select(AircraftSize.SMALL, 8, 0), select(AircraftSize.MEDIUM, 18, 0));
    }
}