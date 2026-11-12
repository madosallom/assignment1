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
   // Start and stop the engine.
    /*public void startEngine() {
        currentSpeed = 0.1;
    }

    public void stopEngine() {
        currentSpeed = 0;
    }

    // Volvo and Saab can change this calculation.
    protected double speedFactor() {
        return enginePower * 0.01;
    }

    // Check the pedal input and calculate how much speed changes.
    private double speedChange(double amount) {
        if (amount >= 0 && amount <= 1) {
            return amount * speedFactor();
        }
        throw new IllegalArgumentException("Amount must be between 0 and 1");
    }

    // Speed goes up, but must not pass enginePower.
    public void gas(double amount) {
        double change = speedChange(amount);
        currentSpeed = currentSpeed + change;

        if (currentSpeed > enginePower) {
            currentSpeed = enginePower;
        }
    }

    // Speed goes down, but must not go below zero.
    public void brake(double amount) {
        double change = speedChange(amount);
        currentSpeed = currentSpeed - change;

        if (currentSpeed < 0) {
            currentSpeed = 0;
        }
    }

    // Moving changes only x or y, depending on direction.
    @Override
    public void move() {
        if (direction == Direction.NORTH) {
            y = y + currentSpeed;
        } else if (direction == Direction.EAST) {
            x = x + currentSpeed;
        } else if (direction == Direction.SOUTH) {
            y = y - currentSpeed;
        } else if (direction == Direction.WEST) {
            x = x - currentSpeed;
        }
    }

    // Turning only changes direction, not the position.
    @Override
    public void turnRight() {
        if (direction == Direction.NORTH) {
            direction = Direction.EAST;
        } else if (direction == Direction.EAST) {
            direction = Direction.SOUTH;
        } else if (direction == Direction.SOUTH) {
            direction = Direction.WEST;
        } else if (direction == Direction.WEST) {
            direction = Direction.NORTH;
        }
    }

    @Override
    public void turnLeft() {
        if (direction == Direction.NORTH) {
            direction = Direction.WEST;
        } else if (direction == Direction.WEST) {
            direction = Direction.SOUTH;
        } else if (direction == Direction.SOUTH) {
            direction = Direction.EAST;
        } else if (direction == Direction.EAST) {
            direction = Direction.NORTH;
        }
    }*/
}

