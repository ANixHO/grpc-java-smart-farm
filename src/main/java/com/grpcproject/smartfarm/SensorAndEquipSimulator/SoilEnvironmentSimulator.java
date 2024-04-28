package com.grpcproject.smartfarm.SensorAndEquipSimulator;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SoilEnvironmentSimulator {

    static final Data soilData = new Data(20, 20);

    private final Thread sensor, soilSim, equip;

    public SoilEnvironmentSimulator() {

        sensor = new Thread(new Sensor());
        soilSim = new Thread(new SoilSim());
        equip = new Thread(new Equip());

    }

    public void start() {
        soilSim.start();
        sensor.start();
        equip.start();
    }

    public void shutdown(){
        equip.interrupt();
        sensor.interrupt();
        soilSim.interrupt();
    }


    // store soil temp and humidity
    static class Data {
        private int temp, humidity;

        public Data(int defaultTemp, int defaultHumidity) {
            this.temp = defaultTemp;
            this.humidity = defaultHumidity;
        }

        public int getTemp() {
            return temp;
        }

        // a method that modify the temp, keep the temp from 0 to 100
        public void tempUp(int val) {
            if ((temp + val) <= 0) {
                temp = 0;
            } else if ((temp + val) >= 100) {
                temp = 100;
            } else {
                temp += val;
            }
        }

        public int getHumidity() {
            return humidity;
        }

        // a method that modify the humidity, keep the humidity from 0 to 100
        public void humidityUp(int val) {
            if ((humidity + val) <= 0) {
                humidity = 0;
            } else if ((humidity + val) >= 100) {
                humidity = 100;
            } else {
                humidity += val;
            }
        }
    }

    /*
        soil simulator, simulate the humidity drop down every 5 seconds
        and temp drop down or up every 10 seconds.
     */
    static class SoilSim implements Runnable {

        private int airTempCur;
        private int airTempUpVal = -1;


        public SoilSim() {
            airTempCur = 25;
        }

        @Override
        public void run() {
            int tempChangeCount = 0;

            int tempUpValue;

            while (true) {

                // if soil temp is higher than air temp, then the soil temp will down,
                // otherwise, soil temp is lower than air, then the soil temp will up.
                tempUpValue = soilTempAndAirTempRelationModel(tempChangeCount);

                soilData.tempUp(tempUpValue);
                soilData.humidityUp(-1);
                System.out.println("SoilSim: temp " + soilData.getTemp() + "; humidity " + soilData.getHumidity());

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                tempChangeCount++;
            }
        }

        private int soilTempAndAirTempRelationModel(int tempChangeCount) {
            int tempUpValue = 0;

            // temp will be changed when the variable 'tempChangeCount' is even number
            if ((tempChangeCount % 2) == 0) {

                // when air temp reach 50, then it will go down
                // when air temp reach 10, then it will go up
                if (airTempCur >= 50) {
                    airTempUpVal = -1;
                } else if (airTempCur <= 0) {
                    airTempUpVal = 1;
                }

                airTempCur += airTempUpVal;

                // if soil temp is higher than air temp, the soil temp will go down
                // if soil temp is lower than air temp, the soil temp will go up
                // if they are same, soil temp will not change
                if (soilData.getTemp() > airTempCur) {
                    tempUpValue = -1;
                } else if (soilData.getTemp() < airTempCur) {
                    tempUpValue = 1;
                } else if (soilData.getTemp() == airTempCur) {
                    tempUpValue = 0;
                }

            }

            return tempUpValue;
        }


    }

    /*
        Equip thread, this thread runs a socket to
        accept an int array that contains modify value of temp and humidity,
        and it will modify the temp and humidity
    */
    static class Equip implements Runnable {

        public Equip() {
        }

        @Override
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(8082);
                System.out.println("Equip is waiting for client connection...");

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Sim: Equip connect successful!");

                    ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

                    int[] adjustVal = (int[]) in.readObject();

                    soilData.tempUp(adjustVal[0]);
                    soilData.humidityUp(adjustVal[1]);

                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    out.println("Temp is modified to : " + soilData.getTemp() + " ; Humidity is modified to : " + soilData.getHumidity());

                    in.close();
                    out.close();
                    clientSocket.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /*
        Sensor thread, this thread runs a socket to
        send an array that contains current temp and humidity
     */
    static class Sensor implements Runnable {

        public Sensor() {
        }

        @Override
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(8081);
                System.out.println("Sensor is waiting for client connection...");

                while (true) {
                    Socket clientSocket = serverSocket.accept();

                    int[] array = new int[]{soilData.getTemp(), soilData.getHumidity()};

                    ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

                    out.writeObject(array);

                    out.close();
                    clientSocket.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



