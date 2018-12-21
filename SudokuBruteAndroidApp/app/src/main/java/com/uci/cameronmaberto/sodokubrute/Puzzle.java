package com.uci.cameronmaberto.sodokubrute;

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
 *     Puzzle class handles the setting up and copying of a board.
 *     Puzzle class holds the board as a 2 dimmensional array of Candidate objects.
 * </p>
 */

public class Puzzle {

    public Candidate[][] board = new Candidate[9][9];

    /**
     * Default constructor for a Puzzle.
     * <p>
     *     Builds a puzzle of all unsolved spaces needed for the copy function.
     *     New values may be copied in later for a copy constructor.
     * </p>
     */
    public Puzzle() {
        for(int r = 0; r < 9; ++r) {
            for (int c = 0; c < 9; ++c) {
                board[r][c] = new Candidate();
            }
        }
    }

    /**
     * The constructor for a puzzle.
     * <p>
     *     The 2-dimensional integer array will be converted to a 2-dimensional array of candidates.
     *     A zero in the array will be considered an unsolved box and will remain a default candidate.
     *     A non-zero in the array will be considered a solved box and will be replaced as a solved candidate.
     * </p>
     * @param newPuzzle - The 2-dimensional integer array that will be made into the current puzzle.
     */
    public Puzzle(int[][] newPuzzle) {
        for(int r = 0; r < 9; ++r) {
            for(int c = 0; c < 9; ++c) {
                if(newPuzzle[r][c] != 0) {
                    board[r][c] = new Candidate(newPuzzle[r][c]);
                }
                else {
                    board[r][c] = new Candidate();
                }
            }
        }
    }

    /**
     * A function that copies a puzzle.
     * <p>
     *     Builds a new puzzle and uses the Candidate class copy constructor to duplicate all its boxes.
     * </p>
     * @return - returns a new puzzle that is a deep copy of the current puzzle.
     */
    public Puzzle copy() {
        Puzzle p = new Puzzle();
        for(int r = 0; r < 9; ++r) {
            for(int c = 0; c < 9; ++c) {
                p.board[r][c] = new Candidate(board[r][c]);
            }
        }
        return p;
    }

    /**
     * A function that prints the current board to the standard output.
     * <p>
     *     The board is printed with horizontal and vertical lines dividing each sector for easy reading.
     *     Unsolved spaces are replaces with a * character to represent an empty space.
     *     Solved spaces are represented with their solved value.
     * </p>
     */
    public void display() {
        for(int r = 0; r < 9; ++r) {
            if(r == 3 || r == 6)
                System.out.println("---------------------");
            for(int c = 0; c < 9; ++c) {
                if(c == 3 || c == 6)
                    System.out.print("| ");
                if(board[r][c].solved) {
                    System.out.print(board[r][c].candidates[0] + " ");
                }
                else {
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
    }

}
