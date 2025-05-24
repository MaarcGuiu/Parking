package business;
import java.sql.SQLException;
public class SimulationParkingStatusManager implements Runnable {
    private boolean running;
    private final Runnable viewRefreshTask;
    private ParkingStatusManager parkingStatusManager;
    public SimulationParkingStatusManager(Runnable viewRefreshTask) {
        this.viewRefreshTask = viewRefreshTask;
    }

    @Override
    public void run() {
        running = true;
        parkingStatusManager = new ParkingStatusManager();
        long frequency = parkingStatusManager.calculateFrequency() * 1000L;

        while (running) {
            try {
                Thread.sleep(frequency);
                viewRefreshTask.run();
            } catch (InterruptedException e) {
                System.out.println("There was an interruption of the thread");
                running = false;
            } //catch (SQLException e) {
                //throw new RuntimeException(e);
            //}
        }
    }

    public void stop() {
        running = false;
    }


}
