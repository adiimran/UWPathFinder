// --== CS400 File Header Information ==--
// Name: Ahmad Adi Imran Zuraidi
// Email: zuraidi@wisc.edu
// Group and Team: E39
// Group TA: Nicholas Russell
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
public class Frontend implements FrontendInterface{
    private Backend backend;
    private Scanner input;

    /**
     * Constructor for the Frontend class.
     *
     * @param BackendReference Reference to the Backend object.
     * @param inputReference Scanner object for reading user input.
     */
    public Frontend(Backend BackendReference, Scanner inputReference){
        this.backend = BackendReference;
        this.input = inputReference;
    }

    /**
     * Begins the main loop of the application, presenting the user with menu options.
     */
    @Override
    public void beginLoop(){
        System.out.println("Menu:");
        System.out.println("    (1) Load data");
        System.out.println("    (2) Display statistics");
        System.out.println("    (3) Find shortest path");
        System.out.println("    (4) Quit");
        System.out.print("Enter an option: ");
        int option = input.nextInt();
        input.nextLine();
        if (option == 1){
            loadDataHandler();
        }
        else if (option == 2){
            loadStatistics();
        }
        else if (option == 3){
            loadShortestPath();
        }
        else if (option == 4){
            exit();
        }
        else{
            System.out.println("Invalid option.");
            beginLoop();
        }
    }

    /**
     * Handles the loading of data from a file.
     */
    @Override
    public void loadDataHandler(){
        System.out.print("Enter the filename to load: ");
        String filename = input.next();

        try {
            backend.readFile(filename);
            System.out.println("Successfully loaded " + filename + "!");
            beginLoop();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
            beginLoop();
        }
        catch (IllegalArgumentException e) {
            System.out.println("File is in incorrect format");
            beginLoop();
        }
    }

    /**
     * Displays statistics gathered by the backend.
     */
    @Override
    public void loadStatistics(){
        String statistics = backend.getStatistics();
        System.out.println(statistics);
        beginLoop();
    }

    /**
     * Handles the finding and displaying of the shortest path between two locations.
     */
    @Override
    public void loadShortestPath(){
        System.out.print("Enter starting location: ");
        String start = input.nextLine();
        System.out.print("Enter destination location: ");
        String destination = input.nextLine();

        ShortestPath path = backend.getShortestPath(start, destination);
        if (path != null){
            System.out.println("Shortest path from " + start + " to " + destination + ":");
            System.out.println("    " + path.path());
            System.out.println("Total walking time: " + path.totalPathCost() + " seconds.");
            beginLoop();
        }
        else {
            System.out.println("Locations not found, please try again.");
            beginLoop();
        }
    }

    /**
     * Exits the application.
     */
    @Override
    public void exit(){
        System.out.println("Goodbye!");
    }

    /**
     * The main method to start the application.
     */
    public static void main (String[] args){
        Backend backend = new Backend();
        Scanner scnr = new Scanner(System.in);
        Frontend frontend = new Frontend(backend,scnr);
        frontend.beginLoop();
    }
}
