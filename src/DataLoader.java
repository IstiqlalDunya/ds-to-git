import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading job data from external files
 */
public class DataLoader {
    
    /**
     * Loads jobs from a text file
     * File format: each line should contain: id,deadline,profit,description
     * Example: J1,3,60,Complete algorithm analysis
     * 
     * @param filePath Path to the file
     * @return List of jobs
     * @throws IOException if file cannot be read
     */
    public List<Job> loadFromFile(String filePath) throws IOException {
        List<Job> jobs = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                
                // Skip empty lines and comments
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                
                String[] parts = line.split(",");
                if (parts.length < 3) {
                    System.err.println("Warning: Line " + lineNumber + " has insufficient data. Skipping.");
                    continue;
                }
                
                try {
                    String id = parts[0].trim();
                    int deadline = Integer.parseInt(parts[1].trim());
                    int profit = Integer.parseInt(parts[2].trim());
                    String description = parts.length > 3 ? parts[3].trim() : "";
                    
                    Job job = new Job(id, deadline, profit, description);
                    jobs.add(job);
                    
                } catch (NumberFormatException e) {
                    System.err.println("Warning: Line " + lineNumber + " has invalid number format. Skipping.");
                }
            }
        }
        
        return jobs;
    }
    
    /**
     * Loads jobs from a CSV file (alternative format)
     * 
     * @param filePath Path to the CSV file
     * @return List of jobs
     * @throws IOException if file cannot be read
     */
    public List<Job> loadFromCSV(String filePath) throws IOException {
        // CSV format is the same as text format for simplicity
        return loadFromFile(filePath);
    }
    
    /**
     * Creates a sample data file
     * 
     * @param filePath Path where to create the sample file
     * @throws IOException if file cannot be written
     */
    public void createSampleFile(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("# Job Sequencing Problem - Sample Data\n");
            writer.write("# Format: JobID,Deadline,Profit,Description\n\n");
            writer.write("J1,3,60,Complete algorithm analysis assignment\n");
            writer.write("J2,1,100,Submit project proposal\n");
            writer.write("J3,2,20,Review literature\n");
            writer.write("J4,3,40,Prepare presentation slides\n");
            writer.write("J5,2,20,Test and debug code\n");
            writer.write("J6,4,80,Write final report\n");
            writer.write("J7,1,50,Create documentation\n");
            writer.write("J8,5,90,Implement UI components\n");
            writer.write("J9,3,70,Conduct user testing\n");
            writer.write("J10,4,30,Update references\n");
        }
    }
}