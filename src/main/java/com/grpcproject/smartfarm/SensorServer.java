package com.grpcproject.smartfarm;


import com.grpcproject.smartfarm.SensorAndEquipSimulator.SoilEnvirenmentSimulatorConnector;
import com.grpcproject.smartfarm.SensorDataCollectService.SensorDataCollectServiceGrpc;
import com.grpcproject.smartfarm.SensorDataCollectService.SensorDataCollectServiceProto;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;


import java.util.concurrent.TimeUnit;

public class SensorServer {
    private final SoilEnvirenmentSimulatorConnector simConnector;

    private final ManagedChannel channel;
    private final SensorDataCollectServiceGrpc.SensorDataCollectServiceStub stub;
    private Thread sensorServerThread;

    public SensorServer() {
        ConsulFindUtil serverFind = new ConsulFindUtil("localhost", 8500, "SmartControlServer");
        this.channel = ManagedChannelBuilder
                .forAddress(serverFind.getServerHost(), serverFind.getServerPort())
                .usePlaintext()
                .build();
        this.stub = SensorDataCollectServiceGrpc.newStub(channel);

        this.simConnector = new SoilEnvirenmentSimulatorConnector();

    }

    /*
        When sensor server start,
        it will run a thread that get temp and humidity data
        from soilsim via soilsim connector,
        then send the data to smart control server.
     */
    public void start() {
        sensorServerThread = new Thread(() -> {
            int[] data;

            while (!sensorServerThread.isInterrupted()) {
                try {

                    // get temp and humidity data from soil sim via socket
                    data = simConnector.getTempAndHumidity();

                    // send it to smart control server via grpc client
                    saveSensorData(data[0], data[1]);

                    // repeat process every 1 second
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });
        sensorServerThread.start();
    }


    public void saveSensorData(int soilTemp, int soilHumidity) {
        StreamObserver<SensorDataCollectServiceProto.SaveSensorDataRes> resObserver = new StreamObserver<>() {
            @Override
            public void onNext(SensorDataCollectServiceProto.SaveSensorDataRes saveSensorDataRes) {
                System.out.println("Server response: " + saveSensorDataRes.getResponse());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error in Streaming sensor data: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Streaming sensor data completed");
            }
        };
        StreamObserver<SensorDataCollectServiceProto.SaveSensorDataReq> reqObserver = stub.saveSensorData(resObserver);

        SensorDataCollectServiceProto.SaveSensorDataReq sensorData = SensorDataCollectServiceProto.SaveSensorDataReq
                .newBuilder()
                .setSoilTemp(soilTemp)
                .setSoilHumidity(soilHumidity)
                .build();

        reqObserver.onNext(sensorData);
        reqObserver.onCompleted();
    }

    public void shutdown() throws InterruptedException {
        sensorServerThread.interrupt();
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

}
