package Sensors;

import com.Communications;

public class IRsensor extends Sensors {

    Communications communications;

    public IRsensor(boolean ic2, boolean serial, boolean spi, boolean uart, int communicationIndex) {
        super(ic2, serial, spi, uart, communicationIndex);
    }

    public IRsensor(boolean ic2) {
        super(ic2);
    }

    public int getAnalogValue () {
        return Communications.getDataByIndex(this.communicationIndex);
    }
}
