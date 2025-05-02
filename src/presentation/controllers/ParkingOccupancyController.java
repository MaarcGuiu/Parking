package presentation.controllers;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ParkingOccupancyController {
    private final ParkingOccupancyManager occupancyManager;
    private ScheduledExecutorService scheduler;
    private volatile int[] currentOccupancy = new int[60];

    public ParkingOccupancyController(ParkingOccupancyManager occupancyManager) {
        this.occupancyManager = occupancyManager;
    }

    @PostConstruct
    public void init() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        // Programa la actualización cada minuto con un retraso inicial de 0 segundos
        scheduler.scheduleAtFixedRate(this::updateOccupancyData, 0, 1, TimeUnit.MINUTES);
    }

    @PreDestroy
    public void cleanup() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }

    public void updateOccupancyData() {
        try {
            int[] newData = occupancyManager.calculateLast60MinutesOccupancy();
            this.currentOccupancy = newData;
            System.out.println("Datos de ocupación actualizados: " + LocalDateTime.now());
        } catch (Exception e) {
            System.err.println("Error actualizando datos: " + e.getMessage());
        }
    }

    public int[] getCurrentOccupancy() {
        return currentOccupancy.clone(); // Devuelve copia para evitar modificaciones externas
    }
}
