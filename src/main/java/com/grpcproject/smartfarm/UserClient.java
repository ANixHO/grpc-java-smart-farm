package com.grpcproject.smartfarm;

import com.grpcproject.smartfarm.UserMonitorAndControlService.*;
import io.grpc.*;
import io.grpc.stub.StreamObserver;

public class UserClient {
    private final ManagedChannel channel;
    private final UserMonitorAndControlServiceGrpc.UserMonitorAndControlServiceStub stub;

    private long curTemp, curHumidity;
    private long heaterRunningTimeLeft, sprinklerRunningTimeLeft;

    private boolean isHeaterRunning, isSprinklerRunning;


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

        setHeaterRunning(false);
        setSprinklerRunning(false);
    }

    public void refreshSensorData() {
        StreamObserver<UserMonitorAndControlServiceProto.RefreshSensorDataRes> resObserver = new StreamObserver<>() {
            @Override
            public void onNext(UserMonitorAndControlServiceProto.RefreshSensorDataRes refreshSensorDataRes) {

                String sensorName = refreshSensorDataRes.getSensorName();
                int sensorData = refreshSensorDataRes.getSensorData();

                if(sensorName.equals("soil temp")){
                    setCurTemp(sensorData);
                }else if(sensorName.equals("soil humidity")){
                    setCurHumidity(sensorData);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error in smart controller server is streaming data: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
//                System.out.println("Temp: " + getCurTemp() + " ; Humidity: " + getCurHumidity());
//                System.out.println("Smart controller server streaming Newest sensor data complete");
            }
        };

        UserMonitorAndControlServiceProto.RefreshSensorDataReq req = UserMonitorAndControlServiceProto.RefreshSensorDataReq
                .newBuilder()
                .setRequest("Request current temp and humidity from user-client")
                .build();

        stub.refreshSensorData(req, resObserver);
//        System.out.println("Refresh Sensor data request sent complete");
    }

    public void runEquipment(String equipName, long equipRuntime) {
        StreamObserver<UserMonitorAndControlServiceProto.EquipRunningStatusRes> resObserver = new StreamObserver<>() {
            @Override
            public void onNext(UserMonitorAndControlServiceProto.EquipRunningStatusRes equipRunningStatusRes) {
                String runningEquipName = equipRunningStatusRes.getEquipName();
                long runningTimeLeft = equipRunningStatusRes.getEquipRunningTime();

                if(runningEquipName.equals("heater")){
                    setHeaterRunningTimeLeft(runningTimeLeft);
                }else if (runningEquipName.equals("sprinkler")){
                    setSprinklerRunningTimeLeft(runningTimeLeft);
                }

            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error from smart control server: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                if(equipName.equals("heater")){
                    setHeaterRunning(false);
                    setHeaterRunningTimeLeft(0L);
                }else if (equipName.equals("sprinkler")){
                    setSprinklerRunning(false);
                    setSprinklerRunningTimeLeft(0L);
                }
//                System.out.println("Run " + equipName + " complete");
            }
        };

        if(equipName.equals("heater")){
            setHeaterRunning(true);
        }else if (equipName.equals("sprinkler")){
            setSprinklerRunning(true);
        }

        StreamObserver<UserMonitorAndControlServiceProto.RunEquipReq> reqObserver = stub.runEquipment(resObserver);

        UserMonitorAndControlServiceProto.RunEquipReq req = UserMonitorAndControlServiceProto.RunEquipReq
                .newBuilder()
                .setEquipName(equipName)
                .setEquipRuntime(equipRuntime)
                .build();
        reqObserver.onNext(req);
        reqObserver.onCompleted();
    }

    public long getCurTemp() {
        return curTemp;
    }

    public void setCurTemp(long curTemp) {
        this.curTemp = curTemp;
    }

    public long getCurHumidity() {
        return curHumidity;
    }

    public void setCurHumidity(long curHumidity) {
        this.curHumidity = curHumidity;
    }

    public long getHeaterRunningTimeLeft() {
        return heaterRunningTimeLeft;
    }

    public void setHeaterRunningTimeLeft(long heaterRunningTimeLeft) {
        this.heaterRunningTimeLeft = heaterRunningTimeLeft;
    }

    public long getSprinklerRunningTimeLeft() {
        return sprinklerRunningTimeLeft;
    }

    public void setSprinklerRunningTimeLeft(long sprinklerRunningTimeLeft) {
        this.sprinklerRunningTimeLeft = sprinklerRunningTimeLeft;
    }

    public boolean isHeaterRunning() {
        return isHeaterRunning;
    }

    public void setHeaterRunning(boolean heaterRunning) {
        isHeaterRunning = heaterRunning;
    }

    public boolean isSprinklerRunning() {
        return isSprinklerRunning;
    }

    public void setSprinklerRunning(boolean sprinklerRunning) {
        isSprinklerRunning = sprinklerRunning;
    }
}
