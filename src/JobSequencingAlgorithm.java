import java.util.List;

/**
 * Interface for all job sequencing algorithms
 */
public interface JobSequencingAlgorithm {
    
    /**
     * Solves the job sequencing problem
     * @param jobs List of jobs to schedule
     * @return AlgorithmResult containing selected jobs, total profit, and unselected jobs
     */
    AlgorithmResult solve(List<Job> jobs);
    
    /**
     * Gets the name of the algorithm
     * @return Algorithm name
     */
    String getAlgorithmName();
    
    /**
     * Gets the time complexity of the algorithm
     * @return Time complexity in Big-O notation
     */
    String getTimeComplexity();
    
    /**
     * Gets the space complexity of the algorithm
     * @return Space complexity in Big-O notation
     */
    String getSpaceComplexity();
}