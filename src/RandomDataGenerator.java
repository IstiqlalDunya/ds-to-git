import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generates random job data for testing
 */
public class RandomDataGenerator {
    
    private static final String[] JOB_PREFIXES = {"ASSIGN", "LAB", "PROJ", "TASK", "HW"};
    private static final String[] DESCRIPTIONS = {
        "Complete programming assignment",
        "Submit lab report",
        "Prepare presentation",
        "Write documentation",
        "Test and debug",
        "Review literature",
        "Analyze data",
        "Design algorithm",
        "Implement feature",
        "Conduct research"
    };
    
    private Random random;
    
    public RandomDataGenerator() {
        this.random = new Random();
    }
    
    public RandomDataGenerator(long seed) {
        this.random = new Random(seed);
    }
    
    /**
     * Generates random jobs with specified parameters
     * 
     * @param count Number of jobs to generate
     * @param minDeadline Minimum deadline value
     * @param maxDeadline Maximum deadline value
     * @param minProfit Minimum profit value
     * @param maxProfit Maximum profit value
     * @return List of randomly generated jobs
     */
    public List<Job> generateRandomJobs(int count, int minDeadline, int maxDeadline, 
                                        int minProfit, int maxProfit) {
        List<Job> jobs = new ArrayList<>();
        
        for (int i = 1; i <= count; i++) {
            String prefix = JOB_PREFIXES[random.nextInt(JOB_PREFIXES.length)];
            String id = prefix + "_" + i;
            
            int deadline = minDeadline + random.nextInt(maxDeadline - minDeadline + 1);
            int profit = minProfit + random.nextInt(maxProfit - minProfit + 1);
            
            String description = DESCRIPTIONS[random.nextInt(DESCRIPTIONS.length)] + 
                                " (Job " + i + ")";
            
            Job job = new Job(id, deadline, profit, description);
            jobs.add(job);
        }
        
        return jobs;
    }
    
    /**
     * Generates random jobs with default ranges
     * 
     * @param count Number of jobs to generate
     * @return List of randomly generated jobs
     */
    public List<Job> generateRandomJobs(int count) {
        return generateRandomJobs(count, 1, 10, 10, 100);
    }
    
    /**
     * Generates random jobs with increasing complexity
     * 
     * @param count Number of jobs to generate
     * @param difficultyLevel 1-5 where 5 is hardest (larger deadlines and profits)
     * @return List of randomly generated jobs
     */
    public List<Job> generateRandomJobsWithDifficulty(int count, int difficultyLevel) {
        int maxDeadline = 5 + (difficultyLevel - 1) * 2;
        int maxProfit = 50 + (difficultyLevel - 1) * 30;
        
        return generateRandomJobs(count, 1, maxDeadline, 10, maxProfit);
    }
}