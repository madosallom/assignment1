import java.awt.*;

public abstract class Car implements Movable {

    protected int nrDoors; // Number of doors on the car
    protected double enginePower; // Engine power of the car        
    protected double currentSpeed; // The current speed of the car
    private Color color; // Color of the car
    protected String modelName; // The car model name
    protected boolean turboOn;
    protected double x;
    protected double y;
    protected double[][] xy_direction_list = {{0,1}, {1,0}, {0,-1}, {-1,0}}; //the turning direction where 0,1 is up; 1,0 is right; 0,-1 is down; -1,0 is left;
    protected int xy_direction_counter; 

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

    protected void setColor(Color clr) {
        color = clr;
    }

    public void startEngine() {
        currentSpeed = 0.1;
    }

    public void stopEngine() {
        currentSpeed = 0;
    }

    public void setTurboOn() {
        turboOn = true;
    }

    public void setTurboOff() {
        turboOn = false;
    }

    protected abstract double speedFactor();
    
    private void incrementSpeed(double amount) {
        if (amount > 0){
        currentSpeed = getCurrentSpeed() + speedFactor() * amount;
        }
        if (currentSpeed > enginePower){currentSpeed = enginePower;}
    }

    private void decrementSpeed(double amount) {
        if (amount > 0){
        currentSpeed = getCurrentSpeed() - speedFactor() * amount;
        }
        if (currentSpeed < 0) {currentSpeed = 0;}
    }

    
    // TODO fix this method according to assignment pm
    public void gas(double amount) {
        if (0 <= amount && amount <= 1){      
        incrementSpeed(amount);
        }
    }

    // TODO fix this method according to assignment pm
    public void brake(double amount) {
        if (0 <= amount && amount <= 1){
        decrementSpeed(amount);
        }
    }

    public void move(){
        x += currentSpeed * xy_direction_list[xy_direction_counter%4][0];
        y += currentSpeed * xy_direction_list[xy_direction_counter%4][1];
    }

    public void turnLeft(){
        xy_direction_counter--;
    }

    public void turnRight(){
        xy_direction_counter++;
    }
}
