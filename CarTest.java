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

    private static void testGas(double test){

        Saab95 saab = new Saab95();
        saab.gas(test);
        
    }

    private static void testCurrentSpeed(){
        boolean result = true;
        Saab95 saab = new Saab95();
        Volvo240 volvo = new Volvo240();
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

    static void main() {
        testTurbo();
        //testGas(-1);
        testCurrentSpeed();
        //TODO: add and run more tests
    }
}
