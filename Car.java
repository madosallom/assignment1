import java.awt.Color;
public abstract class Car implements Movable {

    public enum Direction {
        NORTH, EAST, SOUTH, WEST
    }

    private final int nrDoors;
    private final double enginePower;
    private final String modelName;
    private Color color;
    private double currentSpeed;

    private double x;
    private double y;
    private Direction direction;

    protected Car(int nrDoors, double enginePower, Color color, String modelName) {
        if (nrDoors <= 0 || !Double.isFinite(enginePower) || enginePower <= 0) {
            throw new IllegalArgumentException("Invalid car specifications");
        }
        this.nrDoors = nrDoors;
        this.enginePower = enginePower;
        this.color = color;
        this.modelName = modelName;
        this.x = 0;
        this.y = 0;
        this.direction = Direction.NORTH;
        stopEngine();
    }

    public int getNrDoors() {
        return nrDoors;
    }

    public double getEnginePower() {
        return enginePower;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getModelName() {
        return modelName;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void startEngine() {
        currentSpeed = Math.min(0.1, enginePower);
    }

    public void stopEngine() {
        currentSpeed = 0;
    }

    protected double speedFactor() {
        return enginePower * 0.01;
    }

    private double checkedSpeedChange(double amount) {
        if (!(amount >= 0 && amount <= 1)) {
            throw new IllegalArgumentException("Amount must be between 0 and 1");
        }
        double factor = speedFactor();
        if (!Double.isFinite(factor) || factor < 0) {
            throw new IllegalStateException("Speed factor must be finite and nonnegative");
        }
        return factor * amount;
    }

    public void gas(double amount) {
        double change = checkedSpeedChange(amount);
        currentSpeed = Math.min(enginePower, currentSpeed + change);
    }

    public void brake(double amount) {
        double change = checkedSpeedChange(amount);
        currentSpeed = Math.max(0, currentSpeed - change);
    }

    @Override
    public void move() {
        switch (direction) {
            case NORTH -> y += currentSpeed;
            case EAST  -> x += currentSpeed;
            case SOUTH -> y -= currentSpeed;
            case WEST  -> x -= currentSpeed;
        }
    }

    @Override
    public void turnLeft() {
        switch (direction) {
            case NORTH -> direction = Direction.WEST;
            case WEST  -> direction = Direction.SOUTH;
            case SOUTH -> direction = Direction.EAST;
            case EAST  -> direction = Direction.NORTH;
        }
    }

    @Override
    public void turnRight() {
        switch (direction) {
            case NORTH -> direction = Direction.EAST;
            case EAST  -> direction = Direction.SOUTH;
            case SOUTH -> direction = Direction.WEST;
            case WEST  -> direction = Direction.NORTH;
        }
    }
}
