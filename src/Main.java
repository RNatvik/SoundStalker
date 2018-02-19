
import javax.swing.*;

public class Main {
    static boolean shutdown = false;

    public static void main(String[] args) throws InterruptedException {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            shutdown = true;
            System.out.println();
            System.out.println("Shutdown");
        }));

        System.out.println("hello");
        BatteryManager batteryManager = new BatteryManager();
        MotorController motorController = new MotorController(0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 500);
        System.out.println("motorcontroller setup done");

        try {




            Communications.setup(0);
            System.out.println("setup complet");
            Thread.sleep(2000); //mulig unødvendig tipp topp tommel opp.
            System.out.println("sleep over");


            JFrame window = new JFrame();
            JSlider slider = new JSlider();
            slider.setMaximum(1023);
            window.add(slider);
            window.pack();
            window.setVisible(true);

            System.out.println("starting loop");
            while (!shutdown) {

                boolean dataUpdated = Communications.update();


                if (dataUpdated) {
                    batteryManager.checkCondition();
                    motorController.doYourThing();
                    Communications.printHashMap();
                    slider.setValue(Communications.getSensorValue("irSensorR"));
                    System.out.println();
                    System.out.println("data updated");
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        batteryManager.shutdown();
        motorController.shutdown();
        System.out.println("Shutdown 2");

    }
}
