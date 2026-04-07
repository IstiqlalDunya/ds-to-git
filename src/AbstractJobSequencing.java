import java.util.Collections;
import java.util.List;

/**
 * Abstract base class for job sequencing algorithms
 */
public abstract class AbstractJobSequencing implements JobSequencingAlgorithm {
    
    /**
     * Validates input jobs
     * @param jobs List of jobs to validate
     * @return true if valid, false otherwise
     */
    protected boolean validateJobs(List<Job> jobs) {
        if (jobs == null || jobs.isEmpty()) {
            System.err.println("Error: No jobs provided");
            return false;
        }
        
        for (Job job : jobs) {
            if (job.getDeadline() <= 0) {
                System.err.println("Error: Invalid deadline for job " + job.getId());
                return false;
            }
            if (job.getProfit() < 0) {
                System.err.println("Error: Negative profit for job " + job.getId());
                return false;
            }
        }
        return true;
    }
    
    /**
     * Sorts jobs by profit in descending order (common for many algorithms)
     * @param jobs List of jobs to sort
     */
    protected void sortByProfitDescending(List<Job> jobs) {
        Collections.sort(jobs, (j1, j2) -> Integer.compare(j2.getProfit(), j1.getProfit()));
    }
    
    /**
     * Finds the maximum deadline among all jobs
     * @param jobs List of jobs
     * @return Maximum deadline
     */
    protected int findMaxDeadline(List<Job> jobs) {
        int maxDeadline = 0;
        for (Job job : jobs) {
            if (job.getDeadline() > maxDeadline) {
                maxDeadline = job.getDeadline();
            }
        }
        return maxDeadline;
    }
}