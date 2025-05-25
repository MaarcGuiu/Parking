package presentation.controllers;

import business.ParkingOccupancyManager;
import presentation.views.AdminMenuView;
import presentation.views.OccupancyChangeListener;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * The type Parking occupancy controller.
 */
public class ParkingOccupancyController {
    private final ParkingOccupancyManager occupancyManager;
    private ScheduledExecutorService scheduler;
    private volatile int[] currentOccupancy = new int[60];

    /**
     * Instantiates a new Parking occupancy controller.
     *
     * @param occupancyManager the occupancy manager
     */
    public ParkingOccupancyController(ParkingOccupancyManager occupancyManager) {
        this.occupancyManager = occupancyManager;
        init();
    }

    /**
     * Init.
     */
    public void init() {
        cleanup();
        scheduler = Executors.newSingleThreadScheduledExecutor();
        // Programa la actualización cada minuto con un retraso inicial de 0 segundos
        scheduler.scheduleAtFixedRate(this::updateOccupancyData, 0, 1, TimeUnit.MINUTES);
    }


    /**
     * Cleanup.
     */
    public void cleanup() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }

    private final List<OccupancyChangeListener> listeners = new ArrayList<>();

    /**
     * Add occupancy change listener.
     *
     * @param listener the listener
     */
    public void addOccupancyChangeListener(OccupancyChangeListener listener) {
        listeners.add(listener);
    }

    /**
     * Remove occupancy change listener.
     *
     * @param listener the listener
     */
    public void removeOccupancyChangeListener(OccupancyChangeListener listener) {
        listeners.remove(listener);
    }

    private void notifyOccupancyChanged() {
        for (OccupancyChangeListener listener : listeners) {
            listener.onOccupancyChanged(currentOccupancy);
        }
    }

    /**
     * Update occupancy data.
     */
    public void updateOccupancyData() {
        if (occupancyManager != null) {
            try {
                int[] newData = occupancyManager.calculateLast60MinutesOccupancy();

                this.currentOccupancy = newData;
                notifyOccupancyChanged();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get current occupancy int [ ].
     *
     * @return the int [ ]
     */
    public int[] getCurrentOccupancy() {
        return currentOccupancy.clone();
    }

}
