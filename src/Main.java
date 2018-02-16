import com.Communications;

import javax.swing.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {

       // BatteryManager batteryManager = new BatteryManager();
        Communications.setup(0);
        MotorController motorController = new MotorController(0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 500);
        Thread.sleep(2000); //mulig unødvendig tipp topp tommel opp.
        int i = 0;

        Communications.setup(0);
        Thread.sleep(2000);


        JFrame window = new JFrame();
        JSlider slider = new JSlider();
        slider.setMaximum(1023);
        window.add(slider);
        window.pack();
        window.setVisible(true);

        while (true) {

            boolean dataUpdated = Communications.update();
            Communications.printHashMap();
            System.out.println();
            i++;

            if (dataUpdated) {
               // batteryManager.checkCondition();
                motorController.doYourThing();
            }

            /*Communications.update();
            slider.setValue(Communications.getSensorValue("irSensorR"));
            Communications.printHashMap();*/


        }




    }
}
