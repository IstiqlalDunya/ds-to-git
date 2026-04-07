import java.util.ArrayList;
import java.util.List;

/**
 * Dynamic Programming Algorithm
 * Time Complexity: O(n * maxDeadline)
 * Space Complexity: O(n * maxDeadline)
 */
public class DynamicProgrammingAlgorithm extends AbstractJobSequencing {
    
    @Override
    public AlgorithmResult solve(List<Job> jobs) {
        long startTime = System.currentTimeMillis();
        AlgorithmResult result = new AlgorithmResult();
        
        if (!validateJobs(jobs)) return result;
        
        // Sort by deadline
        List<Job> sortedJobs = new ArrayList<>(jobs);
        sortedJobs.sort((j1, j2) -> Integer.compare(j1.getDeadline(), j2.getDeadline()));
        
        int maxDeadline = findMaxDeadline(sortedJobs);
        int n = sortedJobs.size();
        
        // DP table
        int[][] dp = new int[n + 1][maxDeadline + 1];
        boolean[][] selected = new boolean[n + 1][maxDeadline + 1];
        
        // Fill DP table
        for (int i = 1; i <= n; i++) {
            Job job = sortedJobs.get(i - 1);
            for (int t = 1; t <= maxDeadline; t++) {
                dp[i][t] = dp[i - 1][t]; // Exclude job
                
                if (t <= job.getDeadline()) {
                    int includeProfit = dp[i - 1][t - 1] + job.getProfit();
                    if (includeProfit > dp[i][t]) {
                        dp[i][t] = includeProfit;
                        selected[i][t] = true;
                    }
                }
            }
        }
        
        // Backtrack to find selected jobs
        List<Job> selectedJobs = new ArrayList<>();
        int time = maxDeadline;
        for (int i = n; i >= 1; i--) {
            if (selected[i][time]) {
                selectedJobs.add(sortedJobs.get(i - 1));
                time--;
            }
        }
        
        // Reverse to maintain original order
        java.util.Collections.reverse(selectedJobs);
        
        // Calculate total profit
        int totalProfit = dp[n][maxDeadline];
        
        List<Job> unselected = new ArrayList<>(jobs);
        unselected.removeAll(selectedJobs);
        
        result.setSelectedJobs(selectedJobs);
        result.setUnselectedJobs(unselected);
        result.setTotalProfit(totalProfit);
        result.setExecutionTime(System.currentTimeMillis() - startTime);
        return result;
    }
    
    @Override
    public String getAlgorithmName() {
        return "Dynamic Programming Algorithm";
    }
    
    @Override
    public String getTimeComplexity() {
        return "O(n × maxDeadline)";
    }
    
    @Override
    public String getSpaceComplexity() {
        return "O(n × maxDeadline)";
    }
}