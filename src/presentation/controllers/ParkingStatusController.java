package presentation.controllers;

import business.ParkingStatusManager;
import business.SimulationParkingStatusManager;
import business.model.CancelledReservation;
import business.model.Slot;

import java.util.List;

public class ParkingStatusController {

    private static  ParkingStatusManager parkingStatusManager;
    private SimulationParkingStatusManager simulationManager;
    private Thread simulationThread;

    public ParkingStatusController() {
        parkingStatusManager = new ParkingStatusManager();
    }
    public void startSimulation(Runnable refreshView) {
        if (simulationThread == null || !simulationThread.isAlive()) {
            simulationManager = new SimulationParkingStatusManager(refreshView);
            simulationThread = new Thread(simulationManager);
            simulationThread.start();
        }
    }

    public void stopSimulation() {
        if (simulationManager != null) {
            simulationManager.stop();
            simulationManager = null;
            simulationThread = null;
        }
    }

    public List<Slot> getAllSlots() {
        return parkingStatusManager.getAllSlots();
    }

    public boolean cancelSlot(int slotId) {
        return parkingStatusManager.cancelSlot(slotId);
    }

    public boolean createCancelledReservation(int slotId, int userId, String vehiclePlate) {
        return parkingStatusManager.createCancelledReservation(slotId, userId, vehiclePlate);
    }

    public int setUserNewReservationSlot(int slotId, String vehiclePlate) {
        return parkingStatusManager.setUserNewReservationSlot(slotId, vehiclePlate);
    }

    public static boolean getFreeUnbookedSlots() {
        return parkingStatusManager.getFreeUnbookedSlots();
    }

    public Slot getSlot(int slotId) {
        return parkingStatusManager.getSlot(slotId);
    }
}
