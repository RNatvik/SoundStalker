package com.SoudStalker;


public class Sensors {

    /*private int numberOfPins;
    private int[] pins;*/
    private boolean i2c;
    private boolean serial;
    private boolean spi;
    private int communicationIndex;

    static Communications communicationsArray;

    public Sensors(/*int numberOfPins, int[] pins,*/ int communicationIndex, boolean i2c, boolean serial, boolean spi) {
        /*this.numberOfPins = numberOfPins;
        this.pins = pins;*/
        this.communicationIndex = communicationIndex;
        this.i2c = i2c;
        this.serial = serial;
        this.spi = spi;
    }

    static void startCom() {
        communicationsArray = new Communications();
    }

    static void updateComArray() {
        communicationsArray.getSerialData();
    }

    public double getSensorValue() {
        return communicationsArray.getDataByIndex(this.communicationIndex);
    }


}
