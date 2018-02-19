
import javax.swing.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("hello");

        //BatteryManager batteryManager = new BatteryManager();
        Communications.setup(0);
        System.out.println("setup complet");
        //MotorController motorController = new MotorController(0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 500);
        System.out.println("motorcontroller setup done");
        Thread.sleep(2000); //mulig unødvendig tipp topp tommel opp.
        System.out.println("sleep over");


        JFrame window = new JFrame();
        JSlider slider = new JSlider();
        slider.setMaximum(1023);
        window.add(slider);
        window.pack();
        window.setVisible(true);

        System.out.println("starting loop");
        while (true) {

            boolean dataUpdated = Communications.update();



            if (dataUpdated) {
                //batteryManager.checkCondition();
                //motorController.doYourThing();
                Communications.printHashMap();
                slider.setValue(Communications.getSensorValue("irSensorR"));
                System.out.println();
                System.out.println("data updated");
            }




        }




    }
}
