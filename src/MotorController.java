import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.RaspiPin;

public class MotorController {

    private int leftSensorValue;
    private int rightSensorValue;
    private int leftMotorPWM;
    private int rightMotorPWM;

    private final GpioController GPIO;

    private final GpioPinPwmOutput MOTOR_L;
    private final GpioPinPwmOutput MOTOR_R;

    public MotorController() {


        this.GPIO = GpioFactory.getInstance();

        MOTOR_L = this.GPIO.provisionPwmOutputPin(RaspiPin.GPIO_23, "Left motor");
        MOTOR_R = this.GPIO.provisionPwmOutputPin(RaspiPin.GPIO_24, "Right motor");

        MOTOR_L.setPwm(1023);   // Sets the PWM frequency
        MOTOR_R.setPwm(1023);   // Sets the PWM frequency
        MOTOR_L.setPwmRange(0); // Sets the output signal to 0 V
        MOTOR_R.setPwmRange(0); // Sets the output signal to 0 V
    }
}
