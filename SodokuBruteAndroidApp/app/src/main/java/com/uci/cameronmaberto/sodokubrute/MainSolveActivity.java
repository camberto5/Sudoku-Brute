package com.uci.cameronmaberto.sodokubrute;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainSolveActivity extends AppCompatActivity {

    Solve solver = new Solve();

    /**
     * A function that converts a String taken from an EditText into an integer 0-9
     * @param s - the String that will be converted
     * @return - returns 0 if s is the empty string, the string parsed to an integer otherwise.
     */
    public static int parseText(String s) {
        if(s.equals(""))
            return 0;
        else
            return Integer.parseInt(s);
    }

    /**
     * A function that builds the game board from the UI Table
     * @param board - the board that will be modified into the game board
     */
    public void boardFromTable(int[][] board) {
        //get the table from the UI
        TableLayout table = findViewById(R.id.Board);
        for (int r = 0; r < 9; ++r) {
            //take the current TableRow from the TableView of EditTexts
            TableRow currentRow = (TableRow) table.getChildAt(r);
            for (int c = 0; c < 9; ++c) {
                //take the current EditText from the TableRow, parse the text and place it in the board
                EditText currentText = (EditText) currentRow.getChildAt(c);
                int boxValue = parseText(currentText.getText().toString());
                if(boxValue != 0) {
                    currentText.setTextColor(Color.parseColor("#FF0E8994"));
                }
                else {
                    currentText.setTextColor(Color.BLACK);
                }
                board[r][c] = boxValue;
            }
        }
    }

    /**
     * A void function that displays the given board to the UI
     * <p>
     *     Used for displaying solved boards back to the UI
     * </p>
     * @param board - the board that will be displayed
     */
    public void displayBoardToUI(int[][] board) {
        //get the table from the UI
        TableLayout table = findViewById(R.id.Board);
        for(int r = 0; r < 9; ++r) {
            //take the current TableRow from the TableView of EditTexts
            TableRow currentRow = (TableRow) table.getChildAt(r);
            for(int c = 0; c < 9; ++c) {
                //take the current EditText from the TableRow, parse the int and place it in the board
                EditText currentText = (EditText) currentRow.getChildAt(c);
                String boxValue = Integer.toString(board[r][c]);
                currentText.setText(boxValue);
            }
        }
    }

    /**
     * A function that makes all the UI board slots empty
     */
    public void clearBoard() {
        TableLayout table = findViewById(R.id.Board);
        for(int r = 0; r < 9; ++r) {
            //take the current TableRow from the TableView of EditTexts
            TableRow currentRow = (TableRow) table.getChildAt(r);
            for (int c = 0; c < 9; ++c) {
                //take the current EditText from the TableRow, set it to empty
                EditText currentText = (EditText) currentRow.getChildAt(c);
                currentText.setText("");
                currentText.setTextColor(Color.BLACK);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_solve);

        final Button resetButton = findViewById(R.id.reset);
        final Button solveButton = findViewById(R.id.solve);

        clearBoard();

        solveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean solved = false;
                int[][] board = new int[9][9];
                //take the items from the UI and build it into a board
                boardFromTable(board);
                //build a puzzle from the completed board
                Puzzle currentPuzzle = new Puzzle(board);
                //solve the puzzle
                long solveStart = System.currentTimeMillis();
                solved = solver.solve(currentPuzzle);
                long solveEnd = System.currentTimeMillis();
                if(solved) {
                    //convert the board back into a 2D integer array
                    int[][] solution = solver.convertBoard();
                    //display the board to the UI
                    displayBoardToUI(solution);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainSolveActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("The puzzle was solved!");
                    builder.setMessage("The puzzle was solved in " + (solveEnd - solveStart) + "ms");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainSolveActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("The puzzle was not solved");
                    builder.setMessage("The puzzle may be impossible to solve. Try fixing any mistakes made in puzzle entry or clear the board and try a new puzzle.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.setNegativeButton("CLEAR BOARD", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            clearBoard();
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearBoard();
            }
        });
    }
}
