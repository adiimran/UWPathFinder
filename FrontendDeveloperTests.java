// --== CS400 File Header Information ==--
// Name: Ahmad Adi Imran Zuraidi
// Email: zuraidi@wisc.edu
// Group and Team: E39
// Group TA: Nicholas Russell
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.Test;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

public class FrontendDeveloperTests {

    private Frontend frontend;

    /**
     * This method tests the beginLoop method of FrontendInterface
     */
    @Test
    public void testWrongInput() {
        String input = "5\n4\n";
        TextUITester tester = new TextUITester(input);
        Frontend frontend = new Frontend(new Backend(), new Scanner(input));
        frontend.beginLoop();
        String output = tester.checkOutput();
        assertTrue(output.contains("Invalid option."));
    }

    /**\
     * This method tests the loadData method of FrontendInterface
     */
    @Test
    public void testLoadDataHandler() {
        String filename = "src/campus.dot";
        TextUITester tester = new TextUITester(filename + "\n4\n");
        Frontend frontend = new Frontend(new Backend(), new Scanner(System.in));
        frontend.loadDataHandler();
        String output = tester.checkOutput();
        assertTrue(output.contains("Successfully loaded " + filename + "!"));
    }

    /**
     * This method tests the loadStatistics method of FrontendInterface
     */
    @Test
    public void testLoadStatistics() {
        TextUITester tester = new TextUITester("1\nsrc/campus.dot\n2\n4\n");
        Frontend frontend = new Frontend(new Backend(), new Scanner(System.in));
        frontend.beginLoop();
        String output = tester.checkOutput();
        assertTrue(output.contains("Number of Buildings: 160\n" +
                "Number of Paths Connecting Buildings: 508\n" +
                "Total Walking Time: 110675.49999999997"));
    }

    /**
     * This method tests the loadShortestPath method of FrontendInterface
     */
    @Test
    public void testLoadShortestPath() {
        TextUITester tester = new TextUITester("1\nsrc/campus.dot\n3\nMemorial Union\nUnion South\n4\n");
        Frontend frontend = new Frontend(new Backend(), new Scanner(System.in));
        frontend.beginLoop();
        String output = tester.checkOutput();
        assertTrue(output.contains("Shortest path from Memorial Union to Union South:\n" +
                "    [Memorial Union, Science Hall, Music Hall, Law Building, X01, Luther Memorial Church, Noland Hall, Meiklejohn House, Computer Sciences and Statistics, Union South]"));
    }

    /**
     * This method tests the exit method of FrontendInterface
     */
    @Test
    public void testExitCommand() {
        TextUITester tester = new TextUITester("");
        Frontend frontend = new Frontend(new Backend(), new Scanner(System.in));
        frontend.exit();
        String output = tester.checkOutput();
        assertTrue(output.contains("Goodbye!"));
    }

    /**
     * This method is the integration test method for readFile of the backend implementation
     */
    @Test
    public void testReadFileIntegration(){
        String filename = "src/campus.dot";
        TextUITester tester = new TextUITester( filename + "\n4\n");
        Backend backend = new Backend();
        Frontend frontend = new Frontend(backend, new Scanner(System.in));
        frontend.loadDataHandler();
        String output = tester.checkOutput();
        assertTrue(output.contains("Successfully loaded " + filename + "!"));
    }

    /**
     * This method is the integration test method for getShortestPath of the backend implementation
     */
    @Test
    public void testGetShortestPathIntegration(){
        TextUITester tester = new TextUITester("1\nsrc/campus.dot\n3\nMemorial Union\nUnion South\n4\n");
        Backend backend = new Backend();
        Frontend frontend = new Frontend(backend,new Scanner(System.in));
        frontend.beginLoop();
        String output = tester.checkOutput();
        assertTrue(output.contains("Shortest path from Memorial Union to Union South:\n" +
                "    [Memorial Union, Science Hall, Music Hall, Law Building, X01, Luther Memorial Church, Noland Hall, Meiklejohn House, Computer Sciences and Statistics, Union South]"));
    }
}
