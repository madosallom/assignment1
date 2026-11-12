import java.awt.Color;
public class CarTest {
    private static int passed = 0;
    private static int failed = 0;

    private static void check(String name, boolean result) {
        if (result) {
            System.out.println("PASS: " + name);
            passed = passed + 1;
        } else {
            System.out.println("FAIL: " + name);
            failed = failed + 1;
        }
    }

    // Task 1: Check that the cars keep their basic properties.
    private static void testBasics() {
        Car volvo = new Volvo240();
        Saab95 saab = new Saab95();

        check("Volvo has four doors", volvo.getNrDoors() == 4);
        check("Saab has two doors", saab.getNrDoors() == 2);
        check("Volvo engine power is 100", volvo.getEnginePower() == 100);
        check("Saab engine power is 125", saab.getEnginePower() == 125);
        check("New car has zero speed", volvo.getCurrentSpeed() == 0);

        volvo.startEngine();
        check("Start engine sets speed to 0.1", volvo.getCurrentSpeed() == 0.1);
        volvo.stopEngine();
        check("Stop engine sets speed to zero", volvo.getCurrentSpeed() == 0);

        volvo.setColor(Color.BLUE);
        check("Car color can change", volvo.getColor().equals(Color.BLUE));
    }

    // Task 1: Check that turbo makes a Saab accelerate faster.
    private static void testTurbo() {
        Saab95 normal = new Saab95();
        Saab95 turbo = new Saab95();
        normal.setTurboOff();
        turbo.setTurboOn();

        normal.gas(0.5);
        turbo.gas(0.5);
        check("Turbo gives more speed", turbo.getCurrentSpeed() > normal.getCurrentSpeed());
    }

    // Task 2: Check moving in all four directions.
    private static void testMovement() {
        Car car = new Volvo240();
        Movable movingCar = car;
        car.startEngine(); // Speed is 0.1.

        movingCar.move(); // Start facing north.
        check("Moving north increases Y", car.getX() == 0 && car.getY() == 0.1);

        movingCar.turnRight(); // Now facing east.
        check("Right turn faces east", car.getDirection() == Car.Direction.EAST);
        movingCar.move();
        check("Moving east increases X", car.getX() == 0.1 && car.getY() == 0.1);

        movingCar.turnLeft(); // East to north.
        check("Left turn faces north", car.getDirection() == Car.Direction.NORTH);
        movingCar.turnLeft(); // North to west.
        movingCar.move();
        check("Moving west decreases X", car.getX() == 0);

        movingCar.turnLeft(); // West to south.
        movingCar.move();
        check("Moving south decreases Y", car.getY() == 0);

        car.stopEngine();
        movingCar.move();
        check("Zero speed means no movement", car.getX() == 0 && car.getY() == 0);
    }

    // Task 4: Check the rules for gas, brake, and speed limits.
    private static void testSpeedRules() {
        Car car = new Volvo240();

        car.gas(0.5);
        double previousSpeed = car.getCurrentSpeed();
        car.gas(0.5);
        check("Gas cannot decrease speed", car.getCurrentSpeed() >= previousSpeed);

        previousSpeed = car.getCurrentSpeed();
        car.brake(0.5);
        check("Brake cannot increase speed", car.getCurrentSpeed() <= previousSpeed);

        car.brake(1);
        check("Brake stops at zero", car.getCurrentSpeed() == 0);

        for (int i = 0; i < 200; i = i + 1) {
            car.gas(1);
        }
        check("Volvo stops at its maximum speed", car.getCurrentSpeed() == car.getEnginePower());

        previousSpeed = car.getCurrentSpeed();
        car.gas(0);
        check("Gas with zero does not change speed", car.getCurrentSpeed() == previousSpeed);
        car.brake(0);
        check("Brake with zero does not change speed", car.getCurrentSpeed() == previousSpeed);

        Saab95 saab = new Saab95();
        saab.setTurboOn();
        for (int i = 0; i < 200; i = i + 1) {
            saab.gas(1);
        }
        check("Saab stops at its maximum speed", saab.getCurrentSpeed() == saab.getEnginePower());
    }

    // Task 4: Values outside [0, 1] should be rejected.
    private static void testInvalidInputs() {
        Car car = new Volvo240();
        boolean rejected = false;

        try {
            car.gas(-0.1);
        } catch (IllegalArgumentException e) {
            rejected = true;
        }
        check("Gas rejects negative input", rejected);

        rejected = false;
        try {
            car.gas(1.1);
        } catch (IllegalArgumentException e) {
            rejected = true;
        }
        check("Gas rejects input above one", rejected);

        rejected = false;
        try {
            car.brake(-0.1);
        } catch (IllegalArgumentException e) {
            rejected = true;
        }
        check("Brake rejects negative input", rejected);

        rejected = false;
        try {
            car.brake(1.1);
        } catch (IllegalArgumentException e) {
            rejected = true;
        }
        check("Brake rejects input above one", rejected);
        check("Rejected inputs do not change speed", car.getCurrentSpeed() == 0);
    }
    public static void main(String[] args) {
        testBasics();
        testTurbo();
        testMovement();
        testSpeedRules();
        testInvalidInputs();

        System.out.println("\nFinal result: " + passed + " passed, " + failed + " failed.");
    }
}
