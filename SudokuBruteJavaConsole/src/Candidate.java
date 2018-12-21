/**
 * Sodoku Brute: A program that solves sodoku puzzles.
 * @author Cameron Maberto
 * <p>
 *     Solves a sodoku puzzle with a brute force style algorithm.
 *     Tests every possible complete board from the given board recursively.
 *     Stops working after it finds a complete board.
 *     Algorithm prototyped and proofed in Python
 * </p>
 * <p>
 *     Candidate class handles the possible values and solved boxes.
 *     Candidate class can either represent a solved box or an unsolved box.
 * </p>
 */

public class Candidate {

    public int [] candidates;
    public int size;
    public boolean solved;

    /**
     * Unsolved constructor for Candidate class.
     * <p>
     *     When no value is provided, the box is considered unsolved.
     *     The box will be given the values 1-9 that could possibly appear in this box in the puzzle.
     *     Impossible values will be removed during solving.
     * </p>
     */
    public Candidate() {
        candidates = new int[]{1,2,3,4,5,6,7,8,9};
        size = 9;
        solved = false;
    }

    /**
     * Solved constructor for Candidate class.
     * <p>
     *     When a value is provided, the box is considered solved.
     * </p>
     * @param value - the value that is the solution to this Sodoku space.
     */
    public Candidate(int value) {
        candidates = new int[]{value};
        size = 1;
        solved = true;
    }

    /**
     * Copy constructor for Candidate class.
     * <p>
     *     Copies values of another Candidate object.
     *     Needed for copying an entire board for a recursive call.
     * </p>
     * @param c - Candidate object that will be copied
     */
    public Candidate(Candidate c) {
        candidates = new int[c.size];
        System.arraycopy(c.candidates, 0, candidates, 0, c.size);
        size = c.size;
        solved = c.solved;
    }

    /**
     * A boolean function for testing if a number is a candidate for a given box.
     * <p>
     *     Linearly searches the candidate array for the value.
     *     Returns true as soon as the value is found, false otherwise.
     * </p>
     * @param num - an int specifying the number we are testing if it is in the candidates.
     * @return - a boolean specifying whether or not num is in the candidates array.
     */
    private boolean contains(int num) {
        for(int i = 0; i < size; ++i) {
            if(candidates[i] == num)
                return true;
        }
        return false;
    }

    /**
     * A function for removing a number from the candidate list.
     * <p>
     *     If the provided number is a candidate, remove the value and decrease the size by one.
     *     If not, do not do anything.
     * </p>
     * @param num - the number we are trying to remove from the candidate array.
     */
    public void remove(int num) {
        if(this.contains(num)) {
            int [] newCandidates = new int[size - 1];
            int index = 0;
            for (int i = 0; i < size; ++i) {
                if(candidates[i] != num) {
                    newCandidates[index] = candidates[i];
                    ++index;
                }
            }
            candidates  = newCandidates;
            size = size - 1;
        }
    }

}
