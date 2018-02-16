import com.Communications;

public class Main {

    public static void main(String[] args) throws InterruptedException {

       BatteryManager batteryManager = new BatteryManager();
        Communications.setup(0);
        Communications communications = new Communications();
        MotorController motorController = new MotorController(0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 500);
        Thread.sleep(2000); //mulig unødvendig tipp topp tommel opp.
        int i = 0;

        while (true) {
            boolean dataUpdated = Communications.update();
            Communications.printHashMap();
            System.out.println();
            i++;

            if (dataUpdated) {
                batteryManager.checkCondition();
                motorController.doYourThing();
            }
        }


    }
}
