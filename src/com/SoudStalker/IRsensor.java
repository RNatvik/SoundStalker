package com.SoudStalker;

public class IRsensor extends Sensors {

    private double currentValue;

    public IRsensor(int communicationIndex, boolean i2c, boolean serial, boolean spi) {
        super(/*0, null,*/ communicationIndex, i2c, serial, spi);
        this.currentValue = 0;
    }



}
