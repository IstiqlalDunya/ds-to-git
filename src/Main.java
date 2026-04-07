import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Main application class for Job Sequencing Problem
 * 
 * @author Group Assignment
 * @version 1.0
 */
public class Main {
    
    private static List<Job> currentJobs;
    private static Map<Integer, JobSequencingAlgorithm> algorithms;
    private static InputValidator inputValidator;
    private static DataLoader dataLoader;
    private static RandomDataGenerator randomGenerator;
    
    public static void main(String[] args) {
        System.out.println("+=======================================================+");
        System.out.println("| UNIVERSITY ASSIGNMENT / LAB SUBMISSION JOB SEQUENCING |");
        System.out.println("|     UECS2453 Problem Solving with Data Structures     |");
        System.out.println("+=======================================================+\n");
        
        initialize();
        
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = inputValidator.getIntInput("Select option: ", 1, 6);
            
            switch (choice) {
                case 1:
                    loadDataFromFile();
                    break;
                case 2:
                    generateRandomData();
                    break;
                case 3:
                    displayJobs();
                    break;
                case 4:
                    runAlgorithms();
                    break;
                case 5:
                    running = false;
                    System.out.println("Exiting application. Goodbye!");
                    break;
            }
            
            if (running && choice != 4 && choice != 5) {
                System.out.println("\nPress Enter to continue...");
                inputValidator.getStringInput("", true);
            }
        }
        
