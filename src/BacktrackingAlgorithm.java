
import java.util.ArrayList;
import java.util.List;

/**
 * Backtracking Algorithm
 * Time Complexity: O(2^n) exponential
 * Space Complexity: O(n)
 */
public class BacktrackingAlgorithm extends AbstractJobSequencing {
    
    private List<Job> bestSolution;
    private int bestProfit;
    private List<Job> allJobs;
    
    @Override
    public AlgorithmResult solve(List<Job> jobs) {
        long startTime = System.currentTimeMillis();
        AlgorithmResult result = new AlgorithmResult();
        
        if (!validateJobs(jobs)) return result;
        
        this.allJobs = jobs;
        this.bestSolution = new ArrayList<>();
        this.bestProfit = 0;
        
        // Start backtracking
        backtrack(new ArrayList<>(), 0, 0);
        
        // Prepare result
        List<Job> selectedJobs = new ArrayList<>(bestSolution);
        List<Job> unselected = new ArrayList<>(jobs);
        unselected.removeAll(selectedJobs);
        
        result.setSelectedJobs(selectedJobs);
        result.setUnselectedJobs(unselected);
        result.setTotalProfit(bestProfit);
        result.setExecutionTime(System.currentTimeMillis() - startTime);
        return result;
    }
    
    // Helper method for backtracking
    private void backtrack(List<Job> currentSelection, int currentProfit, int index) {
        // Base case: all jobs considered
        if (index >= allJobs.size()) {
            // Check if current selection is feasible
            if (isFeasible(currentSelection) && currentProfit > bestProfit) {
                bestProfit = currentProfit;
                bestSolution = new ArrayList<>(currentSelection);
            }
            return;
        }
        
        // Option 1: Exclude current job
        backtrack(currentSelection, currentProfit, index + 1);
        
        // Option 2: Include current job
        Job job = allJobs.get(index);
        currentSelection.add(job);
        if (isFeasible(currentSelection)) {
            backtrack(currentSelection, currentProfit + job.getProfit(), index + 1);
        }
        currentSelection.remove(currentSelection.size() - 1);
    }
    
    // Check if a set of jobs is feasible (all can be completed by deadlines)
    private boolean isFeasible(List<Job> jobs) {
        if (jobs.isEmpty()) return true;
        
        // Sort by deadline
        List<Job> sorted = new ArrayList<>(jobs);
        sorted.sort((j1, j2) -> Integer.compare(j1.getDeadline(), j2.getDeadline()));
        
        int time = 1;
        for (Job job : sorted) {
            if (time > job.getDeadline()) {
                return false;
            }
            time++;
        }
        return true;
    }
    
    @Override
    public String getAlgorithmName() {
        return "Backtracking Algorithm";
    }
    
    @Override
    public String getTimeComplexity() {
        return "O(2^n)";
    }
    
    @Override
    public String getSpaceComplexity() {
        return "O(n)";
    }
}