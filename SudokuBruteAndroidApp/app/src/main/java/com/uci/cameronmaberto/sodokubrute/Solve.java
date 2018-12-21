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
 *     Solve class handles solving of the puzzle.
 * </p>
 */

public class Solve {

    public Puzzle solution = null;
    public int recursiveCalls = 0;

    /**
     * A function that removes the impossible candidates from the provided box based upon row.
     * <p>
     *     If a number shows up elsewhere in the row, it will be removed from the current box.
     * </p>
     * @param row - row of the current box we are removing candidates from.
     * @param col - column of the current box we are removing candidates from.
     * @param p - the puzzle that is being modified.
     */
    private void removeRowCandidates(int row, int col, Puzzle p) {
        for(int c = 0; c < 9; ++c) {
            if(c != col && p.board[row][c].solved) {
                p.board[row][col].remove(p.board[row][c].candidates[0]);
            }
        }
    }

    /**
     * A function that removes the impossible candidates from the provided box based upon column.
     * <p>
     *     If a number shows up elsewhere in the column, it will be removed from the current box.
     * </p>
     * @param row - row of the current box we are removing candidates from.
     * @param col - column of the current box we are removing candidates from.
     * @param p - the puzzle that is being modified.
     */
    private void removeColCandidates(int row, int col, Puzzle p) {
        for(int r = 0; r < 9; ++r) {
            if(r != row && p.board[r][col].solved) {
                p.board[row][col].remove(p.board[r][col].candidates[0]);
            }
        }
    }

    /**
     * A function that removes the impossible candidates from the provided box based upon sector.
     * <p>
     *     If a number shows up elsewhere in the sector, it will be removed from the current box.
     * </p>
     * @param row - row of the current box we are removing candidates from.
     * @param col - column of the current box we are removing candidates from.
     * @param p - the puzzle that is being modified.
     */
    private void removeSectorCandidates(int row, int col, Puzzle p) {
        int sectorOffsetR = (row / 3) * 3;
        int sectorOffsetC = (col / 3) * 3;
        for(int r = sectorOffsetR; r < sectorOffsetR + 3; ++r) {
            for(int c = sectorOffsetC; c < sectorOffsetC + 3; ++c) {
                if(r != row && c != col && p.board[r][c].solved) {
                    p.board[row][col].remove(p.board[r][c].candidates[0]);
                }
            }
        }
    }

    /**
     * A function that removes all the impossible candidates for a given box.
     * <p>
     *     Candidate box is originally the numbers 1-9.
     *     All values that show up in either the current column, row, or sector may not
     *     appear in the current box.
     *     Sets the current box to solved if there is only one candidate for the given space.
     * </p>
     * @param row - row of the current box we are removing candidates from.
     * @param col - col of the current box we are removing candidates from.
     * @param p - the puzzle that is being modified.
     */
    public void buildCandidates(int row, int col, Puzzle p) {
        removeRowCandidates(row, col, p);
        removeColCandidates(row, col, p);
        removeSectorCandidates(row, col, p);
        if(p.board[row][col].size == 1) {
            p.board[row][col].solved = true;
        }
    }

