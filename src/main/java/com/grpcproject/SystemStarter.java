package com.grpcproject;

import com.grpcproject.smartfarm.EquipServer;
import com.grpcproject.smartfarm.SensorServer;
import com.grpcproject.smartfarm.SmartControlServer;
import com.grpcproject.smartfarm.SensorAndEquipSimulator.*;


import java.io.IOException;

public class SystemStarter {
    public static void main(String[] args) throws IOException, InterruptedException {

        SoilEnvironmentSimulator soilEnvironmentSimulator = new SoilEnvironmentSimulator();
        EquipServer equipServer = new EquipServer();
        SensorServer sensorServer = new SensorServer();
        SmartControlServer smartControlServer = new SmartControlServer();

        soilEnvironmentSimulator.start();
        equipServer.start();
        sensorServer.start();
        smartControlServer.start();


    }
}
