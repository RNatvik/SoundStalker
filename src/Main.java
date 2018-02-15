import Sensors.IRsensor;
import com.Communications;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InterruptedException {


        Communications.setup();
        int i = 0;
        Thread.sleep(2000);

        while (true) {
            Communications.update();
            Communications.printHashMap();
            System.out.println();
            i++;
        }

    }
}
