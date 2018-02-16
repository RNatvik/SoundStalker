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
    private double targetStrength;

    private MiniPID sensorPID;
    private MiniPID relativePID;

    private final GpioController GPIO;

    private final GpioPinPwmOutput MOTOR_L;
    private final GpioPinPwmOutput MOTOR_R;

    public MotorController(double sensorP, double sensorI, double sensorD,
                           double relativeP, double relativeI, double relativeD,
                           double targetStrength) {

        this.leftSensorStrength = 0;
        this.leftMotorPWM = 0;
        this.rightSensorStrength = 0;
        this.rightMotorPWM = 0;
        this.targetStrength = targetStrength;

        this.sensorPID = new MiniPID(sensorP,sensorI, sensorD);
        this.sensorPID.setOutputLimits(0, 1023);
        this.relativePID = new MiniPID(relativeP, relativeI, relativeD);
        this.relativePID.setOutputLimits(-1023, 1023);

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
            int newForwardPWM = calculateNewForwardPWM();
            int newTurnPWM = calculateNewTurnPWM();
            this.leftMotorPWM = newForwardPWM - newTurnPWM/2;
            this.rightMotorPWM = newForwardPWM + newTurnPWM/2;

            updateMotorSpeed();

        } else {
            System.out.println("Change in direction not large enough to change direction");
            successful = true;
        }
        return successful;
    }

    private boolean updateMotorSpeed() {
        boolean successful = false;
        try {
            MOTOR_L.setPwm(this.leftMotorPWM);
            MOTOR_R.setPwm(this.rightMotorPWM);
            successful = true;
        } catch (Exception e) {
            successful = false;
            e.printStackTrace();
        }
        return successful;
    }

    private int calculateNewTurnPWM() {
        //if sensorRelativeStrength < 0, left motor must run faster than right.
        return (int) relativePID.getOutput(this.sensorRelativeStrength, 0);
    }

    private int calculateNewForwardPWM() {
        int meanSensorValue = (this.leftSensorStrength + this.rightSensorStrength) / 2;
        int forwardPWM = (int) sensorPID.getOutput(meanSensorValue, this.targetStrength);
        return forwardPWM;
    }

    private int getNewRelativeStrength() {
        return this.leftSensorStrength - this.rightSensorStrength;
    }

    private void update() {
        this.leftSensorStrength = Communications.getSensorValue("irSensorL");
        this.rightSensorStrength = Communications.getSensorValue("irSensorR");
    }
}
