

public class BatteryManager {

    int temperatureAnalogValue;  // Temperature form 0-1023 = 0-5 V
    double temprature;           // Temperature in C'
    int batteryVoltAnalogValue;  // Battery volt form 0-1023 = 0-5 V
    double batteryVolt;          // Battery volt in V

    // Pins for the relay.
    final int relayAmplifier = 5;
    final int relayAmplifierArduino = 6;
    final int buttonMotorControler =13;
    final int buttonMmplifier = 19;

    public BatteryManager() {
        this.temperatureAnalogValue = 0;
        this.temprature = 0;
        this.batteryVoltAnalogValue = 0;
        this.batteryVolt = 0;
    }
}
