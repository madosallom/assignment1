import java.awt.*;

public class Saab95 extends Car{
    
    public Saab95() {
        nrDoors = 2;
        color = Color.red;
        enginePower = 125;
        turboOn = false;
        modelName = "Saab95";
        stopEngine();
        x = 0;
        y = 0;
        xy_direction_counter = 0;
    }
        
    @Override 
    public double speedFactor() {
        double turbo = 1;
        if(turboOn) turbo = 1.3;
        return enginePower * 0.01 * turbo;
    }

}