        inputValidator.close();
    }
    
    /**
     * Initialize application components
     */
    private static void initialize() {
        inputValidator = new InputValidator();
        dataLoader = new DataLoader();
        randomGenerator = new RandomDataGenerator();
        currentJobs = new ArrayList<>();
        
        // Initialize algorithms
        algorithms = new HashMap<>();
        algorithms.put(1, new GreedyProfitAlgorithm());
        algorithms.put(2, new GreedyUnionFindAlgorithm());
        algorithms.put(3, new DynamicProgrammingAlgorithm());
        algorithms.put(4, new BacktrackingAlgorithm());
        
        System.out.println("System initialized with 4 algorithms.");
    }
    
    /**
     * Display main menu
     */
    private static void displayMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Load data from file");
        System.out.println("2. Generate random data");
        System.out.println("3. Display current jobs");
        System.out.println("4. Run algorithms");
        System.out.println("5. Exit");
    }
    
    /**
     * Load data from external file
     */
    private static void loadDataFromFile() {
        System.out.println("\n=== Load Data from File ===");
        System.out.println("1. Load from text file");
        System.out.println("2. Create sample data file");
        
        int choice = inputValidator.getIntInput("Select option: ", 1, 2);
        
        if (choice == 1) {
            String filePath = inputValidator.getFilePathInput("Enter file path: ");
            try {
                currentJobs = dataLoader.loadFromFile(filePath);
                System.out.println("Successfully loaded " + currentJobs.size() + " jobs.");
                displayJobsSummary();
            } catch (IOException e) {
                System.err.println("Error loading file: " + e.getMessage());
                System.err.println("Please check the file path and format.");
            }
        } else if (choice == 2) {
            String filePath = inputValidator.getFilePathInput("Enter file path to create sample data: ");
            try {
                dataLoader.createSampleFile(filePath);
                System.out.println("Sample data file created successfully at: " + filePath);
                System.out.println("You can now load this file using option 1.");
            } catch (IOException e) {
                System.err.println("Error creating sample file: " + e.getMessage());
            }
        }
    }
    
    /**
     * Generate random data
     */
    private static void generateRandomData() {
        System.out.println("\n=== Generate Random Data ===");
        System.out.println("1. Default range (1-10 deadlines, 10-100 profits)");
        System.out.println("2. Custom ranges");
        System.out.println("3. By difficulty level (1-5)");
        
        int choice = inputValidator.getIntInput("Select option: ", 1, 3);
        
        int count = inputValidator.getIntInput("Number of jobs to generate: ", 1, 100);
        
        try {
            if (choice == 1) {
                currentJobs = randomGenerator.generateRandomJobs(count);
            } else if (choice == 2) {
                int minDeadline = inputValidator.getIntInput("Minimum deadline: ", 1, 100);
                int maxDeadline = inputValidator.getIntInput("Maximum deadline: ", minDeadline, 100);
                int minProfit = inputValidator.getIntInput("Minimum profit: ", 1, 1000);
                int maxProfit = inputValidator.getIntInput("Maximum profit: ", minProfit, 1000);
                
                currentJobs = randomGenerator.generateRandomJobs(count, minDeadline, maxDeadline, 
                                                                minProfit, maxProfit);
            } else {
                int difficulty = inputValidator.getIntInput("Difficulty level (1-5): ", 1, 5);
                currentJobs = randomGenerator.generateRandomJobsWithDifficulty(count, difficulty);
            }
            
            System.out.println("Successfully generated " + currentJobs.size() + " random jobs.");
            displayJobsSummary();
            
        } catch (Exception e) {
            System.err.println("Error generating random data: " + e.getMessage());
        }
    }
    
    /**
     * Display all current jobs
     */
    private static void displayJobs() {
        System.out.println("\n=== Current Jobs ===");
        
        if (currentJobs == null || currentJobs.isEmpty()) {
            System.out.println("No jobs available. Please load or generate data first.");
            return;
        }
        
        System.out.printf("%-10s %-10s %-10s %s\n", "Job ID", "Deadline", "Profit", "Description");
        System.out.println("--------------------------------------------------------------");
        
        for (Job job : currentJobs) {
            System.out.printf("%-10s %-10d %-10d %s\n", 
                            job.getId(), job.getDeadline(), job.getProfit(), job.getDescription());
        }
        
        System.out.println("\nTotal Jobs: " + currentJobs.size());
        
        // Calculate statistics
        int maxDeadline = 0;
        int maxProfit = 0;
        int totalProfit = 0;
        
        for (Job job : currentJobs) {
            maxDeadline = Math.max(maxDeadline, job.getDeadline());
            maxProfit = Math.max(maxProfit, job.getProfit());
            totalProfit += job.getProfit();
        }
        
        System.out.println("\nStatistics:");
        System.out.println("  - Max Deadline: " + maxDeadline);
        System.out.println("  - Max Profit: " + maxProfit);
        System.out.println("  - Total Potential Profit: " + totalProfit);
    }
    
    /**
     * Run selected algorithms
     */
    private static void runAlgorithms() {
        if (!inputValidator.validateJobs(currentJobs)) {
            return;
        }
        
        System.out.println("\n=== Run Algorithms ===");
        System.out.println("Available algorithms:");
        System.out.println("1. " + algorithms.get(1).getAlgorithmName());
        System.out.println("2. " + algorithms.get(2).getAlgorithmName());
        System.out.println("3. " + algorithms.get(3).getAlgorithmName());
        System.out.println("4. " + algorithms.get(4).getAlgorithmName());
        System.out.println("5. Run all algorithms");
        
        int choice = inputValidator.getIntInput("Select algorithm: ", 1, 5);
        
        if (choice == 5) {
            runAllAlgorithms();
        } else {
            runSingleAlgorithm(choice);
        }
    }
    
    /**
     * Run a single algorithm
     */
    private static void runSingleAlgorithm(int algorithmNumber) {
        JobSequencingAlgorithm algorithm = algorithms.get(algorithmNumber);
        
        System.out.println("\n=== Running " + algorithm.getAlgorithmName() + " ===");
        System.out.println("Time Complexity: " + algorithm.getTimeComplexity());
        System.out.println("Space Complexity: " + algorithm.getSpaceComplexity());
        System.out.println("\nProcessing...\n");
        
        try {
            AlgorithmResult result = algorithm.solve(currentJobs);
            
            System.out.println("========== RESULTS ==========");
            System.out.println(result);
            
        } catch (Exception e) {
            System.err.println("Error running algorithm: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Run all algorithms for comparison
     */
    private static void runAllAlgorithms() {
        System.out.println("\n===== Running All Algorithms =====");
        System.out.println("This will compare all algorithms on the same dataset.\n");
        
        if (currentJobs.size() > 30) {
            System.out.println("Warning: You have " + currentJobs.size() + " jobs.");
            System.out.println("Some algorithms (especially Backtracking) may be slow with large datasets.");
            String proceed = inputValidator.getStringInput("Continue? (y/n): ", true);
            if (!proceed.equalsIgnoreCase("y")) {
                return;
            }
        }
        
        System.out.println("\n" + "=".repeat(60));
        
        for (int i = 1; i <= 4; i++) {
            JobSequencingAlgorithm algorithm = algorithms.get(i);
            System.out.println("\n" + "=".repeat(60));
            System.out.println("Algorithm " + i + ": " + algorithm.getAlgorithmName());
            System.out.println("Time Complexity: " + algorithm.getTimeComplexity());
            System.out.println("-".repeat(60));
            
            try {
                long startTime = System.currentTimeMillis();
                AlgorithmResult result = algorithm.solve(currentJobs);
                long executionTime = System.currentTimeMillis() - startTime;
                
                System.out.println("Selected Jobs: " + result.getSelectedJobs().size());
                System.out.println("Total Profit: " + result.getTotalProfit());
                System.out.println("Execution Time: " + executionTime + " ms");
                System.out.println("\nSelected Job Details:");
                for (Job job : result.getSelectedJobs()) {
                    System.out.println("  " + job.getId() + " | Deadline: " + job.getDeadline() + 
                                     " | Profit: " + job.getProfit());
                }
                
            } catch (Exception e) {
                System.err.println("Error running algorithm " + i + ": " + e.getMessage());
            }
            
            System.out.println("-".repeat(60));
        }
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Comparison Complete!");
    }
    
    /**
     * Display summary of current jobs
     */
    private static void displayJobsSummary() {
        if (currentJobs != null && !currentJobs.isEmpty()) {
            System.out.println("\nJob Summary:");
            System.out.println("  - Total Jobs: " + currentJobs.size());
            System.out.println("  - Deadline Range: " + getDeadlineRange());
            System.out.println("  - Profit Range: " + getProfitRange());
        }
    }
    
    /**
     * Get deadline range of current jobs
     */
    private static String getDeadlineRange() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        
        for (Job job : currentJobs) {
            min = Math.min(min, job.getDeadline());
            max = Math.max(max, job.getDeadline());
        }
        
        return min + " to " + max;
    }
    
    /**
     * Get profit range of current jobs
     */
    private static String getProfitRange() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        
        for (Job job : currentJobs) {
            min = Math.min(min, job.getProfit());
            max = Math.max(max, job.getProfit());
        }
        
        return min + " to " + max;
    }
}