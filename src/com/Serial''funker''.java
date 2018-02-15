
import com.fazecast.jSerialComm.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JSlider;

public class Main {

    public static void main(String[] args) {
        SerialPort comPort = SerialPort.getCommPorts()[0];
        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
        InputStream in = comPort.getInputStream();


        try
        {
            while(true) {
                long startTime = System.currentTimeMillis();
                // System.out.println(in.read());
                if (in.available() > 0) {
                    int tempInt = in.read();
                    char[] charArray = new char[4];
                    if (tempInt == 10) {
                        int i = 0;
                        while (tempInt != 13) {
                            int tempInt2 = in.read();
                            if (tempInt2 == 13) {
                                tempInt = tempInt2;
                            } else {
                                charArray[i] = (char) tempInt2;
                                tempInt = tempInt2;
                                i++;
                            }
                        }
                        String string = String.copyValueOf(charArray).trim();
                        int yolo = Integer.parseInt(string);
                        System.out.println("Yolo: " + yolo);
                        System.out.println();
                    }
                    in.close();
                } else {
                    System.out.println("empty");
                }
                long endTime = System.currentTimeMillis();
                System.out.println(endTime-startTime + " to finish ");
            }
        } catch (Exception e) { e.printStackTrace(); }
        comPort.closePort();


    }

}
