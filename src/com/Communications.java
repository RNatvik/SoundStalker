package com;

import com.fazecast.jSerialComm.*;
import java.io.InputStream;
import java.util.HashMap;

/**
 * A class for handling communication between a two units connected by USB.
 */
public class Communications {

    private static SerialPort comPort;
    private static InputStream in;
    private static HashMap<String, Integer> sensorValues;


    /**
     * Method for setting up the whole Communication class.
     * This method is required to run ONCE before using any of the Class' other methods.
     * @param comPortIndex the index the wanted comPort is located at in "SerialPort.getCommPorts()"'s returned array.
     */
    public static void setup(int comPortIndex) {
        comPort = SerialPort.getCommPorts()[comPortIndex];
        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
        in = comPort.getInputStream();
        sensorValues = new HashMap<>();
    }

    /**
     * Method for getting the value associated with a spesific sensor.
     * @param sensor HashMap key as String.
     * @return the HashMap's associated value with given parameter key.
     */
    public static int getSensorValue(String sensor) {
        return sensorValues.get(sensor);
    }

    /**
     * Method for printing values located in HashMap sensorValues to System.out
     * Used for troubleshooting.
     */
    public static void printHashMap() {
        System.out.println(sensorValues);
    }

    /**
     * Method for updating HashMap values
     * @return true if update successful, false otherwise.
     */
    public static boolean update() {
        boolean dataUpdated = false;
        char[] charArray = getInputStream();
        if (charArray[0] > 0) {
            parseToInt(charArray);
            dataUpdated = true;
        }
        return dataUpdated;
    }

    /**
     * Takes a charArray and splits it into component integer values
     * then maps them into HashMap sensorValues at predetermined keys.
     * @param charArray a charArray with 6 separate values as char, separated with "\r\n"
     */
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

    /**
     * Method for getting new input from an external unit as a char[]
     * Input stream must be started with an 'A', and ended with a 'B'
     * @return
     */
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