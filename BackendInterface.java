import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * This backend interface takes a graph filled with buildings as a construction parameter and
 * performs operations that can be accessed by the frontend.
 * @param <T>
 */
public interface BackendInterface<T> {

    /*

    Constructor this for this backendInterface takes an instance of the GraphADT:

    public IndividualBackendInterface (GraphADT<String, Double> buildingGraph) {
        this.buildingGraph = buildingGraph;
    }

     */

    /**
     * Reads data from a DOT file and inserts the data into a graph data structure that
     * implements the GraphADT interface
     * @param file the DOT file to read data from
     */
    public void readFile(String file) throws IllegalArgumentException, NullPointerException, FileNotFoundException;

    /**
     * Method that returns the shortest path from a starting point to a destination in the form
     * of an arrayList of String (String being the names of the buildings)
     * @param start starting building as a String
     * @param destination destination building as a String
     * @return an arrayList of String (Strings being buildings)
     */
    public ShortestPath getShortestPath (String start, String destination);

    /**
     * Method that returns statistics about the dataset and includes the number of nodes
     * (buildings), the number of edges, and the total walking time (sum of weights) for all
     * edges in the graph
     * @return String of statistics of the dataset
     */
    public String getStatistics ();
}
