package com.grpcproject.smartfarm;

import com.grpcproject.smartfarm.EquipControlService.*;
import com.grpcproject.smartfarm.SensorDataCollectService.*;
import com.grpcproject.smartfarm.UserMonitorAndControlService.*;
import io.grpc.*;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SmartControlServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        SmartControlServer smartControlServer = new SmartControlServer();
        smartControlServer.start();

        smartControlServer.blockUntilShutdown();
    }


    private Server server;
    static final DataList dataList = new DataList();
    static final GrpcEquipServerClient grpcEquipServerClient = new GrpcEquipServerClient();
    static boolean isUserRunningEquip = false;
    static final Object threadLock = new Object();
    Thread smartControllorThread;


    public SmartControlServer() {
        smartControllorThread = new SmartController();
    }

    public void start() throws IOException {
        int port = 50062;

        server = ServerBuilder.forPort(port)
                .addService(new SensorDataCollectServiceImpl())
                .addService(new UserMonitorAndControlServiceImpl())
                .build()
                .start();

        System.out.println("Smart Control Server start, listening on port " + port);

        ConsulRegisterUtil registerUtil = new ConsulRegisterUtil("SmartControlServerConsul.properties");
        registerUtil.registerToConsul();

        smartControllorThread.start();

    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }

        if (!smartControllorThread.isInterrupted()) {
            smartControllorThread.interrupt();
        }
    }

    static class UserMonitorAndControlServiceImpl extends UserMonitorAndControlServiceGrpc.UserMonitorAndControlServiceImplBase {

        public void refreshSensorData(UserMonitorAndControlServiceProto.RefreshSensorDataReq req, StreamObserver<UserMonitorAndControlServiceProto.RefreshSensorDataRes> resObserver) {
            String reqMessage = req.getRequest();
            System.out.println("Request from user: " + reqMessage);

            UserMonitorAndControlServiceProto.RefreshSensorDataRes tempRes = UserMonitorAndControlServiceProto.RefreshSensorDataRes
                    .newBuilder()
                    .setSensorName("soil temp")
                    .setSensorData(dataList.getLastSoilTemp())
                    .build();

            UserMonitorAndControlServiceProto.RefreshSensorDataRes humidityRes = UserMonitorAndControlServiceProto.RefreshSensorDataRes
                    .newBuilder()
                    .setSensorName("soil humidity")
                    .setSensorData(dataList.getLastSoilHumidity())
                    .build();

            resObserver.onNext(tempRes);
            resObserver.onNext(humidityRes);

            resObserver.onCompleted();
        }

        public StreamObserver<UserMonitorAndControlServiceProto.RunEquipReq> runEquipment(UserMonitorAndControlServiceProto.RunEquipReq req, StreamObserver<UserMonitorAndControlServiceProto.EquipRunningStatusRes> resObserver) {
            return new StreamObserver<>() {
                @Override
                public void onNext(UserMonitorAndControlServiceProto.RunEquipReq req) {
                    String equipName = req.getEquipName();
                    long equipRuntime = req.getEquipRuntime();
                    System.out.println("Received run equip request: run " + equipName + " for " + equipRuntime + " seconds");

                    Runnable equipRunner = () -> {

                        isUserRunningEquip = true;

                        if (equipName.equals("heater")) {
                            grpcEquipServerClient.heaterPower(1);
                        } else if (equipName.equals("sprinkler")) {
                            grpcEquipServerClient.sprinklerPower(1);
                        }
                        System.out.println(equipName + " starts running...");

                        long i = 0L;

                        while (i <= equipRuntime || !Thread.currentThread().isInterrupted()) {
                            try {
                                Thread.sleep(1000);
                                i += grpcEquipServerClient.equipStatus(equipName);

                                UserMonitorAndControlServiceProto.EquipRunningStatusRes res = UserMonitorAndControlServiceProto.EquipRunningStatusRes
                                        .newBuilder()
                                        .setEquipName(equipName)
                                        .setEquipRunningTime(i)
                                        .build();
                                resObserver.onNext(res);

                            } catch (InterruptedException e) {
                                resObserver.onError(e);
                                throw new RuntimeException(e);

                            } finally {
                                if (equipName.equals("heater")) {
                                    grpcEquipServerClient.heaterPower(0);
                                } else if (equipName.equals("sprinkler")) {
                                    grpcEquipServerClient.sprinklerPower(0);
                                }

                                System.out.println(equipName + " stops running");
                                resObserver.onCompleted();

                                isUserRunningEquip = false;
                                threadLock.notifyAll();
                            }
                        }
                    };

                    Thread equipRunnerThread = new Thread(equipRunner);
                    equipRunnerThread.start();

                }

                @Override
                public void onError(Throwable throwable) {
                    System.out.println("Error from request " + throwable.getMessage());
                }

                @Override
                public void onCompleted() {
                    System.out.println("Run equip request streaming completed");
                    resObserver.onCompleted();
                }
            };
        }

    }

    static class SensorDataCollectServiceImpl extends SensorDataCollectServiceGrpc.SensorDataCollectServiceImplBase {

        @Override
        public StreamObserver<SensorDataCollectServiceProto.SaveSensorDataReq> saveSensorData(StreamObserver<SensorDataCollectServiceProto.SaveSensorDataRes> resObserver) {
            return new StreamObserver<>() {
                @Override
                public void onNext(SensorDataCollectServiceProto.SaveSensorDataReq saveSensorDataReq) {
                    dataList.addSoilTemp(saveSensorDataReq.getSoilTemp());
                    dataList.addSoilHumidity(saveSensorDataReq.getSoilHumidity());
                }

                @Override
                public void onError(Throwable throwable) {
                    System.out.println("Error in sensor data streaming: " + throwable.getMessage());
                }

                @Override
                public void onCompleted() {
                    System.out.println("Sensor data streaming completed");
                    SensorDataCollectServiceProto.SaveSensorDataRes res = SensorDataCollectServiceProto.SaveSensorDataRes
                            .newBuilder()
                            .setResponse("Save sensor data completed")
                            .build();

                    resObserver.onNext(res);
                    resObserver.onCompleted();
                }
            };
        }
    }

    class SmartController extends Thread {

        public SmartController() {
        }

        @Override
        public void run() {
            System.out.println("Thread: SmartController starts running!");

            while (!isInterrupted()) {
                try {
                    Thread.sleep(3000);

                    synchronized (threadLock) {
                        int soilTemp = dataList.getLastSoilTemp();
                        int soilHumidity = dataList.getLastSoilHumidity();

                        if (soilTemp < 15) {
                            grpcEquipServerClient.heaterPower(1);
                        } else if (soilTemp > 20) {
                            grpcEquipServerClient.heaterPower(0);
                        }

                        if (soilHumidity < 20) {
                            grpcEquipServerClient.sprinklerPower(1);
                        } else if (soilHumidity > 80) {
                            grpcEquipServerClient.sprinklerPower(0);
                        }

                        while (!isUserRunningEquip) {
                            threadLock.wait();
                        }
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

}

class DataList {
    private List<Integer> tempList;
    private List<Integer> humidityList;

    public DataList() {
        this.tempList = new LinkedList<>();
        this.humidityList = new LinkedList<>();
    }

    public void addSoilTemp(int soilTemp) {
        tempList.add(soilTemp);
        System.out.println("Add soil temp : " + soilTemp);
    }

    public void addSoilHumidity(int soilHumidity) {
        humidityList.add(soilHumidity);
        System.out.println("Add soil humidity : " + soilHumidity);
    }

    public int getLastSoilTemp() {
        return tempList.getLast();
    }

    public int getLastSoilHumidity() {
        return humidityList.getLast();
    }
}

class GrpcEquipServerClient {
    private final ManagedChannel channel;
    private final EquipControlServiceGrpc.EquipControlServiceBlockingStub bStub;

    public GrpcEquipServerClient() {
        ConsulFindUtil serveceFind = new ConsulFindUtil("localhost", 8500, "EquipServer");
        this.channel = ManagedChannelBuilder.forAddress(serveceFind.getServerHost(), serveceFind.getServerPort())
                .usePlaintext()
                .build();
        this.bStub = EquipControlServiceGrpc.newBlockingStub(channel);
    }

    public void equipPowerControl(String equipName) {

        EquipControlServiceProto.EquipPowerReq equipPowerReq = EquipControlServiceProto.EquipPowerReq.newBuilder()
                .setEquipName(equipName)
                .build();

        String resMessage = bStub.equipPowerControl(equipPowerReq).getEquipPowerStatus();
        System.out.println(resMessage);
    }

    public int equipStatus(String equipName) {
        EquipControlServiceProto.EquipStatusReq equipStatusReq = EquipControlServiceProto.EquipStatusReq
                .newBuilder()
                .setEquipStatusRequest(equipName)
                .build();

        return bStub.equipStatus(equipStatusReq).getEquipStatusCode();
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void heaterPower(int on1off0) {
        int i = equipStatus("heater");
        if (i != on1off0) {
            equipPowerControl("heater");
        }
    }

    public void sprinklerPower(int on1off0) {
        int i = equipStatus("sprinkler");
        if (i != on1off0) {
            equipPowerControl("sprinkler");
        }
    }
}
