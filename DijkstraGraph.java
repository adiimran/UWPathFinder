// --== CS400 File Header Information ==--
// Name: Ahmad Adi Imran Zuraidi
// Email: zuraidi@wisc.edu
// Group and Team: E39
// Group TA: Nicholas Russell
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.PriorityQueue;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in its node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * Constructor that sets the map that the graph uses.
     * @param map the map that the graph uses to map a data object to the node
     *        object it is stored in
     */
    public DijkstraGraph(MapADT<NodeType, Node> map) {
        super(map);
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
        // check if the start and end nodes exist
        if (!nodes.containsKey(start) || !nodes.containsKey(end))
            throw new NoSuchElementException("no path found between these nodes");

        // create a map of visited nodes and a priority queue of nodes to visit
        MapADT<NodeType, Node> visited = new PlaceholderMap<>();

        // create a priority queue of nodes to visit
        PriorityQueue<SearchNode> queue = new PriorityQueue<>();

        // create a start node and add it to the queue
        SearchNode startNode = new SearchNode(nodes.get(start), 0.0, null);

        queue.add(startNode); // add the start node to the queue
        while (!queue.isEmpty()){
            SearchNode current = queue.remove();

            // skip this node if we have already visited it
            if (visited.containsKey(current.node.data)){
                continue;
            }

            // if we have reached the end node, return the current node
            if (current.node.data.equals(end)){
                visited.put(current.node.data, current.node);
                return current;
            }

            // add this node to the visited map
            visited.put(current.node.data, current.node);

            // add all of the current node's neighbors to the queue
            for (Edge edge : current.node.edgesLeaving){
                // check if we have already visited this node
                if (!visited.containsKey(edge.successor.data)){
                    queue.add(new SearchNode(edge.successor, edge.data.doubleValue() + current.cost, current));
                }
            }
        }
        throw new NoSuchElementException("no path found between these nodes");
    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        // check if the start and end nodes exist
        if (!nodes.containsKey(start) || !nodes.containsKey(end))
            throw new NoSuchElementException("no path found between these nodes");

        // initialize the list of data to return
        SearchNode pathNode = computeShortestPath(start, end);

        // create a list to store the data
        List<NodeType> pathData = new LinkedList<>();

        // add the data to the list
        while (pathNode != null){
            pathData.add(0, pathNode.node.data);
            pathNode = pathNode.predecessor;
        }
        return pathData;
    }

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        // check if the start and end nodes exist
        if (!nodes.containsKey(start) || !nodes.containsKey(end))
            throw new NoSuchElementException("no path found between these nodes");

        // initialize the list of data to return
        SearchNode pathNode = computeShortestPath(start, end);
        return pathNode.cost;
    }
    @Test
    public void testLectureExample() {
        DijkstraGraph<String, Integer> dijkstraGraph = new DijkstraGraph<>(new PlaceholderMap<>());
        dijkstraGraph.insertNode("A");
        dijkstraGraph.insertNode("B");
        dijkstraGraph.insertNode("D");
        dijkstraGraph.insertNode("E");
        dijkstraGraph.insertNode("F");
        dijkstraGraph.insertNode("G");
        dijkstraGraph.insertNode("H");
        dijkstraGraph.insertNode("I");
        dijkstraGraph.insertNode("L");
        dijkstraGraph.insertNode("M");

        dijkstraGraph.insertEdge("A", "B", 1);
        dijkstraGraph.insertEdge("A", "H", 8);
        dijkstraGraph.insertEdge("A", "M", 5);
        dijkstraGraph.insertEdge("B", "M", 3);
        dijkstraGraph.insertEdge("D", "A", 7);
        dijkstraGraph.insertEdge("D", "G", 2);
        dijkstraGraph.insertEdge("F", "G", 9);
        dijkstraGraph.insertEdge("G", "L", 7);
        dijkstraGraph.insertEdge("H", "B", 6);
        dijkstraGraph.insertEdge("H", "I", 2);
        dijkstraGraph.insertEdge("I", "D", 1);
        dijkstraGraph.insertEdge("I", "L", 5);
        dijkstraGraph.insertEdge("I", "H", 2);
        dijkstraGraph.insertEdge("M", "E", 3);
        dijkstraGraph.insertEdge("M", "F", 4);

        assertEquals(17, dijkstraGraph.shortestPathCost("D", "I"));
        assertEquals("[D, A, H, I]", dijkstraGraph.shortestPathData("D", "I").toString());
    }

    @Test
    public void testAnotherLectureExample() {
        DijkstraGraph<String, Integer> dijkstraGraph = new DijkstraGraph<>(new PlaceholderMap<>());
        dijkstraGraph.insertNode("A");
        dijkstraGraph.insertNode("B");
        dijkstraGraph.insertNode("D");
        dijkstraGraph.insertNode("E");
        dijkstraGraph.insertNode("F");
        dijkstraGraph.insertNode("G");
        dijkstraGraph.insertNode("H");
        dijkstraGraph.insertNode("I");
        dijkstraGraph.insertNode("L");
        dijkstraGraph.insertNode("M");

        dijkstraGraph.insertEdge("A", "B", 1);
        dijkstraGraph.insertEdge("A", "H", 8);
        dijkstraGraph.insertEdge("A", "M", 5);
        dijkstraGraph.insertEdge("B", "M", 3);
        dijkstraGraph.insertEdge("D", "A", 7);
        dijkstraGraph.insertEdge("D", "G", 2);
        dijkstraGraph.insertEdge("F", "G", 9);
        dijkstraGraph.insertEdge("G", "L", 7);
        dijkstraGraph.insertEdge("H", "B", 6);
        dijkstraGraph.insertEdge("H", "I", 2);
        dijkstraGraph.insertEdge("I", "D", 1);
        dijkstraGraph.insertEdge("I", "L", 5);
        dijkstraGraph.insertEdge("I", "H", 2);
        dijkstraGraph.insertEdge("M", "E", 3);
        dijkstraGraph.insertEdge("M", "F", 4);

        assertEquals(4, dijkstraGraph.shortestPathCost("A", "M"));
        assertEquals("[A, B, M]", dijkstraGraph.shortestPathData("A", "M").toString());
    }

    /**
     * Tests the shortestPathData method. It validates that the
     * cost of the shortest path between start and end nodes is correct.
     */
    @Test
    public void testSearchNodeCost() {
        // create a graph with 4 nodes and 5 edges
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());

        // adding nodes
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");
        graph.insertNode("I");
        graph.insertNode("L");
        graph.insertNode("M");

        //  adding edges
        graph.insertEdge("A", "B", 1);
        graph.insertEdge("A", "H", 8);
        graph.insertEdge("A", "M", 5);
        graph.insertEdge("B", "M", 3);
        graph.insertEdge("D", "A", 7);
        graph.insertEdge("D", "G", 2);
        graph.insertEdge("F", "G", 9);
        graph.insertEdge("G", "L", 7);
        graph.insertEdge("H", "B", 6);
        graph.insertEdge("H", "I", 2);
        graph.insertEdge("I", "D", 1);
        graph.insertEdge("I", "L", 5);
        graph.insertEdge("I", "H", 2);
        graph.insertEdge("M", "E", 3);
        graph.insertEdge("M", "F", 4);

        // compute the shortest path
        SearchNode current = (SearchNode) graph.computeShortestPath("D", "I");
        List<Double> pathData = new LinkedList<>();
        while (current != null) {
            pathData.add(0, current.cost);
            current = current.predecessor;
        }
        Assertions.assertEquals("[0.0, 7.0, 15.0, 17.0]", pathData.toString());
    }

    @Test
    public void testNoPathExists() {
        DijkstraGraph<String, Integer> dijkstraGraph = new DijkstraGraph<>(new PlaceholderMap<>());
        dijkstraGraph.insertNode("A");
        dijkstraGraph.insertNode("B");
        dijkstraGraph.insertNode("D");
        dijkstraGraph.insertNode("E");
        dijkstraGraph.insertNode("F");
        dijkstraGraph.insertNode("G");
        dijkstraGraph.insertNode("H");
        dijkstraGraph.insertNode("I");
        dijkstraGraph.insertNode("L");
        dijkstraGraph.insertNode("M");

        dijkstraGraph.insertEdge("A", "B", 1);
        dijkstraGraph.insertEdge("A", "H", 8);
        dijkstraGraph.insertEdge("A", "M", 5);
        dijkstraGraph.insertEdge("B", "M", 3);
        dijkstraGraph.insertEdge("D", "A", 7);
        dijkstraGraph.insertEdge("D", "G", 2);
        dijkstraGraph.insertEdge("F", "G", 9);
        dijkstraGraph.insertEdge("G", "L", 7);
        dijkstraGraph.insertEdge("H", "B", 6);
        dijkstraGraph.insertEdge("H", "I", 2);
        dijkstraGraph.insertEdge("I", "D", 1);
        dijkstraGraph.insertEdge("I", "L", 5);
        dijkstraGraph.insertEdge("I", "H", 2);
        dijkstraGraph.insertEdge("M", "E", 3);
        dijkstraGraph.insertEdge("M", "F", 4);

        assertThrows(NoSuchElementException.class, () -> dijkstraGraph.computeShortestPath("E", "A"));
    }

    /**
     * Tests the shortestPathCost and shortestPathData method. It tests the cost of the shortest path
     * is correct and the shortest path data is correct.
     */
    @Test
    public void testComplexGraphShortestPath() {
        // create a graph with 4 nodes and 5 edges
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());

        // adding nodes
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");
        graph.insertNode("I");
        graph.insertNode("J");
        graph.insertNode("K");
        graph.insertNode("L");

        // adding edges
        graph.insertEdge("A" , "B" ,3);
        graph.insertEdge("A" , "F" ,5);
        graph.insertEdge("A" , "I" ,8);
        graph.insertEdge("B" , "C" ,5);
        graph.insertEdge("B" , "D" ,1);
        graph.insertEdge("B" , "G" ,2);
        graph.insertEdge("B" , "I" ,3);
        graph.insertEdge("C" , "D" ,2);
        graph.insertEdge("C" , "E" ,7);
        graph.insertEdge("C" , "H" ,6);
        graph.insertEdge("C" , "J" ,2);
        graph.insertEdge("D" , "E" ,4);
        graph.insertEdge("D" , "G" ,3);
        graph.insertEdge("D" , "K" ,5);
        graph.insertEdge("E" , "A" ,6);
        graph.insertEdge("E" , "F" ,1);
        graph.insertEdge("E" , "H" ,2);
        graph.insertEdge("E" , "L" ,9);
        graph.insertEdge("F" , "G" ,2);
        graph.insertEdge("F" , "J" ,1);
        graph.insertEdge("G" , "H" ,3);
        graph.insertEdge("G" , "K" ,3);
        graph.insertEdge("H" , "F" ,4);
        graph.insertEdge("H" , "L" ,2);
        graph.insertEdge("I" , "C" ,7);
        graph.insertEdge("I" , "F" ,4);
        graph.insertEdge("I" , "J" ,5);
        graph.insertEdge("J" , "D" ,2);
        graph.insertEdge("J" , "G" ,6);
        graph.insertEdge("J" , "K" ,4);
        graph.insertEdge("K" , "E" ,8);
        graph.insertEdge("K" , "H" ,5);
        graph.insertEdge("K" , "L" ,6);
        graph.insertEdge("L" , "A" ,7);
        graph.insertEdge("L" , "B" ,3);

        //Checking the cost and data of the shortest path for several path
        Assertions.assertEquals(10.0, graph.shortestPathCost("A", "L"));
        Assertions.assertEquals("[A, B, G, H, L]", graph.shortestPathData("A", "L").toString());

        Assertions.assertEquals(3.0, graph.shortestPathCost("E", "G"));
        Assertions.assertEquals("[E, F, G]", graph.shortestPathData("E", "G").toString());

        Assertions.assertEquals(8.0, graph.shortestPathCost("L", "C"));
        Assertions.assertEquals("[L, B, C]", graph.shortestPathData("L", "C").toString());

        Assertions.assertEquals(10.0, graph.shortestPathCost("F", "B"));
        Assertions.assertEquals("[F, G, H, L, B]", graph.shortestPathData("F", "B").toString());

        Assertions.assertEquals(9.0, graph.shortestPathCost("K", "F"));
        Assertions.assertEquals("[K, E, F]", graph.shortestPathData("K", "F").toString());

    }
}
