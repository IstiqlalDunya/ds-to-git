import java.util.ArrayList;
import java.util.List;

/**
 * Greedy Algorithm with Disjoint Set (Union-Find)
 * Time Complexity: O(n log n) or O(n α(n)) with path compression
 * Space Complexity: O(n)
 */
public class GreedyUnionFindAlgorithm extends AbstractJobSequencing {
    
    // Disjoint Set class for efficient slot finding
    private class DisjointSet {
        private int[] parent;
        
        public DisjointSet(int n) {
            parent = new int[n + 1];
            for (int i = 0; i <= n; i++) {
                parent[i] = i;
            }
        }
        
        public int find(int x) {
            if (parent[x] == x) {
                return x;
            }
            // Path compression
            parent[x] = find(parent[x]);
            return parent[x];
        }
        
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                parent[rootX] = rootY;
            }
        }
    }
    
    @Override
    public AlgorithmResult solve(List<Job> jobs) {
        long startTime = System.currentTimeMillis();
        AlgorithmResult result = new AlgorithmResult();
        
        if (!validateJobs(jobs)) return result;
        
        sortByProfitDescending(jobs);
        
        int maxDeadline = findMaxDeadline(jobs);
        DisjointSet ds = new DisjointSet(maxDeadline);
        Job[] slots = new Job[maxDeadline + 1];
        List<Job> selected = new ArrayList<>();
        
        for (Job job : jobs) {
            int availableSlot = ds.find(Math.min(maxDeadline, job.getDeadline()));
            
            if (availableSlot > 0) {
                slots[availableSlot] = job;
                selected.add(job);
                ds.union(availableSlot, availableSlot - 1);
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
        return "Greedy Algorithm with Disjoint Set (Union-Find)";
    }
    
    @Override
    public String getTimeComplexity() {
        return "O(n log n) or O(n α(n))";
    }
    
    @Override
    public String getSpaceComplexity() {
        return "O(n)";
    }
}