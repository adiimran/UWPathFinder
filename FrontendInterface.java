public interface FrontendInterface {

    //Constructor Placeholder
    //Backend backend;
    //Scanner input;
    /*
     *
     * public IndividualFrontendInterface(Backend backendReference, Scanner inputReference){
     *         backend = backendReference;
     *         input = intputReference;
     * }
     *
     */

    /**
     * Starts the main command loop for the user, resetting back to this loop after
     * the completion of any other loop/method.
     */
    public void beginLoop();

    /**
     * Prompts the user to enter a DOT file with the scanner
     */
    public void loadDataHandler();

    /**
     * Shows the user statistics about the data set including the number of buildings,
     * the number of edges, and the total walking time in the graph.
     */
    public void loadStatistics();

    /**
     * Prompts the user to enter a starting location and then a destination location.
     * Returns the shortest path between those buildings, including all buildings on
     * the way, the estimated walking time for each segment, and the total time it
     * takes to walk the path.
     */
    public void loadShortestPath();

    /**
     * Exits the program
     */
    public void exit();

}