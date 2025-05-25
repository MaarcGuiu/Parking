package business;

import java.sql.SQLException;

public class SimulationManager implements Runnable {
    private boolean running;
    private ParkingStatusManager parkingStatusManager;

    public SimulationManager()  {
    }

    @Override
    public void run() {
        running = true;

        try {
            parkingStatusManager = new ParkingStatusManager();
        } catch (SQLException e) {
            parkingStatusManager = null;
        }

        if (parkingStatusManager != null) {
            long frequency = parkingStatusManager.calculateFrequency() * 1000L;
            while (running) {
                try {
                    Thread.sleep(frequency);
                    if (!parkingStatusManager.getFreeUnbookedSlots()) {
                        parkingStatusManager.simulateEntry();
                    } else {
                        if (parkingStatusManager.calculateEntryOrExit()){
                            parkingStatusManager.simulateEntry();
                        } else {
                            parkingStatusManager.simulateExit();
                        }
                    }
                } catch (InterruptedException e) {
                    running = false;
                } catch (SQLException e) {
                }
            }
        }
    }
    public void stop() {
        running = false;
    }

}
