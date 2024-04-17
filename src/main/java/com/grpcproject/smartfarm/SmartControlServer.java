package com.grpcproject.smartfarm;

import com.grpcproject.smartfarm.EquipControlService.EquipControlServiceGrpc;
import com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class SmartControlServer {

}

class GrpcEquipServer {
    private final ManagedChannel channel;
    private final EquipControlServiceGrpc.EquipControlServiceBlockingStub bStub;

    public GrpcEquipServer(){
        ConsulFindUtil serveceFind = new ConsulFindUtil("localhost", 8500, "EquipServer");
        this.channel = ManagedChannelBuilder.forAddress(serveceFind.getServerHost(), serveceFind.getServerPort())
                .usePlaintext()
                .build();
        this.bStub = EquipControlServiceGrpc.newBlockingStub(channel);
    }

    public void equipPowerControl(String equipName){

        EquipControlServiceProto.EquipPowerReq equipPowerReq = EquipControlServiceProto.EquipPowerReq.newBuilder()
                .setEquipName(equipName)
                .build();

        String resMessage = bStub.equipPowerControl(equipPowerReq).getEquipPowerStatus();
        System.out.println(resMessage);
    }


    public int equipStatus(String equipName){
        EquipControlServiceProto.EquipStatusReq equipStatusReq = EquipControlServiceProto.EquipStatusReq
                .newBuilder()
                .setEquipStatusRequest(equipName)
                .build();

        return bStub.equipStatus(equipStatusReq).getEquipStatusCode();
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void heaterPower(int on1off0){
        int i = equipStatus("heater");
        if (i != on1off0){
            equipPowerControl("heater");
        }
    }

    public void sprinklerPower(int on1off0){
        int i = equipStatus("sprinkler");
        if (i != on1off0){
            equipPowerControl("sprinkler");
        }
    }
}
