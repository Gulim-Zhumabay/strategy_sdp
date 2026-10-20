# Airport Operations: Strategy Pattern

This is my Assignment 5 for the design patterns course. The project is about airport operations, the same domain I used in Assignment 4. It is written in Java 17 with Maven and JUnit 5.

I made two families of strategies. The first family calculates the fee for baggage: standard, frequent flyer and international. The second family chooses a gate for a flight: nearest, best fit and rotating. A class called GateSelector chooses the gate strategy by itself. It looks at the time of day and the size of the aircraft.

The international fee needs the dollar exchange rate. I hide this rate behind my own interface called ExchangeRateProvider. The program uses a simulated source, and in the tests I use a fake source that always returns the same number.

## How to run

To run all tests, use the command mvn test. To run the demo, first compile with mvn -q compile. Then run java -cp target/classes airport.Demo.

## Classes

| Class | Responsibility |
| --- | --- |
| Baggage | Holds the weight of one bag. |
| ExchangeRateProvider | Defines how the program asks for the current dollar exchange rate. |
| SimulatedExchangeRateProvider | Returns a fixed dollar exchange rate instead of calling a real service. |
| FeeType | Names the available baggage fee rules. |
| BaggageFeeCalculator | Gives older callers the original fee entry point, delegating each fee type to its strategy. |
| FeeStrategy | Declares the common operation that returns a baggage fee for one bag. |
| StandardFeeStrategy | Charges a price per kilogram above the free weight limit. |
| FrequentFlyerFeeStrategy | Charges one flat penalty when the bag exceeds the higher free weight limit. |
| InternationalFeeStrategy | Charges a base fee in dollars plus a price per extra kilogram, converted with the current rate. |
| FeeContext | Holds the current fee strategy, lets the program replace it, then runs it. |
| AircraftSize | Lists the three aircraft sizes in order from smallest to largest. |
| Flight | Holds the flight number with the aircraft size. |
| Gate | Describes one gate, with its walking time, size limit, plus a check for flight compatibility. |
| GateStrategy | Declares the common operation that chooses a gate for a flight from a list. |
| NearestGateStrategy | Chooses the suitable gate with the shortest walking time. |
| BestFitGateStrategy | Chooses the smallest suitable gate, keeping large gates free for large aircraft. |
| RotatingGateStrategy | Chooses suitable gates in turn, remembering the last position. |
| GateContext | Holds the current gate strategy, lets the program replace it, then runs it. |
| GateSelector | Picks the gate strategy that fits the time of day plus the aircraft size. |
| Demo | Prints a console example of every strategy, the selector, plus runtime switching. |

## How the selector works

GateSelector checks the rules from top to bottom, and the first rule that matches wins. At night, from 22:00 to 05:59, it uses NearestGateStrategy. In rush hour, from 07:00 to 09:59 or from 17:00 to 19:59, it uses RotatingGateStrategy. For a large aircraft at other times it uses NearestGateStrategy. In all other cases it uses BestFitGateStrategy.

## Why Strategy and not State or Template Method

Strategy is the right pattern here because in both families I have one job that can be done in several ways. The jobs are calculating a baggage fee and choosing a gate. Every way is a separate class that implements one interface. The context holds one strategy and just calls it. The program can choose the strategy from outside, for example with GateSelector, and it can change the strategy at any time with setStrategy().

It is not State. In State, an object changes its behavior when its own inner state changes, and the states often move the object to the next state by themselves. My strategies do not know about each other and never switch to each other. The choice always comes from outside: from the user, from the demo or from the selector. RotatingGateStrategy keeps a counter, but this counter is only private data of that one algorithm. It is not a life cycle of the context.

It is not Template Method. Template Method puts the skeleton of an algorithm in a base class, and subclasses fill in some steps. It uses inheritance, so an object cannot change its subclass while it is alive. My algorithms are different from the beginning to the end, and they have no shared skeleton. The context uses a strategy by composition, so I can change the algorithm while the program runs. I can also add a new algorithm as a new class without changing the context or the other strategies.

## Refactoring commits

I wrote the baggage fee family first in the bad way. All fee types were in one class with a switch. Then I refactored it into the Strategy pattern. The same tests pass in both commits.

Before refactoring (one class with a switch, tests added):

https://github.com/Gulim-Zhumabay/strategy_sdp/commit/32f0926cd2ffe8d43c3e99cb86105ec8ae3b9945

New strategy classes and context added:

https://github.com/Gulim-Zhumabay/strategy_sdp/commit/ffa405992961dfc646bc16273c2ea47ac6779f15

After refactoring (calculator uses the strategies):

https://github.com/Gulim-Zhumabay/strategy_sdp/commit/79a14575399ffa6f73f17c1a59a96b3e20932662

All changes between before and after:

https://github.com/Gulim-Zhumabay/strategy_sdp/compare/32f0926cd2ffe8d43c3e99cb86105ec8ae3b9945...79a14575399ffa6f73f17c1a59a96b3e20932662