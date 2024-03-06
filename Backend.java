import java.io.File;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.NoSuchElementException;

/*
 * This backend takes a graph filled with buildings as a construction parameter
 * and performs operations that can be accessed by the frontend.
 */
public class Backend implements BackendInterface {

	/*
	 * The graph that contains the buildings and paths between them.
	 */
	private DijkstraGraph<String, Double> buildingGraph;

	/*
	 * The total walkingTime of the buildingGraph.
	 */
	private double totalWalkingTime;

	/*
	 * Reads data from a DOT file and inserts the data into a graph data structure that
	 * implements the GraphADT interface.
	 * @param file the DOT file to read data from
	 */
	public void readFile (String file) throws IllegalArgumentException, NullPointerException,
			FileNotFoundException {
		// check that file isn't null
		if (file == null) {
			throw new NullPointerException("File is null");
		}
		//check that file is in the correct format
		if (!file.endsWith(".dot")) {
			throw new IllegalArgumentException("File is in an incorrect format");
		}
		// create a new graph for this instance of backend and intialize walkinTime to 0
		this.buildingGraph = new DijkstraGraph<>(new PlaceholderMap<>());
		this.totalWalkingTime = 0;
		// read from the provided dot file
		File graphFile = new File(file);
		Scanner scanner = new Scanner(graphFile);
		String line = "";
		// update the graph to match the data in each valid line of the dot file
		while (scanner.hasNextLine()) {
			line = scanner.nextLine().trim();
			// if line isn't one with the data we want skip it
			if (line.trim().endsWith("{") || line.trim().endsWith("}") || line.isEmpty()) {
				continue;
			}
			// split string into two between the edge character -> building1 and building2+time
			String[] split1 = line.split("--");
			// trim to get id of white space
			String building1 = split1[0].trim();
			// split again to seperate building2 and walking time
			String[] split2 = split1[1].split("=");
			// isolate the building
			String building2 = split2[0].substring(0, split2[0].length() - 8).trim();
			// isolate the walking time
			String walkTime = split2[1].substring(0, split2[1].length() - 2);
			// convert the walking time String into a double to pass into an edge
			double walkingTime = Double.valueOf(walkTime);
			//get rid of quotes
			building1 = building1.substring(1, building1.length() - 1);
			building2 = building2.substring(1, building2.length() -1);
			// insert two nodes associated with the buildings
			this.buildingGraph.insertNode(building1);
			this.buildingGraph.insertNode(building2);
			//insert two edges for the buildings involved since it is an undirected edge
			this.buildingGraph.insertEdge(building1, building2, walkingTime);
			this.buildingGraph.insertEdge(building2, building1, walkingTime);
			// update total wlaking time
			this.totalWalkingTime += walkingTime;
		}

	}

	/*
	 * Method that returns the shortest path from a starting point to a destination in
	 * the form of an arrayList of String (String being the names of the buildings)
	 * @param start starting building as a String
	 * @param destination destination building as a String
	 * @return an arrayList of String (Strings being buildings)
	 */
	public ShortestPath getShortestPath (String start, String destination) throws NoSuchElementException {
		// create and retrun a shortestPath object to frontend where they can access its fields
		ShortestPath shortestPath = null;
		try {
			shortestPath = new ShortestPath(this.buildingGraph, start, destination);
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException(e.getMessage());
		}

		return shortestPath;
	}

	/*
	 * Method that returns statistics about the dataset and includes the number of nodes
	 * (buildings), the number of edges, and the total walking time (sum of weights) for
	 * all edges in the graph.
	 * @param buildingGraph the graph that represents all the buildings in the dataset
	 * @return String of statistics of the dataset
	 */
	public String getStatistics () {
		// if statement for integration testing with a non-existent file
		if (this.buildingGraph == null) {
			return "Dataset is empty.";
		}
		// create and return a string that displays the statistics of the dataset for frontend
		String statistics = "Dataset Statistics:";
		statistics += "\nNumber of Buildings: " + buildingGraph.getNodeCount();
		statistics += "\nNumber of Paths Connecting Buildings: " + (buildingGraph.getEdgeCount() / 2);
		statistics += "\nTotal Walking Time: " + this.totalWalkingTime;
		return statistics;
	}

	/*
	 * Main method to run code.
	 */
	public static void main (String[] args) {
		Backend backend = new Backend();
		Scanner scanner = new Scanner(System.in);
		Frontend frontend = new Frontend(backend, scanner);
		frontend.beginLoop();
	}

}