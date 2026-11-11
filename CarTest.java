public class CarTest {
    private static void reportResult(String testName, boolean success) {
        IO.println((success ? "SUCCESS: " : "FAILURE: ") + testName);
    }

    /** Tests whether a Saab95 with turbo enabled
     *  gains more speed than one without.
     */
    private static void testTurbo() {
        Saab95 slowSaab = new Saab95();
        Saab95 fastSaab = new Saab95();
        fastSaab.setTurboOn();
        slowSaab.setTurboOff();
        fastSaab.gas(0.5);
        slowSaab.gas(0.5);
        boolean result = slowSaab.getCurrentSpeed() < fastSaab.getCurrentSpeed();
        reportResult("Turbo yields more speed", result);
    }


    /* TODO
    gas() and brake() only accepts values in the interval [0,1],
    currentSpeed is always in the interval [0, enginePower],
    Calls to gas() cannot decrease speed, and
    calls to brake() cannot increase speed.
    */

    private static void testGasInterval(double test){
        Car saab = new Saab95();
        double currentSpeed = saab.getCurrentSpeed();        
        saab.gas(test);
        boolean result = currentSpeed == saab.getCurrentSpeed();
        reportResult("Gas() only accepts values in the range 0 to 1", result);
    }

    private static void testBrakeInterval(double test){
        Car volvo = new Volvo240();
        double currentSpeed = volvo.getCurrentSpeed();
        volvo.brake(test);
        boolean result = currentSpeed == volvo.getCurrentSpeed();
        reportResult("Brake() only accepts values in the range 0 to 1", result);
    }

    private static void testCurrentSpeed(){
        boolean result = true;
        Car saab = new Saab95();
        Car volvo = new Volvo240();
        for(double i = 0; i <= 1; i += 0.01){
            saab.gas(i);
            volvo.gas(i);
            if(saab.currentSpeed > saab.enginePower || volvo.currentSpeed > volvo.enginePower){
                result = false;
            }
            if(saab.currentSpeed < 0 || volvo.currentSpeed < 0){
                result = false;
            }
        } 
        reportResult("Curent speed is less than enginpower and more than 0", result);

    }

    private static boolean testGasSmall(double test){
        Car saab = new Saab95();
        double speed = saab.getCurrentSpeed();
        saab.gas(test);
        boolean result = saab.getCurrentSpeed() > speed;
        return result;
    }

    private static void testGas(){
        boolean result = true;
        for(double i = 0; i <=1; i+= 0.01){
            result = testGasSmall(i);            
        }
        reportResult("Gas() doesn't slow down the car", result);
    }

    private static boolean testBrakeSmall(double test){
        Car volvo = new Volvo240();
        double speed = volvo.getCurrentSpeed();
        volvo.brake(test);
        boolean result = volvo.getCurrentSpeed() < speed || volvo.currentSpeed == 0;
        return result;
    }

    private static void testBrake(){
        boolean result = true;
        for(double i = 0; i <=1; i+= 0.01){
            result = testBrakeSmall(i);            
        }
        reportResult("Brake doesn't slow down the car", result);
    }

    static void main() {
        testTurbo();
        testGasInterval(-1);
        testGasInterval(5);
        testBrakeInterval(-3);
        testBrakeInterval(4);
        testGas();
        testBrake();
        testCurrentSpeed();
        //TODO: add and run more tests
    }
}
