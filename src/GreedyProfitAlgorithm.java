
import java.util.ArrayList;
import java.util.List;

/**
 * Greedy Algorithm (Profit-based)
 * Time Complexity: O(n^2) where n is number of jobs
 * Space Complexity: O(n)
 */
public class GreedyProfitAlgorithm extends AbstractJobSequencing {
    
    @Override
    public AlgorithmResult solve(List<Job> jobs) {
        long startTime = System.currentTimeMillis();
        AlgorithmResult result = new AlgorithmResult();
        
        // TODO: Implement Greedy Profit Algorithm
        // Steps:
        // 1. Sort jobs by profit in descending order
        // 2. Initialize time slots array with size = max deadline
        // 3. For each job, find a free slot from its deadline backwards
        // 4. If slot found, assign job to that slot and add to result
        // 5. Jobs not assigned are added to unselected list
        
        if (!validateJobs(jobs)) return result;
        
        sortByProfitDescending(jobs);
        
        int maxDeadline = findMaxDeadline(jobs);
        Job[] slots = new Job[maxDeadline + 1];
        boolean[] filled = new boolean[maxDeadline + 1];
        
        List<Job> selected = new ArrayList<>();
        
        for (Job job : jobs) {
            for (int slot = Math.min(maxDeadline, job.getDeadline()); slot >= 1; slot--) {
                if (!filled[slot]) {
                    filled[slot] = true;
                    slots[slot] = job;
                    selected.add(job);
                    break;
                }
            }
        }
        
        // Calculate total profit
        int totalProfit = 0;
        for (Job job : selected) {
            totalProfit += job.getProfit();
        }
        
        List<Job> unselected = new ArrayList<>(jobs);
        unselected.removeAll(selected);
        
        result.setSelectedJobs(selected);
        result.setUnselectedJobs(unselected);
        result.setTotalProfit(totalProfit);
        result.setExecutionTime(System.currentTimeMillis() - startTime);
        return result;
    }
    
    @Override
    public String getAlgorithmName() {
        return "Greedy Algorithm (Profit-based)";
    }
    
    @Override
    public String getTimeComplexity() {
        return "O(n²)";
    }
    
    @Override
    public String getSpaceComplexity() {
        return "O(n)";
    }
}