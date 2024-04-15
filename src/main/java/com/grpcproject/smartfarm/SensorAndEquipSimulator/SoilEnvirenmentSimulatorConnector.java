package com.grpcproject.smartfarm.SensorAndEquipSimulator;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class SoilEnvirenmentSimulatorConnector {

    public SoilEnvirenmentSimulatorConnector() {
    }

    /*
        it can adjust the soil temperature and soil humidity,
        if put the positive int as parameter, it will make soil temp or humidity up,
        if put the negative int as parameter, it will make soil temp or humidity down
    */

    public String adjust(int tempAdjust, int humidityAdjust) {
        int[] arr = new int[]{tempAdjust, humidityAdjust};
        String sensorData = null;

        try {
            Socket clientSocket = new Socket("localhost", 8082);

            clientSocket.setSoTimeout(5000); // 5 seconds timeout

            try {
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                out.writeObject(arr);

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                sensorData = "SoilSim Response: " + in.readLine();
                System.out.println(sensorData);

                // Close connections for this request
                out.close();
                in.close();
            } catch (SocketTimeoutException e) {
                System.out.println("Timeout: Server took too long to respond.");
            }

            // Close the socket
            clientSocket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return sensorData;
    }

    /*
        this method will return the current soil temp and humidity,
        it will return an int array, its length is 2,
        the first value is temp, second is humidity
     */
    public int[] getTempAndHumidity() {

        int[] arr = new int[2];
        try {
            Socket clientSocket = new Socket("localhost", 8081);
            clientSocket.setSoTimeout(5000); // 5 seconds timeout

            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            arr = (int[]) in.readObject();

            // Close connections for this request
            in.close();

            // Close the socket
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return arr;
    }
}


