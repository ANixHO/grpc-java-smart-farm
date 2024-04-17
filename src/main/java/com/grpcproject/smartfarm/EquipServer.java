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
        equipServer.start(equipPower);
        equipServer.blockUntilShutdown();

    }

    private void start(EquipPower equipPower) throws IOException {
        int port = 50061;
        server = ServerBuilder.forPort(port)
                .addService(new EquipServerImpl(equipPower))
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
        private final EquipPower equipPower;

        public EquipServerImpl(EquipPower equipPower) {
            super();
            this.equipPower = equipPower;
        }

        public void equipPowerControl(EquipControlServiceProto.EquipPowerReq equipPowerReq, StreamObserver<EquipControlServiceProto.EquipPowerRes> resObserver) {

            String equipName = equipPowerReq.getEquipName();
            if (equipName.equals("heater")) {
                equipPower.changeHeaterPower();

            }
            if (equipName.equals("sprinkler")) {
                equipPower.changeSprinklerPower();
            }

            EquipControlServiceProto.EquipPowerRes res = EquipControlServiceProto.EquipPowerRes
                    .newBuilder()
                    .setEquipPowerStatus(
                            "Heater is " + equipPower.statusCodeToString(equipPower.getHeaterPower()) +
                                    " ; Sprinkler is " + equipPower.statusCodeToString((equipPower.getSprinklerPower())))
                    .build();

            resObserver.onNext(res);
            resObserver.onCompleted();
        }


        public void equipStatus(EquipControlServiceProto.EquipStatusReq req, StreamObserver<EquipControlServiceProto.EquipStatusRes> resObserver) {
            String resEquipName = req.getEquipStatusRequest();
            int resEquipStatusCode = -1;

            if (resEquipName.equals("heater")) {
                resEquipStatusCode = equipPower.getHeaterPower();
            }

            if (resEquipName.equals("sprinkler")) {
                resEquipStatusCode = equipPower.getSprinklerPower();
            }

            EquipControlServiceProto.EquipStatusRes res = EquipControlServiceProto.EquipStatusRes
                    .newBuilder()
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

    public String statusCodeToString(int statusCode) {
        if (statusCode == 0) {
            return "off";
        } else {
            return "on";
        }
    }
}

class EquipController implements Runnable {
    private final EquipPower equipPower;
    private final SoilEnvirenmentSimulatorConnector connector;

    public EquipController(EquipPower equipPower) {
        this.equipPower = equipPower;
        connector = new SoilEnvirenmentSimulatorConnector();
    }

    @Override
    public void run() {
        System.out.println("Thread: equip controller starts running! ");
        while (true) {
            System.out.print("");

            if ((equipPower.getSprinklerPower() + equipPower.getHeaterPower()) > 0) {
                String adjustResult = adjustTempAndHumidity();
                System.out.println(adjustResult);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }


    public String adjustTempAndHumidity() {
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
