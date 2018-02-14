package com.SoudStalker;

public class TempSensor extends Sensors{

    private int tempLimit;
    private double currentTemp;

    public TempSensor(/*int numberOfPins, int[] pins, */int communicationIndex, boolean i2c, boolean serial, boolean spi, int tempLimit) {
        super(/*0, null,*/ communicationIndex, i2c, serial, spi);
        this.tempLimit = tempLimit;
        this.currentTemp = 0;
    }

    public double getTempInC() {
        double voltage = getSensorValue() * 0.004882814d;
        return (voltage - 0.5) * 100;
    }
}
