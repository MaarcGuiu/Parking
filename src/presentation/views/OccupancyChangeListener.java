package presentation.views;

/**
 * The interface Occupancy change listener.
 */
public interface OccupancyChangeListener {
    /**
     * On occupancy changed.
     *
     * @param newData the new data
     */
    void onOccupancyChanged(int[] newData);
}