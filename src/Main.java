import Sensors.IRsensor;
import com.Communications;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        BatteryManager batteryManager = new BatteryManager();
        Communications.setup();
        Communications communications = new Communications();
        int i = 0;
        Thread.sleep(2000);

        while (true) {
            boolean dataUpdated = Communications.update();
            Communications.printHashMap();
            System.out.println();
            i++;

            if (dataUpdated) {
                batteryManager.checkConcision();
            }
        }

    }
}
