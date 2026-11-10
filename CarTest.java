import java.awt.Color;

/** Small, dependency-free unit-test suite for the assignment concepts. */
public class CarTest {
    private static int passed = 0;
    private static int failed = 0;
    private static final double EPSILON = 1e-9;

    private static void reportResult(String name, boolean success) {
        System.out.println((success ? "SUCCESS: " : "FAILURE: ") + name);
        if (success) {
            passed++;
        } else {
            failed++;
        }
    }

    private static boolean approximately(double actual, double expected) {
        return Math.abs(actual - expected) < EPSILON;
    }

    private static boolean throwsIllegalArgument(Runnable action) {
        try {
            action.run();
            return false;
        } catch (IllegalArgumentException expected) {
            return true;
        }
    }

    private static void testOriginalSpecifications() {
        Volvo240 volvo = new Volvo240();
        Saab95 saab = new Saab95();
        reportResult("Volvo doors, power, color, model", volvo.getNrDoors() == 4
                && approximately(volvo.getEnginePower(), 100)
                && Color.BLACK.equals(volvo.getColor())
                && "Volvo240".equals(volvo.getModelName()));
        reportResult("Saab doors, power, color, model", saab.getNrDoors() == 2
                && approximately(saab.getEnginePower(), 125)
                && Color.RED.equals(saab.getColor())
                && "Saab95".equals(saab.getModelName()));
        reportResult("Constructors stop both engines", approximately(volvo.getCurrentSpeed(), 0)
                && approximately(saab.getCurrentSpeed(), 0));
    }

    private static void testStartStopAndColor() {
        Car car = new Volvo240();
        car.startEngine();
        reportResult("Starting sets speed to 0.1", approximately(car.getCurrentSpeed(), 0.1));
        car.stopEngine();
        reportResult("Stopping sets speed to 0", approximately(car.getCurrentSpeed(), 0));
        car.setColor(Color.BLUE);
        reportResult("Color can be updated", Color.BLUE.equals(car.getColor()));
    }

    private static void testTurbo() {
        Saab95 slow = new Saab95();
        Saab95 fast = new Saab95();
        fast.setTurboOn();
        slow.setTurboOff();
        fast.gas(0.5);
        slow.gas(0.5);
        reportResult("Turbo yields more speed", slow.getCurrentSpeed() < fast.getCurrentSpeed());
        reportResult("Saab speed without turbo: 0.625", approximately(slow.getCurrentSpeed(), 0.625));
        reportResult("Saab speed with turbo: 0.8125", approximately(fast.getCurrentSpeed(), 0.8125));
    }

    private static void testMovable() {
        Car car = new Volvo240();
        car.startEngine();
        car.gas(0.4);  // speed is now 0.1 + (1.25 * 0.4) = 0.6
        Movable movable = car;
        movable.move();
        reportResult("North movement increases y by current speed",
                approximately(car.getX(), 0) && approximately(car.getY(), 0.6));
        movable.turnRight();
        movable.move();
        reportResult("Right turn points east and increases x",
                car.getDirection() == Car.Direction.EAST
                        && approximately(car.getX(), 0.6)
                        && approximately(car.getY(), 0.6));
        movable.turnLeft();
        movable.move();
        reportResult("Left turn points north and increases y",
                car.getDirection() == Car.Direction.NORTH
                        && approximately(car.getX(), 0.6)
                        && approximately(car.getY(), 1.2));
        movable.turnLeft();
        movable.move();
        reportResult("West movement decreases x",
                car.getDirection() == Car.Direction.WEST
                        && approximately(car.getX(), 0)
                        && approximately(car.getY(), 1.2));
        car.stopEngine();
        movable.move();
        reportResult("Zero speed means no movement", approximately(car.getX(), 0)
                && approximately(car.getY(), 1.2));
    }

    private static void testSpeedLimits() {
        Car volvo = new Volvo240();
        volvo.gas(0.5);
        double before = volvo.getCurrentSpeed();
        volvo.gas(0.5);
        reportResult("Gas cannot decrease speed", volvo.getCurrentSpeed() >= before);
        before = volvo.getCurrentSpeed();
        volvo.brake(0.5);
        reportResult("Brake cannot increase speed", volvo.getCurrentSpeed() <= before);

        volvo.brake(1);
        reportResult("Brake cannot make speed negative", approximately(volvo.getCurrentSpeed(), 0));
        for (int i = 0; i < 200; i++) {
            volvo.gas(1);
        }
        reportResult("Speed cannot exceed engine power", approximately(volvo.getCurrentSpeed(), 100));
        volvo.brake(0);
        reportResult("Braking by 0 leaves speed unchanged", approximately(volvo.getCurrentSpeed(), 100));

        Saab95 saab = new Saab95();
        saab.setTurboOn();
        for (int i = 0; i < 200; i++) {
            saab.gas(1);
        }
        reportResult("Saab with turbo still respects speed cap", approximately(saab.getCurrentSpeed(), 125));
    }

    private static void testInvalidAmounts() {
        Car car = new Volvo240();
        reportResult("Gas rejects negative amount", throwsIllegalArgument(() -> car.gas(-0.1)));
        reportResult("Gas rejects amount over 1", throwsIllegalArgument(() -> car.gas(1.01)));
        reportResult("Brake rejects negative amount", throwsIllegalArgument(() -> car.brake(-0.1)));
        reportResult("Brake rejects amount over 1", throwsIllegalArgument(() -> car.brake(1.01)));
        reportResult("Gas rejects NaN", throwsIllegalArgument(() -> car.gas(Double.NaN)));
        reportResult("Brake rejects infinity", throwsIllegalArgument(() -> car.brake(Double.POSITIVE_INFINITY)));
        reportResult("Invalid amounts leave the speed unchanged", approximately(car.getCurrentSpeed(), 0));
    }

    public static void main(String[] args) {
        testOriginalSpecifications();
        testStartStopAndColor();
        testTurbo();
        testMovable();
        testSpeedLimits();
        testInvalidAmounts();
        System.out.println("\nResult: " + passed + " passed; " + failed + " failed.");
        if (failed > 0) {
            throw new AssertionError(failed + " test(s) failed");
        }
    }
}