    /**
     * A boolean function checking if a puzzle is finished and correct
     * <p>
     *     Checks if each sector, row, and column are correct.
     *     Returns true if each are correct.
     *     Checks if a row and column are correct simultaneously.
     *     Checks a sector is correct separately.
     * </p>
     * @param p - the puzzle that will be checked for correctness.
     * @return - returns a boolean based on whether or not the puzzle is solved and correct
     */
    public boolean validateBoard(Puzzle p) {
        for(int i = 0; i < 9; ++i) {
            Candidate colCheck = new Candidate();
            Candidate rowCheck = new Candidate();
            for(int j = 0; j < 9; ++j) {
                if(p.board[i][j].solved) {
                    rowCheck.remove(p.board[i][j].candidates[0]);
                }
                else {
                    return false;
                }
                if(p.board[j][i].solved) {
                    colCheck.remove(p.board[j][i].candidates[0]);
                }
                else {
                    return false;
                }
            }
            if(colCheck.size != 0 || rowCheck.size != 0) {
                return false;
            }
        }
        for(int sectorRow = 0; sectorRow < 9; sectorRow += 3) {
            for(int sectorCol = 0; sectorCol < 9; sectorCol += 3) {
                Candidate sectorCheck = new Candidate();
                for(int i = 0; i < 3; ++i) {
                    for(int j = 0; j < 3; ++j) {
                        if(p.board[sectorRow + i][sectorCol + j].solved) {
                            sectorCheck.remove(p.board[sectorRow + i][sectorCol + j].candidates[0]);
                        }
                        else {
                            return false;
                        }
                    }
                }
                if(sectorCheck.size != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * A boolean function that determines whether the given board is a solvable sodoku board.
     * <p>
     *     A board is solvable if there are no double occurrences in a row column or sector.
     * </p>
     * @return - returns true if the puzzle is solvable.
     */
    public boolean possibleBoard(Puzzle p) {
        for(int i = 0; i < 9; ++i) {
            int[] rowCheck = new int[]{0,0,0,0,0,0,0,0,0};
            int[] colCheck = new int[]{0,0,0,0,0,0,0,0,0};
            for(int j = 0; j < 9; ++j) {
                if(p.board[i][j].solved) {
                    rowCheck[p.board[i][j].candidates[0] - 1]++;
                }
                if(p.board[j][i].solved) {
                    colCheck[p.board[j][i].candidates[0] - 1]++;
                }
            }
            for(int x = 0; x < 9; ++x) {
                if(rowCheck[x] > 1 || colCheck[x] > 1) {
                    return false;
                }
            }
        }
        for(int sectorRow = 0; sectorRow < 9; sectorRow += 3) {
            for (int sectorCol = 0; sectorCol < 9; sectorCol += 3) {
                int[] sectorCheck = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
                for (int i = 0; i < 3; ++i) {
                    for (int j = 0; j < 3; ++j) {
                        if(p.board[sectorRow + i][sectorCol + j].solved) {
                            sectorCheck[p.board[sectorRow + i][sectorCol + j].candidates[0] - 1]++;
                        }
                    }
                }
                for(int x = 0; x < 9; ++x) {
                    if(sectorCheck[x] > 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * A recursive boolean function that solves a given sodoku puzzle.
     * <p>
     *     If the given row and column are both 8, we have reached the end of the board and should
     *     validate if for correctness.
     *     If the given box is solved, recursively move onto the next box.
     *     If the given box is unsolved, try every candidate for a given box and recursively
     *     continue to build a board from it.
     *     If that board fails to be finished or is incorrect try the next candidate.
     *     Returns true if the board has been solved in order to short circuit solving once we have found the solution.
     *     Returns false if we have ran out of candidates for a given box and must backtrack to a different previous
     *     candidate.
     * </p>
     * @param row - the row we are currently trying all the candidates from.
     * @param col - the column we are currently trying all the candidates from.
     * @param p - the puzzle we are solving.
     * @return - returns a boolean once the board has been solved or cannot be solved.
     */
    public boolean solvePuzzle(int row, int col, Puzzle p) {
        ++recursiveCalls;
        if(row == 8 && col == 8) {
            buildCandidates(row, col, p);
            if (validateBoard(p)) {
                solution = p;
                return true;
            }
            return false;
        }
        else if(p.board[row][col].solved) {
            if(col == 8) {
                return solvePuzzle(row + 1, 0, p);
            }
            else {
                return solvePuzzle(row, col + 1, p);
            }
        }
        else {
            buildCandidates(row, col, p);
            if(p.board[row][col].size == 0) {
                return false;
            }
            for(int i = 0; i < p.board[row][col].size; ++i) {
                boolean solved;
                Puzzle passP = p.copy();
                passP.board[row][col] = new Candidate(passP.board[row][col].candidates[i]);
                if(col == 8) {
                    solved = solvePuzzle(row + 1, 0, passP);
                }
                else {
                    solved = solvePuzzle(row, col + 1, passP);
                }
                if(solved) {
                    return true;
                }
            }
            p.board[row][col] = new Candidate();
            return false;
        }
    }

    /**
     * A function solves the puzzle if it is possible to solve.
     * @param p - the puzzle that will be validated and solved
     * @return - returns true if the puzzle was solved.
     */
    public boolean solve(Puzzle p) {
        if(possibleBoard(p)) {
            return solvePuzzle(0,0, p);
        }
        return false;
    }

    /**
     * A function that converts the current Puzzle back into a 2D integer array
     * <p>
     *     Used for displaying the board on the UI in the app.
     * </p>
     * @return - returns a 2D integer array
     */
    public int[][] convertBoard() {
        int[][] retBoard = new int[9][9];
        for(int r = 0; r < 9; ++r) {
            for(int c = 0; c < 9; ++c) {
                retBoard[r][c] = solution.board[r][c].candidates[0];
            }
        }
        return retBoard;
    }

}
