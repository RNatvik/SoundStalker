package com.SoudStalker;

import java.util.concurrent.TimeUnit;

public class SonicSensor {

    private int triggerPin;
    private int echoPin;

    public SonicSensor(GpioPinDigitalOutput triggerPin, GpioPinDigitalInput echoPin) {
        this.triggerPin = triggerPin;
        this.echoPin = echoPin;
    }

    public double getDistance(){
        triggerPin.setHigh();
        TimeUnit.MICROSECONDS.sleep(10);
        triggerPin.setLow();

        long duration = echoPin.pulseIn(high);
        double distance = duration * 0.034 / 2.0;
        return distance;
    }
}
