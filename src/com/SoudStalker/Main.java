package com.SoudStalker;

public class Main {

    public static void main(String[] args) {



        //Setup
        Sensors.startCom();
        IRsensor irSensor1 = new IRsensor(0,false,true,false);
        IRsensor irSensor2 = new IRsensor(1,false,true,false);
        SonicSensor sonicSensor = new SonicSensor(1,2);
        Sensors.updateComArray();



    }
}
