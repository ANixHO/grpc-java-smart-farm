package com.grpcproject;

import com.grpcproject.smartfarm.SmartFarmSystemStarter;

import java.io.IOException;

public class SystemStarter {
    public static void main(String[] args) throws IOException, InterruptedException {
        SmartFarmSystemStarter smartFarmSystemStarter = new SmartFarmSystemStarter();
        smartFarmSystemStarter.start();
    }
}
