import java.awt.Color;

public class Volvo240 extends Car {
    private static final double TRIM_FACTOR = 1.25;

    public Volvo240() {
        super(4, 100, Color.BLACK, "Volvo240");
    }

    @Override
    protected double speedFactor() {
        return super.speedFactor() * TRIM_FACTOR;
    }
}
