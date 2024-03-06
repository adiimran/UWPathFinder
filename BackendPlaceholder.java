import java.io.File;
import java.util.ArrayList;

public class BackendPlaceholder implements BackendInterface {

    @Override
    public void readFile(String file) throws IllegalArgumentException {
    }

    @Override
    public ShortestPath getShortestPath(String start, String destination) {
        System.out.println("Shortest path from " + start + " to " + destination + ":");
        return null;
    }

    @Override
    public String getStatistics() {
        return "Number of buildings: 0\nNumber of edges: 0\nTotal walking time: 0";
    }
}
