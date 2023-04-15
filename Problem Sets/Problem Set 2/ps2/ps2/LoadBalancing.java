/**
 * Contains static routines for solving the problem of balancing m jobs on p processors
 * with the constraint that each processor can only perform consecutive jobs.
 */
public class LoadBalancing {

    /**
     * Checks if it is possible to assign the specified jobs to the specified number of processors such that no
     * processor's load is higher than the specified query load.
     *
     * @param jobSizes the sizes of the jobs to be performed
     * @param queryLoad the maximum load allowed for any processor
     * @param p the number of processors
     * @return true iff it is possible to assign the jobs to p processors so that no processor has more than queryLoad load.
     */
    public static boolean isFeasibleLoad(int[] jobSizes, int queryLoad, int p) {
        // Invalid case: If the jobSizes array is empty, should return false
        if (jobSizes.length == 0) {
            return false;
        } else if (p == 0) {
            return false;
        } else {
            int currentLoad = 0; //create a variable to store the current load
            int numOfProcessors = p; //create a variable to store number of processors left
            for (int i = 0; i < jobSizes.length; i++) {
                //This checks if the current processor can still fit more jobs
                if (currentLoad + jobSizes[i] <= queryLoad) {
                    currentLoad += jobSizes[i];
                //Invalid case: if there is a job that exceeds the query load
                } else if (jobSizes[i] > queryLoad) {
                    return false;
                } else {
                    //Otherwise, once load exceeds, then we decrease number of processors
                    //left and move on to the next processor.
                    numOfProcessors--;
                    currentLoad = jobSizes[i];
                }
            }

        return numOfProcessors >= 0 ? true : false;
        }
    }

    /**
     * Returns the minimum achievable load given the specified jobs and number of processors.
     *
     * @param jobSizes the sizes of the jobs to be performed
     * @param p the number of processors
     * @return the maximum load for a job assignment that minimizes the maximum load
     */
    public static int findLoad(int[] jobSizes, int p) {
        // Check for invalid cases, which are empty array, and p <= 0
        if (jobSizes.length == 0 || p <= 0) {
            return -1;
        } else {
            int numOfProcessors = p; //Variable to store the number of processors
            int numOfJobs = jobSizes.length; //variable to store number of jobs
            int minMaxLoad = 0; //minimum max Load that the processor is able to take
            int minLoad = 0; //Variable to allow for the splitting of array in half
            for (int i = 0; i < numOfJobs; i++) {
                //This is to get the maximum job sizes that can be taken
                minMaxLoad += jobSizes[i];
            }
            while (minLoad < minMaxLoad) {
                int midLoad = minLoad + (minMaxLoad - minLoad) / 2; //to split in half
                if (isFeasibleLoad(jobSizes, midLoad, numOfProcessors)) {
                    /*If the current midLoad is feasible, then we will take it as our 
                    current minMaxLoad and compare it further
                    */
                    minMaxLoad = midLoad;
                } else {
                    /*else we will continue checking at a higher amount of load as it
                    not being feasible means that not all processors are fulfilled
                    */
                    minLoad = midLoad + 1;
                }
            }
            return minMaxLoad;
        }
    }

    // These are some arbitrary testcases.
    public static int[][] testCases = {
            {1, 3, 5, 7, 9, 11, 10, 8, 6, 4},
            {67, 65, 43, 42, 23, 17, 9, 100},
            {4, 100, 80, 15, 20, 25, 30},
            {2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83},
            {7}
    };

    /**
     * Some simple tests for the findLoad routine.
     */
    public static void main(String[] args) {
        for (int p = 1; p < 30; p++) {
            System.out.println("Processors: " + p);
            for (int[] testCase : testCases) {
                System.out.println(findLoad(testCase, p));
            }
        }
    }
}
