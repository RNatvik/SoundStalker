package com;

public class Communications {

    static int[] communicationsArray;

    public Communications(int arraySize) {
        this.communicationsArray = new int[arraySize];
    }

    public void getSerialData() {
        String serialIn = "14.5,23.7,16.3,19.5";
        String[] parts = serialIn.split(",");
        double[] serialInDouble = new double[parts.length];
        for (int i = 0; i < parts.length; i ++){
            serialInDouble[i] = Double.parseDouble(parts[i]);
        }
        this.communicationsArray = serialInDouble;
    }

    public int[] getDataByIndex(int index) {
        return communicationsArray[index];
    }

}