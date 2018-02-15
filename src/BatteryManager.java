import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class BatteryManager {

    int temperatureAnalogValue;  // Temperature form 0-1023 = 0-5 V
    double temprature;           // Temperature in C'
    int batteryVoltAnalogValue;  // Battery volt form 0-1023 = 0-5 V
    double batteryVolt;          // Battery volt in V

    final GpioController gpio = GpioFactory.getInstance();
    // Pins for the relay.
    final GpioPinDigitalOutput realy1 = this.gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, "Amplifier", PinState.HIGH);
    final GpioPinDigitalOutput realy2 = this.gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, "Bluetooth aluino", PinState.HIGH);
    final GpioPinDigitalOutput realy3 = this.gpio.provisionDigitalOutputPin(RaspiPin.GPIO_13, "Motor controller", PinState.HIGH);
    final GpioPinDigitalInput amplifierButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_19, PinPullResistance.PULL_UP);
    final GpioPinDigitalInput motorcontrollerButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_26, PinPullResistance.PULL_UP);





    public BatteryManager() {
        this.temperatureAnalogValue = 0;
        this.temprature = 0;
        this.batteryVoltAnalogValue = 0;
        this.batteryVolt = 0;

        realy1.setShutdownOptions(true, PinState.LOW);
        realy2.setShutdownOptions(true, PinState.LOW);
        realy3.setShutdownOptions(true, PinState.LOW);
        amplifierButton.setShutdownOptions(true);
        motorcontrollerButton.setShutdownOptions(true);

        amplifierButton.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {

            }
        });
    }

}
