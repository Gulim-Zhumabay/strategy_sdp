package airport;

import airport.baggage.Baggage;
import airport.fees.FeeContext;
import airport.fees.FeeStrategy;
import airport.fees.FrequentFlyerFeeStrategy;
import airport.fees.InternationalFeeStrategy;
import airport.fees.StandardFeeStrategy;
import airport.gates.AircraftSize;
import airport.gates.BestFitGateStrategy;
import airport.gates.Flight;
import airport.gates.Gate;
import airport.gates.GateContext;
import airport.gates.GateSelector;
import airport.gates.GateStrategy;
import airport.gates.NearestGateStrategy;
import airport.gates.RotatingGateStrategy;
import airport.rates.ExchangeRateProvider;
import airport.rates.SimulatedExchangeRateProvider;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class Demo {

    private static final ExchangeRateProvider RATES = new SimulatedExchangeRateProvider();

    private static final List<Gate> GATES = List.of(
            new Gate("A2", 8, AircraftSize.MEDIUM),
            new Gate("A1", 3, AircraftSize.LARGE),
            new Gate("A3", 1, AircraftSize.SMALL),
            new Gate("A4", 5, AircraftSize.MEDIUM)
    );

    public static void main(String[] args) {
        showAllFeeStrategies();
        showAllGateStrategies();
        showManualAndAutomaticChoice();
        showSwitchingAtRuntime();
    }

    private static void showAllFeeStrategies() {
        Baggage baggage = new Baggage(27);
        System.out.println("1. One bag of 27 kg, every fee strategy");
        printFee("Standard", new StandardFeeStrategy(), baggage);
        printFee("Frequent flyer", new FrequentFlyerFeeStrategy(), baggage);
        printFee("International", new InternationalFeeStrategy(RATES), baggage);
        System.out.println();
    }

    private static void showAllGateStrategies() {
        Flight flight = new Flight("AB101", AircraftSize.MEDIUM);
        System.out.println("2. Flight AB101 (medium aircraft), every gate strategy");
        printGate("Nearest", new NearestGateStrategy().chooseGate(flight, GATES));
        printGate("Best fit", new BestFitGateStrategy().chooseGate(flight, GATES));
        printGate("Rotating", new RotatingGateStrategy().chooseGate(flight, GATES));
        System.out.println();
    }

    private static void showManualAndAutomaticChoice() {
        Flight medium = new Flight("AB101", AircraftSize.MEDIUM);
        Flight large = new Flight("AB202", AircraftSize.LARGE);
        GateContext context = new GateContext(new BestFitGateStrategy());
        GateSelector selector = new GateSelector();

        System.out.println("3. Manual choice and automatic choice by the selector");
        System.out.println("  Manual: BestFitGateStrategy, AB101 -> " + describe(context.assignGate(medium, GATES)));
        assignAutomatically(context, selector, medium, LocalTime.of(3, 0));
        assignAutomatically(context, selector, medium, LocalTime.of(8, 0));
        assignAutomatically(context, selector, medium, LocalTime.of(12, 0));
        assignAutomatically(context, selector, large, LocalTime.of(12, 0));
        System.out.println();
    }

    private static void showSwitchingAtRuntime() {
        System.out.println("4. Switching the strategy while the program runs");

        Baggage baggage = new Baggage(35);
        FeeContext feeContext = new FeeContext(new StandardFeeStrategy());
        System.out.printf("  Fee, standard:       %.0f%n", feeContext.calculate(baggage));
        feeContext.setStrategy(new FrequentFlyerFeeStrategy());
        System.out.printf("  Fee, frequent flyer: %.0f%n", feeContext.calculate(baggage));
        feeContext.setStrategy(new InternationalFeeStrategy(RATES));
        System.out.printf("  Fee, international:  %.0f%n", feeContext.calculate(baggage));

        Flight flight = new Flight("AB303", AircraftSize.MEDIUM);
        GateContext gateContext = new GateContext(new NearestGateStrategy());
        System.out.println("  Gate, nearest: " + describe(gateContext.assignGate(flight, GATES)));
        gateContext.setStrategy(new RotatingGateStrategy());
        for (int i = 1; i <= 3; i++) {
            System.out.println("  Gate, rotating, flight " + i + ": " + describe(gateContext.assignGate(flight, GATES)));
        }
    }

    private static void assignAutomatically(GateContext context, GateSelector selector, Flight flight, LocalTime time) {
        GateStrategy strategy = selector.select(flight, time);
        context.setStrategy(strategy);
        System.out.printf("  Auto at %s, %s: %s -> %s%n",
                time, flight.number(), strategy.getClass().getSimpleName(), describe(context.assignGate(flight, GATES)));
    }

    private static void printFee(String name, FeeStrategy strategy, Baggage baggage) {
        System.out.printf("  %-15s %10.0f%n", name, strategy.calculate(baggage));
    }

    private static void printGate(String name, Optional<Gate> gate) {
        System.out.printf("  %-15s %s%n", name, describe(gate));
    }

    private static String describe(Optional<Gate> gate) {
        return gate.map(found -> found.id() + " (" + found.walkingMinutes() + " min walk)")
                .orElse("no gate fits");
    }
}