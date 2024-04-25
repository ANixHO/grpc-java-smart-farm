package com.grpcproject.smartfarm;

import com.grpcproject.smartfarm.EquipControlService.*;
import com.grpcproject.smartfarm.SensorDataCollectService.*;
import com.grpcproject.smartfarm.UserMonitorAndControlService.*;
import io.grpc.*;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class SmartControlServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        SmartControlServer smartControlServer = new SmartControlServer();
        smartControlServer.start();

        smartControlServer.blockUntilShutdown();
    }


    private Server server;

    /*
        There are two lists that temp list and humidity list to store the temp and humidity.
        And the DataList class has methods that add data to lists and get data from lists.
     */
    static final DataList dataList = new DataList();

    /*
        The following class contains methods that connect to Equip Server to
        get the equip status of power and control the equip power.
     */
    static final GrpcEquipServerClient grpcEquipServerClient = new GrpcEquipServerClient();

    /*
        Following two threads are AUTOMATIC controller for heater and sprinkler
        When soil temp get down to less than 15,
        'heaterSmartController' thread will turn on the heater for 5 seconds.
        When soil humidity get down to less than 20,
        'sprinklerSmartController' thread will turn on the sprinkler for 10 seconds.
     */
    static Thread heaterSmartController;
    static Thread sprinklerSmartController;

    /*
       Following two boolean fields and Object fields are using for
       pause the 'heaterSmartController' and 'sprinklerSmartController' threads.
       when user client sends a request to run the heater or sprinkler
       the boolean field will be changed to true,
       then the lock will execute the wait method for automatic controller thread,
       after the equip running time finish, the notifyAll method will be executed
       to continue the automatic controller thread running.

    */
    static boolean isUserRunningHeater = false;
    static boolean isUserRunningSprinkler = false;

    static final Object heaterThreadLock = new Object();
    static final Object sprinklerThreadLock = new Object();



    public SmartControlServer() {

    }

    public void start() throws IOException {
        int port = 50062;

        server = ServerBuilder.forPort(port)
                .addService(new SensorDataCollectServiceImpl())
                .addService(new UserMonitorAndControlServiceImpl())
                .build()
                .start();

        System.out.println("Smart Control Server start, listening on port " + port);

        // register the server to consul
        ConsulRegisterUtil registerUtil = new ConsulRegisterUtil("SmartControlServerConsul.properties");
        registerUtil.registerToConsul();

        // instant the automatic controller threads, then start the threads
        heaterSmartController = new HeaterSmartController();
        sprinklerSmartController = new SprinklerSmartController();

        heaterSmartController.start();
        sprinklerSmartController.start();

    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }

        if (!heaterSmartController.isInterrupted()) {
            heaterSmartController.interrupt();
        }

        if (!sprinklerSmartController.isInterrupted()) {
            sprinklerSmartController.interrupt();
        }
    }

    static class UserMonitorAndControlServiceImpl extends UserMonitorAndControlServiceGrpc.UserMonitorAndControlServiceImplBase {

        // when user request the soil temp and humidity data, the following method will return these data.
        public void refreshSensorData(UserMonitorAndControlServiceProto.RefreshSensorDataReq req, StreamObserver<UserMonitorAndControlServiceProto.RefreshSensorDataRes> resObserver) {
            String reqMessage = req.getRequest();
            System.out.println("Request from user: " + reqMessage);

            int temp = dataList.getLastSoilTemp();
            int humidity = dataList.getLastSoilHumidity();

            UserMonitorAndControlServiceProto.RefreshSensorDataRes tempRes = UserMonitorAndControlServiceProto.RefreshSensorDataRes
                    .newBuilder()
                    .setSensorName("soil temp")
                    .setSensorData(temp)
                    .build();

            UserMonitorAndControlServiceProto.RefreshSensorDataRes humidityRes = UserMonitorAndControlServiceProto.RefreshSensorDataRes
                    .newBuilder()
                    .setSensorName("soil humidity")
                    .setSensorData(humidity)
                    .build();

            resObserver.onNext(tempRes);
            resObserver.onNext(humidityRes);

            resObserver.onCompleted();
        }

        /*
             when user client send a run equip request with equip name and running time
             the following method will start run a new thread to run the equipment.
             In the meantime, the automatic controller thread will be paused.
         */
        public StreamObserver<UserMonitorAndControlServiceProto.RunEquipReq> runEquipment(UserMonitorAndControlServiceProto.RunEquipReq req, StreamObserver<UserMonitorAndControlServiceProto.EquipRunningStatusRes> resObserver) {
            return new StreamObserver<>() {
                @Override
                public void onNext(UserMonitorAndControlServiceProto.RunEquipReq req) {
                    String equipName = req.getEquipName();
                    long equipRuntime = req.getEquipRuntime();
                    System.out.println("Received run equip request: run " + equipName + " for " + equipRuntime + " seconds");

                    if (equipName.equals("heater"))
                        userRunsHeater(equipRuntime, resObserver);
                    else if (equipName.equals("sprinkler"))
                        userRunsSprinkler(equipRuntime, resObserver);

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

        private void userRunsHeater(
                long equipRuntime,
                StreamObserver<UserMonitorAndControlServiceProto.EquipRunningStatusRes> resObserver) {

            Runnable equipRunner = () -> {
                synchronized (heaterThreadLock) {
                    try {

                        // make 'heaterSmartController' pause(wait)
                        isUserRunningHeater = true;

                        // wait 5 seconds to let 'heaterSmartController' finish the remaining task
                        // and step into waiting status
                        Thread.sleep(5000);
                        System.out.println("Heater starts running...");

                        // turn the heater power on
                        grpcEquipServerClient.heaterPower(1);

                        long i = 0L;

                        // send the equip runned time to user client every second until it reach the time that user setting
                        while (i <= equipRuntime || !Thread.currentThread().isInterrupted()) {

                            Thread.sleep(1000);
                            i += grpcEquipServerClient.equipStatus("heater");

                            UserMonitorAndControlServiceProto.EquipRunningStatusRes res = UserMonitorAndControlServiceProto.EquipRunningStatusRes
                                    .newBuilder()
                                    .setEquipName("heater")
                                    .setEquipRunningTime(i)
                                    .build();
                            resObserver.onNext(res);

                        }

                        resObserver.onCompleted();

                        //  make 'heaterSmartController' ready to run
                        isUserRunningHeater = false;

                        // make 'heaterSmartController' keep running
                        heaterThreadLock.notifyAll();


                    } catch (InterruptedException e) {
                        resObserver.onError(e);

                    } finally {

                        // if this thread shut off by error,
                        // then turn off the heater, and complete the grpc response to user client
                        // then make 'heaterSmartController' keep running
                        grpcEquipServerClient.heaterPower(0);

                        System.out.println("Heater stops running");
                        resObserver.onCompleted();
                        isUserRunningHeater = false;
                        heaterThreadLock.notifyAll();
                    }
                }
            };

            Thread equipRunnerThread = new Thread(equipRunner);
            equipRunnerThread.start();
        }

        private void userRunsSprinkler(
                long equipRuntime,
                StreamObserver<UserMonitorAndControlServiceProto.EquipRunningStatusRes> resObserver) {

            Runnable equipRunner = () -> {
                synchronized (sprinklerThreadLock) {
                    try {
                        // make 'sprinklerSmartController' pause(wait)
                        isUserRunningSprinkler = true;

                        // wait 10 seconds to let 'sprinklerSmartController' finish the remaining task
                        // and step into waiting status
                        Thread.sleep(10000);
                        System.out.println("Sprinkler starts running...");

                        // turn the sprinkler power on
                        grpcEquipServerClient.sprinklerPower(1);

                        long i = 0L;

                        // send the equip runned time to user client every second until it reach the time that user setting
                        while (i <= equipRuntime || !Thread.currentThread().isInterrupted()) {

                            Thread.sleep(1000);
                            i += grpcEquipServerClient.equipStatus("sprinkler");

                            UserMonitorAndControlServiceProto.EquipRunningStatusRes res = UserMonitorAndControlServiceProto.EquipRunningStatusRes
                                    .newBuilder()
                                    .setEquipName("sprinkler")
                                    .setEquipRunningTime(i)
                                    .build();
                            resObserver.onNext(res);

                        }

                        resObserver.onCompleted();

                        // make 'sprinklerSmartController' ready to run
                        isUserRunningSprinkler = false;

                        // make 'sprinklerSmartController' keep running
                        sprinklerThreadLock.notifyAll();


                    } catch (InterruptedException e) {
                        resObserver.onError(e);

                    } finally {

                        // if this thread shut off by error,
                        // then turn off the heater, and complete the grpc response to user client
                        // then make 'heaterSmartController' keep running
                        grpcEquipServerClient.sprinklerPower(0);

                        System.out.println("Sprinkler stops running");
                        resObserver.onCompleted();
                        isUserRunningSprinkler = false;
                        sprinklerThreadLock.notifyAll();
                    }
                }
            };

            Thread equipRunnerThread = new Thread(equipRunner);
            equipRunnerThread.start();
        }

    }

    static class SensorDataCollectServiceImpl extends SensorDataCollectServiceGrpc.SensorDataCollectServiceImplBase {

        @Override
        public StreamObserver<SensorDataCollectServiceProto.SaveSensorDataReq> saveSensorData(StreamObserver<SensorDataCollectServiceProto.SaveSensorDataRes> resObserver) {
            return new StreamObserver<>() {
                @Override
                public void onNext(SensorDataCollectServiceProto.SaveSensorDataReq saveSensorDataReq) {
                    // receive temp and humidity data from sensor server
                    int temp = saveSensorDataReq.getSoilTemp();
                    int humidity = saveSensorDataReq.getSoilHumidity();

                    // add temp and humidity to data list class
                    dataList.addSoilTemp(temp);
                    dataList.addSoilHumidity(humidity);
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

    static class HeaterSmartController extends Thread {

        public HeaterSmartController() {
        }

        @Override
        public void run() {
            System.out.println("Thread: Heater Smart Controller starts running!");

            synchronized (heaterThreadLock) {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(1000);

                        /*
                            When user want to run the heater manually,
                            this automatic heater controller will turn the heater off
                            and step into wait status.
                            After user's manual operation finish, this thread will be notified
                            and keep running automatic control
                         */

                        if (isUserRunningHeater) {
                            grpcEquipServerClient.heaterPower(0);
                            heaterThreadLock.wait();
                        }

                        int soilTemp = dataList.getLastSoilTemp();

                        /*
                            when soil temp drop down less than 15,
                            this automatic heater controller will turn the heater on for 5 seconds
                         */
                        if (soilTemp < 15) {
                            grpcEquipServerClient.heaterPower(1);
                            System.out.println("Smart controller: Turn on the heater power");

                            Thread.sleep(5000);

                            grpcEquipServerClient.heaterPower(0);
                            System.out.println("Smart controller: Turn off the heater power");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class SprinklerSmartController extends Thread {

        public SprinklerSmartController() {
        }

        @Override
        public void run() {
            System.out.println("Thread: Sprinkler Smart Controller starts running!");

            synchronized (sprinklerThreadLock) {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(1000);

                        /*
                            when user want to run the sprinkler manually,
                            this automatic heater controller will turn the sprinkler off
                            and step into wait status.
                            After user's manual operation finish, this thread will be notified
                            and keep running automatic control
                         */
                        if (isUserRunningSprinkler) {
                            grpcEquipServerClient.sprinklerPower(0);
                            sprinklerThreadLock.wait();
                        }

                        int soilHumidity = dataList.getLastSoilHumidity();

                        /*
                            when soil humidity drop down less than 20,
                            this automatic sprinkler controller will turn the heater on for 10 seconds
                         */
                        if (soilHumidity < 20) {
                            grpcEquipServerClient.sprinklerPower(1);
                            System.out.println("Smart controller: Turn on the sprinkler");

                            Thread.sleep(10000);

                            grpcEquipServerClient.sprinklerPower(0);
                            System.out.println("Smart controller: Turn off the sprinkler");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}

class DataList {
    private final List<Integer> tempList;
    private final List<Integer> humidityList;

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
        int temp = -1000;

        /*
            If there is no data in the temp list,
            wait 5 seconds to receive the data from sensor server
         */
        if (tempList.isEmpty()) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }else {
            temp = tempList.getLast();
        }
        return temp;
    }

    public int getLastSoilHumidity() {
        int humidity = -1000;

        /*
            If there is no data in the humidity list,
            wait 5 seconds to receive the data from sensor server
         */
        if (humidityList.isEmpty()) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            humidity = humidityList.getLast();
        }
        return humidity;
    }
}

class GrpcEquipServerClient {
    private final ManagedChannel channel;
    private final EquipControlServiceGrpc.EquipControlServiceBlockingStub bStub;

    public GrpcEquipServerClient() {
        ConsulFindUtil serviceFind = new ConsulFindUtil(
                "localhost",
                8500,
                "EquipServer");
        this.channel = ManagedChannelBuilder
                .forAddress(serviceFind.getServerHost(), serviceFind.getServerPort())
                .usePlaintext()
                .build();
        this.bStub = EquipControlServiceGrpc.newBlockingStub(channel);
    }

    public void equipPowerControl(String equipName) {

        EquipControlServiceProto.EquipPowerReq equipPowerReq = EquipControlServiceProto.EquipPowerReq
                .newBuilder()
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

    public void heaterPower(int on_1_off_0) {
        int i = equipStatus("heater");
        if (i != on_1_off_0) {
            equipPowerControl("heater");
        }
    }

    public void sprinklerPower(int on_1_off_0) {
        int i = equipStatus("sprinkler");
        if (i != on_1_off_0) {
            equipPowerControl("sprinkler");
        }
    }
}
