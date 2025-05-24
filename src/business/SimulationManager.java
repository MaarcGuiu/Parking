package business;

import java.sql.SQLException;

public class SimulationManager implements Runnable {
    private boolean running;
    private ParkingStatusManager parkingStatusManager;

    public SimulationManager() {
    }

    @Override
    public void run() {
        running = true;
        parkingStatusManager = new ParkingStatusManager();
        long frequency = parkingStatusManager.calculateFrequency() * 1000L;
        while (running) {
            try {
                Thread.sleep(frequency);
                if (!parkingStatusManager.getFreeUnbookedSlots()) {
                    parkingStatusManager.simulateEntry(); System.out.println("ENTRA VEHICLE\n");
                } else {
                    if (parkingStatusManager.calculateEntryOrExit()){
                        parkingStatusManager.simulateEntry(); System.out.println("ENTRA VEHICLE\n");
                    } else {
                        parkingStatusManager.simulateExit();System.out.println("SALE UN VEHICULO");
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("There was an interruption of the thread");
                running = false;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void stop() {
        running = false;
    }

}
