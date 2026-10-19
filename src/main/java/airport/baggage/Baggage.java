package airport.baggage;

public record Baggage(double weightKg) {

    public Baggage {
        if (weightKg <= 0) {
            throw new IllegalArgumentException("Weight must be positive");
        }
    }
}