# Sodoku-Brute
Brute force Sudoku puzzle solver

Written by Cameron Maberto

A program that solves Sodoku puzzles recursively.

SodokuBrutePythonPrototype was the original proof-of-concept prototype.
Prototype solves a hardcoded game board and was written to prove the logic and feasability of a recursive Sodoku puzzle solver.
Prototype shows the orgiginal board, solved board, and the amount of time it took to solve the puzzle in seconds.

SodokBruteJavaConsole is the translation of SodokuBrutePythonPrototype into Java.
The Java version adds its own data structures for easy translation from Python into Java.
The Java console also uses a hardcoded game board, displays the original board, solved board, amount of time to solve in ms, and
the number of recursive calls that were made during solving.

SodokuBruteAndroidApp is the implementation of SodokuBruteJavaConsole as an android app build in AndroidStudio. 
The app uses no hardcoded board and any solvable board can be entered. 
The app gives the user instructions on how to enter the board into the grid and uses pop-up dialogs to give the user information. 
The app displays the solution to the sodoku board and displays how long it took to solve the puzzle in milliseconds. 
The logo and grind overlay images were made in Adobe Illustrator. 
