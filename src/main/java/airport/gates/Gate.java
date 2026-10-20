package airport.gates;

public record Gate(String id, int walkingMinutes, AircraftSize maxSize) {

    public Gate {
        if (walkingMinutes < 0) {
            throw new IllegalArgumentException("Walking time cannot be negative");
        }
    }

    public boolean canHandle(Flight flight) {
        return maxSize.compareTo(flight.size()) >= 0;
    }
}