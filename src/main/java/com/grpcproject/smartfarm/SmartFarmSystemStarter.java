package com.grpcproject.smartfarm;

import com.grpcproject.smartfarm.SensorAndEquipSimulator.*;

import java.io.IOException;

public class SmartFarmSystemStarter {

    SoilEnvironmentSimulator soilEnvironmentSimulator;
    EquipServer equipServer;
    SensorServer sensorServer;
    SmartControlServer smartControlServer;
    public SmartFarmSystemStarter() throws IOException, InterruptedException {
        soilEnvironmentSimulator = new SoilEnvironmentSimulator();
        equipServer = new EquipServer();
        sensorServer = new SensorServer();
        smartControlServer = new SmartControlServer();

    }

    public void start() throws IOException, InterruptedException {
        equipServer.start();
        sensorServer.start();
        smartControlServer.start();

    }



}
