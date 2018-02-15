package com;

public class Communications {

    static int[] communicationsArray;

    public Communications(int arraySize) {
        communicationsArray = new int[arraySize];
    }

    public static void updateSerialData() {
        String serialIn = "14,23,16,19";
        String[] parts = serialIn.split(",");
        int[] serialInDouble = new int[parts.length];
        for (int i = 0; i < parts.length; i ++){
            serialInDouble[i] = Integer.parseInt(parts[i]);
        }
        communicationsArray = serialInDouble;
    }

    public static int getDataByIndex(int index) {
        return communicationsArray[index];
    }

}
