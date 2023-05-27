import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * ACO - Ant Colony Optimization Meta-heuristic
 *
 * Reference book: Ant Colony Optimization.
 * Authors: Marco Dorigo and Thomas Stützle
 * Links:
 *  -> https://mitpress.mit.edu/books/ant-colony-optimization
 *  -> http://www.aco-metaheuristic.org/
 *
 * This algorithm present the implementation of ACO for TSP problems.
 */
public class Program {

    protected Environment environment;
    protected Statistics statistics;

    public Program() {
    }

    public static void main(String[] args) throws Exception {
        Program program = new Program();
//        program.dataGenerate("eil51.tsp");
//        program.drawData(program.getEnvironment(), program.getStatistics());
        program.startSence(program);
        while (!program.getStatistics().nameFile().equalsIgnoreCase("")){
            String name = program.getStatistics().nameFile();
            switch (name) {
                case "test":
                    System.out.println("test");
                    break;
                case "532nodes.tsp":
                    program.dataGenerate("532nodes.tsp");
                    break;
                case "20nodes.tsp":
                    program.dataGenerate("20nodes.tsp");
                    break;
                case "198nodes.tsp":
                    program.dataGenerate("198nodes.tsp");
                    break;
                case "1291nodes.tsp":
                    program.dataGenerate("1291nodes.tsp");
                    break;
                case "51nodes.tsp":
                    program.dataGenerate("51nodes.tsp");
                    break;
                case "100nodes.tsp":
                    program.dataGenerate("100nodes.tsp");
                    break;
                case "318nodes.tsp":
                    program.dataGenerate("318nodes.tsp");
                    break;
                case "442nodes.tsp":
                    program.dataGenerate("442nodes.tsp");
                    break;
                case "1173nodes.tsp":
                    program.dataGenerate("1173node.tsp");
                    break;
                case "2392nodes.tsp":
                    program.dataGenerate("2392nodes.tsp");
                    break;
                case "783nodes.tsp":
                    program.dataGenerate("783nodes.tsp");
                    break;
            }
        }
    }

    public void dataGenerate(String name) throws Exception{
        String tspPath = (new File(".")).getCanonicalPath();
        tspPath = Paths.get(tspPath, "tsp").toAbsolutePath().toString();
        String tspFiles =  name;
        startApplication(tspPath, tspFiles);
    }

    // Main part of the algorithm
    public void startApplication(String path, String file) {
        System.out.println(statistics.nameFile() + "Name");

        // Create a TSP instance from file with .tsp extension
        Environment environment = new Environment(TspReader.getDistances(path, file));
//        Statistics statistics = new Statistics(file, environment, TspReader.getCoordinates(path, file));
        statistics.showData(file, environment, TspReader.getCoordinates(path, file));

        setEnvironment(environment);
        setStatistics(statistics);

        System.out.println(path);
        drawData(environment, statistics);
    }

    public void startSence(Program program) {
        statistics = new Statistics(program);
    }

    public void drawData(Environment environment, Statistics statistics){
        // Startup part
        environment.generateNearestNeighborList();
        environment.generateAntPopulation();
        environment.generateEnvironment();

        // Repeat the ants behavior by n times
        int n = 0;
        while(n < Parameters.iterationsMax) {
            environment.constructSolutions();
            environment.updatePheromone();
            statistics.calculateStatistics(n);
            n++;
        }
        try { Thread.sleep(3000); } catch (Exception ex) {}
        statistics.close();
        System.out.println("Finished");
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }
}
