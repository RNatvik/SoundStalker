import com.Communications;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/**
 * This class controls all power management on the Sound stalker.
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

    private final GpioController GPIO;
    // Pins for the relay.
    private final GpioPinDigitalOutput RELAY1;
    private final GpioPinDigitalOutput RELAY2;
    private final GpioPinDigitalOutput RELAY3;
    private final GpioPinDigitalOutput RELAY4;
    private final GpioPinDigitalInput amplifierButton;
    private final GpioPinDigitalInput motorControllerButton;

    /**
     * Constructs the object.
     * Initialises the GpioController and all the needed GPIO pins.
      */
    public BatteryManager() {
        this.batteryTemperature = 0;
        this.batteryVolt = 0;
        this.amplifierState = 0;
        this.motorControllerState = 0;

        this.temperatureThreshold = 30;
        this.batteryDepletedThreshold = 3;
        this.batteryFullThreshold = 4.2;

        this.GPIO = GpioFactory.getInstance();
        this.GPIO.shutdown();
        this.GPIO.unprovisionPin();

        this.RELAY1 = this.GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_05, "Amplifier", PinState.HIGH);
        this.RELAY2 = this.GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_06, "Bluetooth aluino", PinState.HIGH);
        this.RELAY3 = this.GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_13, "Motor controller", PinState.HIGH);
        this.RELAY4 = this.GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_19, "Charge", PinState.HIGH);
        this.RELAY1.setShutdownOptions(true, PinState.LOW);
        this.RELAY2.setShutdownOptions(true, PinState.LOW);
        this.RELAY3.setShutdownOptions(true, PinState.LOW);
        this.RELAY4.setShutdownOptions(true, PinState.LOW);

        this.amplifierButton = GPIO.provisionDigitalInputPin(RaspiPin.GPIO_17, PinPullResistance.PULL_UP);
        this.motorControllerButton = GPIO.provisionDigitalInputPin(RaspiPin.GPIO_27, PinPullResistance.PULL_UP);
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
    public void checkCondition() {
        this.update();

        if (this.batteryTemperature > this.temperatureThreshold) {
            // Shuts down the power if the battery overheats.
            this.RELAY1.low();
            this.RELAY2.low();
            this.RELAY3.low();
            this.RELAY4.low();

        } else {
            if (this.batteryVolt <= this.batteryDepletedThreshold) { // Battery is depleted.
                // Shuts down everything except charging.
                this.RELAY1.low();
                this.RELAY2.low();
                this.RELAY3.low();

                }else {
                // Turns on and off the amplifier/motor controller if the buttons are pressed
                    if (this.amplifierState == 1) {
                        this.RELAY1.high();
                        this.RELAY2.high();

                    }else {
                        this.RELAY1.low();
                        this.RELAY2.low();
                    }
                    if (this.motorControllerState == 1) {
                        this.RELAY3.high();

                    }else {
                        this.RELAY3.low();
                }
            }
            if (this.batteryVolt >= this.batteryFullThreshold) {
                this.RELAY4.low();  // Turns off charging wen the battery is full.

            }else {
                this.RELAY4.high();
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
