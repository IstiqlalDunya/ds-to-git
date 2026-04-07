import java.util.Scanner;
import java.util.List;

/**
 * Utility class for validating user input
 */
public class InputValidator {
    
    private Scanner scanner;
    
    public InputValidator() {
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Gets integer input with validation
     * 
     * @param prompt Message to display
     * @param min Minimum allowed value
     * @param max Maximum allowed value
     * @return Validated integer
     */
    public int getIntInput(String prompt, int min, int max) {
        int input = 0;
        boolean valid = false;
        
        while (!valid) {
            System.out.print(prompt);
            try {
                input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    valid = true;
                } else {
                    System.out.println("Error: Please enter a number between " + min + " and " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid input. Please enter a valid number.");
            }
        }
        
        return input;
    }
    
    /**
     * Gets string input with validation
     * 
     * @param prompt Message to display
     * @param allowEmpty Whether empty string is allowed
     * @return Validated string
     */
    public String getStringInput(String prompt, boolean allowEmpty) {
        String input = "";
        boolean valid = false;
        
        while (!valid) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            
            if (!allowEmpty && input.isEmpty()) {
                System.out.println("Error: Input cannot be empty.");
            } else {
                valid = true;
            }
        }
        
        return input;
    }
    
    /**
     * Gets file path input with validation
     * 
     * @param prompt Message to display
     * @return Validated file path
     */
    public String getFilePathInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    /**
     * Validates job list
     * 
     * @param jobs List of jobs to validate
     * @return true if valid
     */
    public boolean validateJobs(List<Job> jobs) {
        if (jobs == null || jobs.isEmpty()) {
            System.out.println("Error: No jobs available. Please load or generate data first.");
            return false;
        }
        
        for (Job job : jobs) {
            if (job.getDeadline() <= 0) {
                System.out.println("Error: Job " + job.getId() + " has invalid deadline.");
                return false;
            }
            if (job.getProfit() < 0) {
                System.out.println("Error: Job " + job.getId() + " has negative profit.");
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Closes the scanner
     */
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}