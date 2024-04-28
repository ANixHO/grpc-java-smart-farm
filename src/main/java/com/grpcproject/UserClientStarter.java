package com.grpcproject;

import com.grpcproject.smartfarm.UserClient;


import java.io.IOException;

import java.util.Scanner;

public class UserClientStarter {

    static UserClient userClient;
    String temp, humidity;
    String heaterStatusMessage, sprinklerStatusMessage;
    static Scanner scanner;

    long heaterRuntime, sprinklerRuntime;

    static final Object lock = new Object();
    static boolean isUserEntering, terminate;


    public static void main(String[] args) throws InterruptedException {
        UserClientStarter userClientStarter = new UserClientStarter();

        NonBlockingInputThread inputThread = new NonBlockingInputThread();
        inputThread.start();


        synchronized (lock) {
            do {

                if (inputThread.isInputAvailable()) {
                    String input = inputThread.getInput();
                    userClientStarter.processUserInput(input);
                }
                if (isUserEntering) {
                    lock.wait();
                }

                try {
                    System.out.println("\n\n\n\n\n\n\n");
                    System.out.println("++++++++++++++++++++++++++++++++++++++++++");
                    userClientStarter.updateSensorData();
                    userClientStarter.updateEquipStatus();
                    System.out.println("  " + userClientStarter.temp + "\t\t" + userClientStarter.humidity + "\n");
                    System.out.println("  " + userClientStarter.heaterStatusMessage);
                    System.out.println("  " + userClientStarter.sprinklerStatusMessage);
                    System.out.println(
                            "\n  Press 'H' to run heater" +
                                    "\n  Press 'S' to run Sprinkler" +
                                    "\n  Press 'Q' to quit");


                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


            } while (!terminate);
        }


    }


    public UserClientStarter() {
        userClient = new UserClient();
        scanner = new Scanner(System.in);
        temp = "";
        humidity = "";
        isUserEntering = false;
        terminate = false;
    }

    public void updateEquipStatus() {

        if (userClient.isHeaterRunning()) {
            heaterStatusMessage = "HEATER    status: Running, " + userClient.getHeaterRunningTimeLeft() + " seconds left";
        } else {
            heaterStatusMessage = "HEATER    status: STOP";
        }

        if (userClient.isSprinklerRunning()) {
            sprinklerStatusMessage = "SPRINKLER status: Running, " + userClient.getSprinklerRunningTimeLeft() + " seconds left";
        } else {
            sprinklerStatusMessage = "SPRINKLER status: STOP";
        }

    }

    public void updateSensorData() {
        userClient.refreshSensorData();

        temp = "Temperature: " + userClient.getCurTemp();
        humidity = "Humidity: " + userClient.getCurHumidity();


    }

    public void processUserInput(String userInput) {

        isUserEntering = true;

        if (userInput.equals("H")) {
            System.out.println("Please enter the runtime (seconds) for HEATER ");
            heaterRuntime = Long.parseLong(scanner.nextLine());
            userClient.runEquipment("heater", heaterRuntime);

        } else if (userInput.equals("S")) {
            System.out.println("Please enter the runtime (seconds) for SPRINKLER ");
            sprinklerRuntime = Long.parseLong(scanner.nextLine());
            userClient.runEquipment("sprinkler", sprinklerRuntime);

        } else if (userInput.equals("Q")) {
            System.out.println("System shut down.....");
            terminate = true;

        } else {
            isUserEntering = false;
        }
        isUserEntering = false;

        lock.notifyAll();

    }


    static class NonBlockingInputThread extends Thread {
        private volatile boolean inputAvailable = false;
        private String input;

        public boolean isInputAvailable() {
            return inputAvailable;
        }

        public String getInput() {
            inputAvailable = false;
            return input;
        }

        public void run() {
            byte[] inputBytes = new byte[2];
            while (!terminate) {
                try {
                    if (System.in.available() > 0) {
                        int bytesRead = System.in.read(inputBytes);

                        if (bytesRead > 0) {
                            inputAvailable = true;
                            input = new String(inputBytes, 0, 1);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}



