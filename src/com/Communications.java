package com;

import com.fazecast.jSerialComm.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class Communications {

    private static SerialPort comPort;
    private static InputStream in;
    private static HashMap<String, Integer> sensorValues;

    public Communications() {
    }

    public static void setup() {
        comPort = SerialPort.getCommPorts()[0];
        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
        in = comPort.getInputStream();
        sensorValues = new HashMap<>();
    }

    public int getSensorValue(String sensor) {
        return sensorValues.get(sensor);
    }

    public static void printHashMap() {
        System.out.println(sensorValues);
    }

    public static void update() {
        char[] charArray = getInputStream();
        if (charArray[0] >= 1) {
            parseToInt(charArray);
        }
    }

    private static void parseToInt(char[] charArray) {
        try {
            String string = String.copyValueOf(charArray).trim();
            String[] stringPart = string.split("\r\n");
            sensorValues.put("irSensorR", Integer.parseInt(stringPart[0]));
            sensorValues.put("irSensorL", Integer.parseInt(stringPart[1]));
            sensorValues.put("batteryVoltage", Integer.parseInt(stringPart[2]));
            sensorValues.put("batteryTemperature", Integer.parseInt(stringPart[3]));
            sensorValues.put("Yolo1", Integer.parseInt(stringPart[4]));
            sensorValues.put("Yolo2", Integer.parseInt(stringPart[5]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static char[] getInputStream() {
        char[] charArray = new char[50];
        try {
            while (in.available() > 0) {
                char tempChar = (char) in.read();
                if (tempChar == 65) {
                    int i = 0;
                    while (tempChar != 66) {
                        tempChar = (char) in.read();
                        if (tempChar != 66) {
                            charArray[i] = tempChar;
                            i++;
                        }
                    }
                    in.close();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charArray;
    }
}


/**
 * try
 * <p>
 * {
 * <p>
 * if (in.available() > 0) {
 * <p>
 * } else {
 * <p>
 * }
 * <p>
 * while (true) {
 * long startTime = System.currentTimeMillis();
 * // System.out.println(in.read());
 * <p>
 * int tempInt = in.read();
 * char[] charArray = new char[4];
 * if (tempInt == 10) {
 * int i = 0;
 * while (tempInt != 13) {
 * int tempInt2 = in.read();
 * if (tempInt2 == 13) {
 * tempInt = tempInt2;
 * } else {
 * charArray[i] = (char) tempInt2;
 * tempInt = tempInt2;
 * i++;
 * }
 * }
 * String string = String.copyValueOf(charArray).trim();
 * int yolo = Integer.parseInt(string);
 * System.out.println("Yolo: " + yolo);
 * System.out.println();
 * }
 * in.close();
 * <p>
 * long endTime = System.currentTimeMillis();
 * System.out.println(endTime - startTime + " to finish ");
 * }
 * } catch(
 * Exception e)
 * <p>
 * {
 * e.printStackTrace();
 * }
 * comPort.closePort();
 * <p>
 * <p>
 * }
 */