# Sudoku-Brute
Brute force Sudoku puzzle solver

Written by Cameron Maberto

A program that solves Sudoku puzzles recursively.

SudokuBrutePythonPrototype was the original proof-of-concept prototype.
Prototype solves a hardcoded game board and was written to prove the logic and feasability of a recursive Sudoku puzzle solver.
Prototype shows the orgiginal board, solved board, and the amount of time it took to solve the puzzle in seconds.

SudokBruteJavaConsole is the translation of SudokuBrutePythonPrototype into Java.
The Java version adds its own data structures for easy translation from Python into Java.
The Java console also uses a hardcoded game board, displays the original board, solved board, amount of time to solve in ms, and
the number of recursive calls that were made during solving.

SudokuBruteAndroidApp is the implementation of SudokuBruteJavaConsole as an android app build in AndroidStudio. 
The app uses no hardcoded board and any solvable board can be entered. 
The app gives the user instructions on how to enter the board into the grid and uses pop-up dialogs to give the user information. 
The app displays the solution to the sudoku board and displays how long it took to solve the puzzle in milliseconds. 
The logo and grind overlay images were made in Adobe Illustrator. 
