
import java.io.Serializable;

/**
 * Represents a job/assignment with deadline and profit
 */
public class Job implements Serializable, Comparable<Job> {
    private String id;
    private int deadline;
    private int profit;
    private String description;
    
    // Constructors
    public Job(String id, int deadline, int profit) {
        this.id = id;
        this.deadline = deadline;
        this.profit = profit;
        this.description = "";
    }
    
    public Job(String id, int deadline, int profit, String description) {
        this.id = id;
        this.deadline = deadline;
        this.profit = profit;
        this.description = description;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public int getDeadline() {
        return deadline;
    }
    
    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }
    
    public int getProfit() {
        return profit;
    }
    
    public void setProfit(int profit) {
        this.profit = profit;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public int compareTo(Job other) {
        // Sort by profit in descending order
        return Integer.compare(other.profit, this.profit);
    }
    
    @Override
    public String toString() {
        return String.format("Job %s | Deadline: %d | Profit: %d | %s", 
                            id, deadline, profit, description);
    }
    
    public String toFileString() {
        return id + "," + deadline + "," + profit + "," + description;
    }
}