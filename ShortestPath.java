import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/*
 * This class stores the result of a shortest path search to be used when calling for the
 * shortestPath.
 */
public class ShortestPath implements ShortestPathInterface {

    /*
     * The graph that the shortest path search is conducted in.
     */
    private DijkstraGraph<String, Double> buildingGraph;

    /*
     * The data item in the starting node for the path.
     */
    private String start;

    /*
     * The data item in the destination node for the path.
     */
    private String end;

    /*
     * Constructor to intialize the shortestPath's graph, start node, and end node.
     */
    public ShortestPath(DijkstraGraph<String, Double> buildingGraph, String start, String end) {
        // if the start node is not within the graph throw a NoSuchElementException
        if (!buildingGraph.containsNode(start)) {
            throw new NoSuchElementException("Start node doesn't exist in the graph");
        }
        // if the start node is not within the graph throw a NoSuchElementException
        if (!buildingGraph.containsNode(end)) {
            throw new NoSuchElementException("End node doesn't exist in the graph");
        }
        // set the fields to the appropriate data values
        this.buildingGraph = buildingGraph;
        this.start = start;
        this.end = end;
    }

    /*
     * Getter method for the path (stored as a list of buildings along the path)
     * @return arrayList of buildings that make up the path.
     */
    public List<String> path() {
        // return the list of buildings accessed from the graph's shortestPathData method
        List<String> path = buildingGraph.shortestPathData(start, end);
        return path;
    }

    /*
     * Getter method for the list of walking times of the path segments (the time it takes to
     * walk from one building to the next).
     * @return arrayList of walking times
     */
    public List<Double> times() {
        // set curretn search node to be the entire shortest path
        DijkstraGraph<String, Double>.SearchNode currNode = buildingGraph.computeShortestPath(start, end);
        ArrayList<Double> times = new ArrayList<>();
        // initialize the total cost to the cost of the shortest path
        double totalPathCost = currNode.cost;
        double walkingTime = 0.0;
        // add each walking time from each edge to its associated place in the arrayList
        while (totalPathCost != 0.0) {
            walkingTime = totalPathCost - currNode.predecessor.cost;
            times.add(0, walkingTime);
            totalPathCost -= walkingTime;
            currNode = currNode.predecessor;
        }

        return times;
    }

    /*
     * Getter method for the total path cost as the estimated time it takes to walk from the
     * start to the destination building.
     * @return the total path cost as a time
     */
    public double totalPathCost() {
        //return the to totalCost accessed by the graph's shortestPathCost method
        double totalPathCost = buildingGraph.shortestPathCost(start, end);
        return totalPathCost;
    }

}
