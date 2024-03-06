import java.util.List;

/**
 * This interface stores the results of a shortest path search to be used when calling for the
 * shortestPath.
 * @param <T>
 */
public interface ShortestPathInterface<T> {

    /**
     * Constructor to store the shortest Path
     */
    /*
     * storeShortestPath(String walkingTime, String path, double cost){
     * this.walkingTime = walkingTime;
     * this.path = path;
     * this.cost = cost;
     * }
     */

    /**
     * Getter method for the path (stored as a list of buildings along the path)
     * @return arrayList of buildings that make up the path
     */
    public List<String> path();

    /**
     * Getter method for the list of walking times of the path segments (the time it takes to
     * walk from one building to the next)
     * @return arrayList of walking times
     */
    public List<Double> times();

    /**
     * Getter method for the total path cost as the estimated time it takes to walk from the
     * start to the destination building.
     * @return the total path cost as a time
     */
    public double totalPathCost();

}
