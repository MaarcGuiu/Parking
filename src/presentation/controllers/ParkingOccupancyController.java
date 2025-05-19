package presentation.controllers;

import business.ParkingOccupancyManager;
import presentation.views.AdminMenuView;
import presentation.views.OccupancyChangeListener;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ParkingOccupancyController {
    private final ParkingOccupancyManager occupancyManager;
    private ScheduledExecutorService scheduler;
    private volatile int[] currentOccupancy = new int[60];

    public ParkingOccupancyController(ParkingOccupancyManager occupancyManager) {
        this.occupancyManager = occupancyManager;
        init();
    }

    public void init() {
        cleanup();
        scheduler = Executors.newSingleThreadScheduledExecutor();
        // Programa la actualización cada minuto con un retraso inicial de 0 segundos
        scheduler.scheduleAtFixedRate(this::updateOccupancyData, 0, 1, TimeUnit.MINUTES);
    }


    public void cleanup() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }

    private final List<OccupancyChangeListener> listeners = new ArrayList<>();

    public void addOccupancyChangeListener(OccupancyChangeListener listener) {
        listeners.add(listener);
    }

    public void removeOccupancyChangeListener(OccupancyChangeListener listener) {
        listeners.remove(listener);
    }

    private void notifyOccupancyChanged() {
        for (OccupancyChangeListener listener : listeners) {
            listener.onOccupancyChanged(currentOccupancy);
        }
    }

    public void updateOccupancyData() {
        try {
            int[] newData = occupancyManager.calculateLast60MinutesOccupancy();

            notifyOccupancyChanged(); // Notificar a los listeners

            System.out.println("Datos de ocupación actualizados: " + LocalDateTime.now());
        } catch (Exception e) {
            System.err.println("Error actualizando datos: " + e.getMessage());
        }
    }

    public int[] getCurrentOccupancy() {
        return currentOccupancy.clone(); // Devuelve copia para evitar modificaciones externas
    }

}
