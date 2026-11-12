import java.awt.Color;

public class Saab95 extends Car {
    private boolean turboOn;

    public Saab95() {
        super(2, 125, Color.RED, "Saab95");
        turboOn = false;
    }

    public void setTurboOn() {
        turboOn = true;
    }

    public void setTurboOff() {
        turboOn = false;
    }

    @Override
    protected double speedFactor() {
        return super.speedFactor() * (turboOn ? 1.3 : 1.0);
    }
    \\@Override
    protected double speedFactor() {
        double turboFactor = 1.0;

        if (turboOn) {
            turboFactor = 1.3;
        }

        return getEnginePower() * 0.01 * turboFactor;
    }
}
