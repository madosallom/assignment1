import java.awt.*;

public class Car implements Movable {

    public int nrDoors; // Number of doors on the car
    public double enginePower; // Engine power of the car        
    public double currentSpeed; // The current speed of the car
    public Color color; // Color of the car
    public String modelName; // The car model name
    public boolean turboOn;
    public double x;
    public double y;
    public double[][] xy_direction_list = {{0,1}, {1,0}, {0,-1}, {-1,0}}; //the turning direction where 0,1 is up; 1,0 is right; 0,-1 is down; -1,0 is left;
    public int xy_direction_counter; 

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

    public void setColor(Color clr) {
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

    public double speedFactor(){
        return 0;
    }
    
    public void incrementSpeed(double amount) {
        currentSpeed = getCurrentSpeed() + speedFactor() * amount;
    }

    public void decrementSpeed(double amount) {
        currentSpeed = getCurrentSpeed() - speedFactor() * amount;
    }
    
    // TODO fix this method according to assignment pm
    public void gas(double amount) {
        incrementSpeed(amount);
    }

    // TODO fix this method according to assignment pm
    public void brake(double amount) {
        decrementSpeed(amount);
    }

    public void move(){
        x = currentSpeed * xy_direction_list[xy_direction_counter%4][0];
        y = currentSpeed * xy_direction_list[xy_direction_counter%4][1];
    }

    public void turnLeft(){
        xy_direction_counter--;
    }

    public void turnRight(){
        xy_direction_counter++;
    }
}
