package business;
import business.model.VehicleEntry;
import business.model.VehicleEvent;
import persistence.LogsSqlDao;
import presentation.views.OccupancyChangeListener;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ParkingOccupancyManager {
    private final LogsSqlDao repository = new LogsSqlDao();
    private static final List<OccupancyChangeListener> listeners = new ArrayList<>();

    public ParkingOccupancyManager() {
    }

    /**
     * Calcula la ocupación por minuto para los últimos 60 minutos
     * @return Array donde cada posición representa un minuto (0 = hace 60 min, 59 = ahora)
     */
    public int[] calculateLast60MinutesOccupancy() throws SQLException {
        int[] occupancyPerMinute = new int[60];
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sixtyMinutesAgo = now.minusMinutes(60);

        if (repository != null) {
            // Obtener todos los eventos de los últimos 60 minutos
            List<VehicleEvent> events = repository.getVehicleEventsLast60Minutes();

            // También necesitamos los vehículos que entraron antes pero aún no han salido
            List<VehicleEntry> activeBefore = repository.getActiveVehicleEntries();

            // Inicializar con los vehículos que ya estaban dentro
            int currentOccupancy = (int) activeBefore.stream()
                    .filter(entry -> entry.getEntryTime().isBefore(sixtyMinutesAgo))
                    .count();

            // Procesar minuto a minuto
            for (int i = 0; i < 60; i++) {
                LocalDateTime currentMinuteStart = sixtyMinutesAgo.plusMinutes(i);
                LocalDateTime currentMinuteEnd = currentMinuteStart.plusMinutes(1);

                // Procesar eventos que ocurrieron en este minuto
                for (VehicleEvent event : events) {
                    if (event.getTimestamp().isAfter(currentMinuteStart) &&
                            !event.getTimestamp().isAfter(currentMinuteEnd)) {

                        if ("entry".equals(event.getAction())) {
                            currentOccupancy++;
                        } else if ("leave".equals(event.getAction())) {
                            currentOccupancy--;
                        }
                    }
                }

                occupancyPerMinute[i] = currentOccupancy;
            }
        }


//        public static void addListener(OccupancyChangeListener listener) {
//            listeners.add(listener);
//        }
//
//        private static void notifyListeners(int[] occupancy) {
//            for (OccupancyChangeListener l : listeners) {
//                l.onOccupancyChanged(occupancy);
//            }
//        }




        return occupancyPerMinute;
    }
}