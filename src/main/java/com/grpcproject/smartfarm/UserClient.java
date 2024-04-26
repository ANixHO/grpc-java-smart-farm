package com.grpcproject.smartfarm;

import com.grpcproject.smartfarm.UserMonitorAndControlService.*;
import io.grpc.*;
import io.grpc.stub.StreamObserver;

public class UserClient {
    private final ManagedChannel channel;
    private final UserMonitorAndControlServiceGrpc.UserMonitorAndControlServiceStub stub;


    public UserClient() {
        ConsulFindUtil serverFind = new ConsulFindUtil(
                "localhost",
                8500,
                "SmartControlServer");
        this.channel = ManagedChannelBuilder
                .forAddress(serverFind.getServerHost(), serverFind.getServerPort())
                .usePlaintext()
                .build();
        this.stub = UserMonitorAndControlServiceGrpc.newStub(channel);
    }

    public void refreshSensorData() {
        StreamObserver<UserMonitorAndControlServiceProto.RefreshSensorDataRes> resObserver = new StreamObserver<>() {
            @Override
            public void onNext(UserMonitorAndControlServiceProto.RefreshSensorDataRes refreshSensorDataRes) {

                String sensorName = refreshSensorDataRes.getSensorName();
                int sensorData = refreshSensorDataRes.getSensorData();
                System.out.println(sensorName + " : " + sensorData);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error in smart controller server is streaming data: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Smart controller server streaming Newest sensor data complete");
            }
        };

        UserMonitorAndControlServiceProto.RefreshSensorDataReq req = UserMonitorAndControlServiceProto.RefreshSensorDataReq
                .newBuilder()
                .setRequest("Request current temp and humidity from user-client")
                .build();

        stub.refreshSensorData(req, resObserver);
        System.out.println("Refresh Sensor data request sent complete");
    }

    public void runEquipment(String equipName, long equipRuntime) {
        StreamObserver<UserMonitorAndControlServiceProto.EquipRunningStatusRes> resObserver = new StreamObserver<>() {
            @Override
            public void onNext(UserMonitorAndControlServiceProto.EquipRunningStatusRes equipRunningStatusRes) {
                String runningEquipName = equipRunningStatusRes.getEquipName();
                long runningTime = equipRunningStatusRes.getEquipRunningTime();

                System.out.println(runningEquipName + " : " + runningTime);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error from smart control server: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Run equip complete");
            }
        };

        StreamObserver<UserMonitorAndControlServiceProto.RunEquipReq> reqObserver = stub.runEquipment(resObserver);

        UserMonitorAndControlServiceProto.RunEquipReq req = UserMonitorAndControlServiceProto.RunEquipReq
                .newBuilder()
                .setEquipName(equipName)
                .setEquipRuntime(equipRuntime)
                .build();
        reqObserver.onNext(req);
        reqObserver.onCompleted();
    }


}
