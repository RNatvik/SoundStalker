import com.Communications;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/**
 * This class controll all power management on the Sound stalker.
 * its complete with battery protective feathers fore battery overheat end generel batery protection.
 */
public class BatteryManager {

    private double batteryTemperature;           // Temperature in C'
    private double batteryVolt;          // Battery volt in V

    private double temperatureThreshold;
    private double batteryDepletedThreshold;
    private double batteryFullThreshold;

    private int amplifierState;
    private int motorControllerState;

    private final GpioController gpio;
    // Pins for the relay.
    private final GpioPinDigitalOutput realy1;
    private final GpioPinDigitalOutput realy2;
    private final GpioPinDigitalOutput realy3;
    private final GpioPinDigitalOutput realy4;
    private final GpioPinDigitalInput amplifierButton;
    private final GpioPinDigitalInput motorControllerButton;

    /**
     * Constructs the object.
     * Initialises the GpioController and all the needed gpio pins.
      */
    public BatteryManager() {
        this.batteryTemperature = 0;
        this.batteryVolt = 0;
        this.amplifierState = 0;
        this.motorControllerState = 0;

        this.temperatureThreshold = 30;
        this.batteryDepletedThreshold = 3;
        this.batteryFullThreshold = 4.2;

        this.gpio = GpioFactory.getInstance();

        this.realy1 = this.gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, "Amplifier", PinState.HIGH);
        this.realy2 = this.gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, "Bluetooth aluino", PinState.HIGH);
        this.realy3 = this.gpio.provisionDigitalOutputPin(RaspiPin.GPIO_13, "Motor controller", PinState.HIGH);
        this.realy4 = this.gpio.provisionDigitalOutputPin(RaspiPin.GPIO_19, "Charge", PinState.HIGH);
        this.realy1.setShutdownOptions(true, PinState.LOW);
        this.realy2.setShutdownOptions(true, PinState.LOW);
        this.realy3.setShutdownOptions(true, PinState.LOW);
        this.realy4.setShutdownOptions(true, PinState.LOW);

        this.amplifierButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_17, PinPullResistance.PULL_UP);
        this.motorControllerButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_27, PinPullResistance.PULL_UP);
        this.amplifierButton.setShutdownOptions(true);
        this.motorControllerButton.setShutdownOptions(true);

        this.amplifierButton.setDebounce(100,PinState.LOW);
        this.amplifierButton.addListener( (GpioPinListenerDigital) event -> this.amplifierState = event.getEdge().getValue());
        this.motorControllerButton.setDebounce(100,PinState.LOW);
        this.motorControllerButton.addListener( (GpioPinListenerDigital) event -> this.motorControllerState = event.getEdge().getValue());

    }

    /**
     * Evaluates all nesesary data reacevd form the Communication class
     * and tacks necessary action to protect the battery.
     * Has control over the Sound stalkers button fore turning on/off parts off the device.
     */
    public void checkConcision() {
        this.update();

        if (this.batteryTemperature > this.temperatureThreshold) {
            // Shuts down the power if the battery overheats.
            this.realy1.low();
            this.realy2.low();
            this.realy3.low();
            this.realy4.low();

        } else {
            if (this.batteryVolt <= this.batteryDepletedThreshold) { // Battery is depleted.
                // Shuts down everything except charging.
                this.realy1.low();
                this.realy2.low();
                this.realy3.low();

                }else {
                // Turns on and off the amplifier/motor controller if the buttons are pressed
                    if (this.amplifierState == 1) {
                        this.realy1.high();
                        this.realy2.high();

                    }else {
                        this.realy1.low();
                        this.realy2.low();
                    }
                    if (this.motorControllerState == 1) {
                        this.realy3.high();

                    }else {
                        this.realy3.low();
                }
            }
            if (this.batteryVolt >= this.batteryFullThreshold) {
                this.realy4.low();  // Turns off charging wen the battery is full.

            }else {
                this.realy4.high();
            }
        }

    }

    /**
     * Gets the needed analog values from Communications class and converts them to metric units.
     */
    private void update() {
        this.batteryVolt = Communications.getSensorValue("batteryVoltage") * 0.0048875;
        this.batteryTemperature = Communications.getSensorValue("batteryTemperature") / 5.39;
    }

}
