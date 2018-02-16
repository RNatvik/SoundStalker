import com.Communications;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.RaspiPin;

public class MotorController {

    private int leftSensorStrength;
    private int rightSensorStrength;
    private int sensorRelativeStrength;
    private int leftMotorPWM;
    private int rightMotorPWM;

    private final GpioController GPIO;

    private final GpioPinPwmOutput MOTOR_L;
    private final GpioPinPwmOutput MOTOR_R;

    public MotorController() {

        this.leftSensorStrength = 0;
        this.leftMotorPWM = 0;
        this.rightSensorStrength = 0;
        this.rightMotorPWM = 0;

        this.GPIO = GpioFactory.getInstance();

        this.MOTOR_L = this.GPIO.provisionPwmOutputPin(RaspiPin.GPIO_23, "Left motor");
        this.MOTOR_R = this.GPIO.provisionPwmOutputPin(RaspiPin.GPIO_24, "Right motor");

        this.MOTOR_L.setPwmRange(1023);  //Sets the PWM range
        this.MOTOR_R.setPwmRange(1023);  //Sets the PWM range
        this.MOTOR_L.setPwm(this.rightMotorPWM);          //Sets initial PWM output to 0
        this.MOTOR_R.setPwm(this.rightMotorPWM);          //Sets initial PWM output to 0

    }

    public boolean doYourThing() {
        boolean successful = false;
        this.update();
        int newRelativeStrength = getNewRelativeStrength();
        if (newRelativeStrength - this.sensorRelativeStrength > 5 ||  newRelativeStrength - this.sensorRelativeStrength < -5) {
            this.sensorRelativeStrength = newRelativeStrength;
            calculateNewForwardPWM();
            calculateNewTunrPWM();

        } else {
            System.out.println("Change in direction not large enough to change direction");
            successful = true;
        }
        return successful;
    }

    private boolean calculateNewTunrPWM() {

    }
    /*
    475 left
    890 right
    1023 / 500 = 2,04
    1023 / 600 = 1,71
     */

    private boolean calculateNewForwardPWM() {
        //if sensorRelativeStrength < 0, left motor must run faster than right.
        boolean successful = false;
        this.leftMotorPWM = (1024 / this.leftSensorStrength+1) -1;

        return successful;
    }

    private int getNewRelativeStrength() {
        return this.leftSensorStrength - this.rightSensorStrength;
    }

    private void update() {
        this.leftSensorStrength = Communications.getSensorValue("irSensorL");
        this.rightSensorStrength = Communications.getSensorValue("irSensorR");
    }
}
