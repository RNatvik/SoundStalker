package com.SoudStalker;


public class Sensors {

    boolean ic2;
    boolean serial;
    boolean spi;
    boolean uart;
    int communicationIndex;

    public Sensors(boolean ic2, boolean serial, boolean spi, boolean uart, int communicationIndex) {
        this.ic2 = ic2;
        this.serial = serial;
        this.spi = spi;
        this.uart = uart;
        this.communicationIndex = communicationIndex;
    }

    public boolean isSerial() {
        return serial;
    }
}
