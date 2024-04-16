package com.grpcproject.smartfarm;

import com.grpcproject.smartfarm.EquipControlService.EquipControlServiceGrpc;
import com.grpcproject.smartfarm.EquipControlService.EquipControlServiceProto;
import com.grpcproject.smartfarm.SensorAndEquipSimulator.SoilEnvirenmentSimulatorConnector;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class EquipServer {
    private Server server;

    public static void main(String[] args) throws InterruptedException, IOException {
        EquipPower equipPower = new EquipPower();
        Thread equipControl = new Thread(new EquipController(equipPower));
        equipControl.start();

        EquipServer equipServer = new EquipServer();
        equipServer.start(equipPower, equipControl);
        equipServer.blockUntilShutdown();

    }

    private void start(EquipPower equipPower, Thread equipController) throws IOException {
        int port = 50061;
        server = ServerBuilder.forPort(port)
                .addService(new EquipServerImpl(equipPower, equipController))
                .build()
                .start();
        System.out.println("Equip Server started, listening on " + port);

        ConsulRegisterUtil registerUtil = new ConsulRegisterUtil("EquipServerConsul.properties");
        registerUtil.registerToConsul();

    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }


    static class EquipServerImpl extends EquipControlServiceGrpc.EquipControlServiceImplBase {
        private EquipPower equipPower;
        private Thread equipController;

        public EquipServerImpl(EquipPower equipPower, Thread equipController) {
            super();
            this.equipPower = equipPower;
            this.equipController = equipController;
        }

        public StreamObserver<EquipControlServiceProto.EquipPowerReq> equipPowerControl(StreamObserver<EquipControlServiceProto.EquipPowerRes> resObserver) {
            return new StreamObserver<EquipControlServiceProto.EquipPowerReq>() {
                @Override
                public void onNext(EquipControlServiceProto.EquipPowerReq equipPowerReq) {
                    String equipName = equipPowerReq.getEquipName();
                    if (equipName.equals("heater")) {
                        equipPower.changeHeaterPower();

                    }
                    if (equipName.equals("sprinkler")) {
                        equipPower.changeSprinklerPower();
                    }

                }

                @Override
                public void onError(Throwable throwable) {
                    System.err.println("Error in client information streaming: " + throwable.getMessage());
                }

                @Override
                public void onCompleted() {
                    System.out.println("Equip power request streaming completed.");

                    EquipControlServiceProto.EquipPowerRes res = EquipControlServiceProto.EquipPowerRes
                            .newBuilder()
                            .setEquipPowerStatus(
                                    "Heater is " + equipPower.statusCodeToString(equipPower.getHeaterPower()) +
                                    " ; Sprinkler is " + equipPower.statusCodeToString((equipPower.getSprinklerPower())))
                            .build();

                    resObserver.onNext(res);
                    resObserver.onCompleted();
                }
            };
        }

        public void equipStatus(EquipControlServiceProto.EquipStatusReq req, StreamObserver<EquipControlServiceProto.EquipStatusRes> resObserver){
            String resEquipName = req.getEquipStatusRequest();
            int resEquipStatusCode = -1;

            if (resEquipName.equals("heater")){
                resEquipStatusCode = equipPower.getHeaterPower();
            }

            if (resEquipName.equals("sprinkler")) {
                resEquipStatusCode = equipPower.getSprinklerPower();
            }

            EquipControlServiceProto.EquipStatusRes res = EquipControlServiceProto.EquipStatusRes
                    .newBuilder()
                    .setEquipName(resEquipName)
                    .setEquipStatusCode(resEquipStatusCode)
                    .build();

            resObserver.onNext(res);
            resObserver.onCompleted();

        }
    }


}

class EquipPower {

    // 0 is power off, 1 is power on
    private int heaterPower = 0;
    private int sprinklerPower = 0;

    public EquipPower() {
    }

    public int getHeaterPower() {
        return heaterPower;
    }
    public int getSprinklerPower() {
        return sprinklerPower;
    }

    public void changeHeaterPower() {
        if (heaterPower == 0) {
            heaterPower = 1;
        } else if (heaterPower == 1) {
            heaterPower = 0;
        }
        System.out.println("change heater power status: " + heaterPower);
    }

    public void changeSprinklerPower() {
        if (sprinklerPower == 0) {
            sprinklerPower = 1;
        } else if (sprinklerPower == 1) {
            sprinklerPower = 0;
        }
        System.out.println("change sprinkler power status: " + sprinklerPower);
    }

    public String statusCodeToString(int statusCode){
        if(statusCode == 0){
            return "off";
        }else{
            return "on";
        }
    }
}

class EquipController implements Runnable {
    private EquipPower equipPower;
    private SoilEnvirenmentSimulatorConnector connector;

    public EquipController(EquipPower equipPower) {
        this.equipPower = equipPower;
        connector = new SoilEnvirenmentSimulatorConnector();
    }

    @Override
    public void run() {
        System.out.println("Thread: equip controller starts running! ");
        while (true) {
            try {
                if ((equipPower.getSprinklerPower() + equipPower.getHeaterPower()) > 0) {
                    adjustTempAndHumidity();
                    System.out.println("Equip is running! ");
                    Thread.sleep(1000);
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }


    public synchronized String adjustTempAndHumidity() {
        int tempUpVal = 0;
        int humidityUpVal = 0;

        if (equipPower.getHeaterPower() == 1) {
            tempUpVal = 1;
        } else if (equipPower.getHeaterPower() == 0) {
            tempUpVal = 0;
        }

        if (equipPower.getSprinklerPower() == 1) {
            humidityUpVal = 2;
        } else if (equipPower.getSprinklerPower() == 0) {
            humidityUpVal = 0;
        }

        return connector.adjust(tempUpVal, humidityUpVal);
    }

}
