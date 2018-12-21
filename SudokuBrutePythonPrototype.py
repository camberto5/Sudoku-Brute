#Sodoku Brute Exhaustive Search Solver

#game board is a 3D array.
#   -First dimmension is row
#   -Second dimmension is column.
#   -Third dimmension is list of candidates for that slot.
#   -If the lenght of third dimmension list is 1, the box is solved.
#   -Board is stored in row major order: board[row][col]

import time

blank_board = [ [ [ ], [ ], [ ], [ ], [ ], [ ], [ ], [ ], [ ] ],
                [ [ ], [ ], [ ], [ ], [ ], [ ], [ ], [ ], [ ] ],
                [ [ ], [ ], [ ], [ ], [ ], [ ], [ ], [ ], [ ] ],
                [ [ ], [ ], [ ], [ ], [ ], [ ], [ ], [ ], [ ] ],
                [ [ ], [ ], [ ], [ ], [ ], [ ], [ ], [ ], [ ] ],
                [ [ ], [ ], [ ], [ ], [ ], [ ], [ ], [ ], [ ] ],
                [ [ ], [ ], [ ], [ ], [ ], [ ], [ ], [ ], [ ] ],
                [ [ ], [ ], [ ], [ ], [ ], [ ], [ ], [ ], [ ] ],
                [ [ ], [ ], [ ], [ ], [ ], [ ], [ ], [ ], [ ] ] ]

game_board =  [ [ [ ], [ ], [1], [ ], [ ], [4], [ ], [ ], [9] ],
                [ [ ], [9], [ ], [ ], [3], [ ], [ ], [4], [ ] ],
                [ [7], [ ], [ ], [9], [ ], [ ], [5], [ ], [ ] ],
                [ [5], [ ], [ ], [2], [ ], [ ], [9], [ ], [ ] ],
                [ [ ], [3], [ ], [ ], [9], [ ], [ ], [5], [ ] ],
                [ [ ], [ ], [4], [ ], [ ], [6], [ ], [ ], [8] ],
                [ [ ], [ ], [3], [ ], [ ], [9], [ ], [ ], [1] ],
                [ [ ], [8], [ ], [ ], [1], [ ], [ ], [6], [ ] ],
                [ [2], [ ], [ ], [6], [ ], [ ], [4], [ ], [ ] ] ]

def display_board(board):
    """Prints the board given."""
    r = 0
    for row in board:
        if(r == 3 or r == 6): #print a seperator every 3 lines
            print("---------------------")
        r += 1
        c = 0
        for num in row:
            if c == 3 or c == 6:
                print("|", end = " ")
            if len(num) != 1:
                print("*", end = " ")
            else:
                print(num[0], end = " ")
            c += 1
        print()
                         

def remove_row_candidates(row, col, board):
    """Removes the candidates from the current box already used in the current row."""
    for c in range(9):
        if c != col:
            if len(board[row][c]) == 1 and board[row][c][0] in board[row][col]:
                board[row][col].remove(board[row][c][0])


def remove_col_candidates(row, col, board):
    """Removes the candidates from the current box already used in the current column."""
    for r in range(9):
        if r != row:
            if len(board[r][col]) == 1 and board[r][col][0] in board[row][col]:
                board[row][col].remove(board[r][col][0])


def remove_sector_candidates(row, col, board):
    """Removes the candidates from the current box already used in the current sector."""
    sectorOffsetR = (row // 3) * 3
    sectorOffsetC = (col // 3) * 3
    for r in range(sectorOffsetR, sectorOffsetR + 3):
        for c in range(sectorOffsetC, sectorOffsetC + 3):
            if r != row and c != col:
                if len(board[r][c]) == 1 and board[r][c][0] in board[row][col]:
                    board[row][col].remove(board[r][c][0])


def build_candidates(row, col, board):
    """Builds the candidates for the individual box given by r and c. """
    #place all possible candidates for a given box
    board[row][col] = [ 1, 2, 3, 4, 5, 6, 7, 8, 9]

    #remove candidates already used in this row
    remove_row_candidates(row, col, board)

    #remove candidates already in current column
    remove_col_candidates(row, col, board)

    #remove candidates already in the current sector
    remove_sector_candidates(row, col, board)
    

def validate_board(board):
    """Checks if the board supplied by board is a correct and finished sodoku board."""
    #check if all rows and columns are correct simultaneously
    for i in range(9):
        tempRowCandidates = [ 1, 2, 3, 4, 5, 6, 7, 8, 9]
        tempColCandidates = [ 1, 2, 3, 4, 5, 6, 7, 8, 9]
        for j in range(9):
            if len(board[i][j]) == 1 and board[i][j][0] in tempRowCandidates: #row check
                tempRowCandidates.remove(board[i][j][0])
            if len(board[j][i]) == 1 and board[j][i][0] in tempColCandidates: #column check
                tempColCandidates.remove(board[j][i][0])
        if len(tempRowCandidates) != 0:
            return False
        if len(tempColCandidates) != 0:
            return False

    #checks if all sectors are correct
    for sectorOffsetR in range(3):
        for sectorOffsetC in range(3):
            tempCandidates = [ 1, 2, 3, 4, 5, 6, 7, 8, 9]
            for r in range(3):
                for c in range(3):
                    if len(board[sectorOffsetR*3 + r][sectorOffsetC*3 + c]) == 1 and board[sectorOffsetR*3 + r][sectorOffsetC*3 + c] in tempCandidates:
                        tempCandidates.remove(board[sectorOffsetR*3 + r][sectorOffsetC*3 + c])
    if len(tempRowCandidates) != 0:
        return False
    return True #we passed everything and can return true                                 


def solve_board(row, col, board):
    """Solves the provided board recursively by trying every possible board until one works.

       Solves the board by cascading across the board in row major order. """

    #if we are at row 8 and col 8, we have reached the end of the board, try to place final candidates and validate the board
    if row == 8 and col == 8:
        build_candidates(row, col, board)
        if validate_board(board):
            display_board(board)
            return True
        return False

    #if the length of the current box is 1, it is solved, move onto the next box
    elif len(board[row][col]) == 1: 
        if col == 8:
            return solve_board(row + 1, 0, board.copy())
        else:
            return solve_board(row, col + 1, board.copy())

    #if the length of the current box is not 1, it is not solved, build the candidates for it according to the current board and try each one        
    else:
        #display_board(board)
        build_candidates(row, col, board)
        if len(board[row][col]) == 0:
            return False
        #try each candidate as a solution
        for num in board[row][col]:
            passBoard = board.copy()
            passBoard[row][col] = [num]
            if col == 8:
                solved = solve_board(row + 1, 0, passBoard)
            else:
                solved = solve_board(row, col + 1, passBoard)
            if solved:
                return True
        board[row][col] = []
        return False

def main():
    print("Attempting to solve:")
    display_board(game_board)
    print("Working...")
    start = time.time()
    solve_board(0, 0, game_board.copy())
    end = time.time()
    print("Puzzle solved in " + str(end - start) + " seconds")
    

if __name__ == '__main__':
    main()
