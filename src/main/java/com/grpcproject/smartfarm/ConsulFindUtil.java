package com.grpcproject.smartfarm;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.health.model.HealthService;

import java.util.List;

public class ConsulFindUtil {
    private final ConsulClient consulClient;
    private final String consulServiceName;
    private HealthService healthService;

    public ConsulFindUtil(String consulHost, int consulPort, String consulServiceName){
        this.consulClient = new ConsulClient(consulHost, consulPort);
        this.consulServiceName = consulServiceName;
        getService();
    }

    private void getService(){
        List<HealthService> healthServices = consulClient.getHealthServices(consulServiceName, true, null).getValue();
        if (healthServices.isEmpty()) {
            System.err.println("No healthy instances of " + consulServiceName + " found in Consul.");
            return;
        }
        healthService = healthServices.get(0);
    }

    public String getServerHost(){
        return healthService.getService().getAddress();
    }

    public int getServerPort(){
        return healthService.getService().getPort();
    }

}
