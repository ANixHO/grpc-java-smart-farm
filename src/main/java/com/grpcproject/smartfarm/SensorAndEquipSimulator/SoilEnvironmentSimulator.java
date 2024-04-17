package com.grpcproject.smartfarm.SensorAndEquipSimulator;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SoilEnvironmentSimulator {
    public static void main(String[] args) {
        final Data data = new Data(20, 50);

        Thread sensor = new Thread(new Sensor(data));
        Thread soilSim = new Thread(new SoilSim(data));
        Thread equip = new Thread(new Equip(data));

        soilSim.start();
        sensor.start();
        equip.start();
    }


    // Soil temp and humidity data
    static class Data {
        private int temp, humidity;
        private boolean isModifying = false;

        public Data(int defaultTemp, int defaultHumidity) {
            this.temp = defaultTemp;
            this.humidity = defaultHumidity;
        }

        public int getTemp() {
            return temp;
        }

        public void tempUp(int val) {
            temp += val;
        }

        public  int getHumidity() {
            return humidity;
        }

        public  void humidityUp(int val) {
            if ((humidity + val) < 0) {
                humidity = 0 ;
            } else if ((humidity + val) >= 100) {
                humidity = 100;
            }else{
                humidity += val;
            }
        }

        public boolean isModifying() {
            return isModifying;
        }

        public void setModifying(boolean modifying) {
            isModifying = modifying;
        }
    }

    // soil simulator, simulate the temperature and humidity drop down every 5 seconds
    static class SoilSim implements Runnable {
        private final Data soilData;

        private int airTempCur;
        private int airTempUpVal = -1;


        public SoilSim(Data data) {
            this.soilData = data;
            airTempCur = 25;
        }

        @Override
        public void run() {
            int tempChangeCount = 0;

            int tempUpValue ;

            while (true) {

                tempUpValue = soilTempAndAirTempRelationModel(tempChangeCount);

                while (soilData.isModifying()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                soilData.setModifying(true);
                soilData.tempUp(tempUpValue);
                soilData.humidityUp(-1);
                System.out.println("SoilSim: temp " + soilData.getTemp() + "; humidity " + soilData.getHumidity());
                soilData.setModifying(false);

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                tempChangeCount ++;
            }
        }

        private int soilTempAndAirTempRelationModel(int tempChangeCount){
            int tempUpValue = 0;

            if((tempChangeCount % 2) == 0) {

                if (airTempCur >= 50) {
                    airTempUpVal = -1;
                } else if (airTempCur <= 10) {
                    airTempUpVal = 1;
                }

                airTempCur += airTempUpVal;

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

    static class Equip implements Runnable {
        private final Data soilData;

        public Equip(Data data) {
            this.soilData = data;
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

                    while (soilData.isModifying()) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    soilData.setModifying(true);
                    soilData.tempUp(adjustVal[0]);
                    int temp = soilData.getTemp();
                    soilData.humidityUp(adjustVal[1]);
                    int humidity = soilData.getHumidity();
                    soilData.setModifying(false);

                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    out.println("Temp is modified to : " + temp + " ; Humidity is modified to : " + humidity);

                    in.close();
                    out.close();
                    clientSocket.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch(ClassNotFoundException e){
                throw new RuntimeException(e);
            }
        }
    }

    static class Sensor implements Runnable {
        private final Data soilData;

        public Sensor(Data data) {
            this.soilData = data;
        }

        @Override
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(8081);
                System.out.println("Sensor is waiting for client connection...");

                while (true) {
                    Socket clientSocket = serverSocket.accept();

                    while (soilData.isModifying()) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

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



