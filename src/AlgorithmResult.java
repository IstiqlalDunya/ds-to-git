import java.util.ArrayList;
import java.util.List;

/**
 * Container class for algorithm results
 */
public class AlgorithmResult {
    private List<Job> selectedJobs;
    private List<Job> unselectedJobs;
    private int totalProfit;
    private long executionTime; // in milliseconds
    
    public AlgorithmResult() {
        this.selectedJobs = new ArrayList<>();
        this.unselectedJobs = new ArrayList<>();
        this.totalProfit = 0;
        this.executionTime = 0;
    }
    
    public List<Job> getSelectedJobs() {
        return selectedJobs;
    }
    
    public void setSelectedJobs(List<Job> selectedJobs) {
        this.selectedJobs = selectedJobs;
    }
    
    public List<Job> getUnselectedJobs() {
        return unselectedJobs;
    }
    
    public void setUnselectedJobs(List<Job> unselectedJobs) {
        this.unselectedJobs = unselectedJobs;
    }
    
    public int getTotalProfit() {
        return totalProfit;
    }
    
    public void setTotalProfit(int totalProfit) {
        this.totalProfit = totalProfit;
    }
    
    public long getExecutionTime() {
        return executionTime;
    }
    
    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== Algorithm Results =====\n");
        sb.append("Selected Jobs (").append(selectedJobs.size()).append("):\n");
        for (Job job : selectedJobs) {
            sb.append("  ").append(job).append("\n");
        }
        sb.append("Total Profit: ").append(totalProfit).append("\n");
        sb.append("Unselected Jobs (").append(unselectedJobs.size()).append("):\n");
        for (Job job : unselectedJobs) {
            sb.append("  ").append(job).append("\n");
        }
        sb.append("Execution Time: ").append(executionTime).append(" ms\n");
        return sb.toString();
    }
}