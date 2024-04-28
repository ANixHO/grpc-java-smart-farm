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
                            "\n  Press 'H' to run HEATER" +
                                    "\n  Press 'S' to run SPRINKLER" +
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

    // get current temp and humidity from user client via grpc
    // and save data to the string fields
    public void updateSensorData() {
        userClient.refreshSensorData();

        temp = "Temperature: " + userClient.getCurTemp();
        humidity = "Humidity: " + userClient.getCurHumidity();
    }

    // when user want to run equip and input a alphabet,
    // it will execute the runEquipment method of userClient
    public void processUserInput(String userInput) {

        isUserEntering = true;

        if (userInput.equalsIgnoreCase("H")) {
            System.out.println("Please enter the runtime (seconds) for HEATER ");
            heaterRuntime = Long.parseLong(scanner.nextLine());
            userClient.runEquipment("heater", heaterRuntime);

        } else if (userInput.equalsIgnoreCase("S")) {
            System.out.println("Please enter the runtime (seconds) for SPRINKLER ");
            sprinklerRuntime = Long.parseLong(scanner.nextLine());
            userClient.runEquipment("sprinkler", sprinklerRuntime);

        } else if (userInput.equalsIgnoreCase("Q")) {
            System.out.println("System shut down.....");
            terminate = true;

        } else {
            System.out.println("\nPlease enter the correct alphabet. \nYou only can input 'H', 'S' and 'Q' ");
        }

        isUserEntering = false;

        lock.notifyAll();

    }


    // This thread is a nonblocking input watching thread
    // it will get the user input, but will not stop the main thread which displays the temp and humidity
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
                            // it will accept one alphabet and one enter key
                            // I only need first alphabet and store it to input string field
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



