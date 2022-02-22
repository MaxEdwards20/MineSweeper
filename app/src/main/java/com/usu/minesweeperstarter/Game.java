package com.usu.minesweeperstarter;

import static com.usu.minesweeperstarter.Cell.Type.EMPTY;
import static com.usu.minesweeperstarter.Cell.Type.MINE;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Game {
    private enum State {
        PLAY,
        WIN,
        LOSE,
    }

    Cell[][] cells;
    int mineCount;
    int rows = 30;
    int cols = 16;
    double cellWidth;
    double cellHeight;
    int screenWidth;
    int screenHeight;
    State state = State.PLAY;

    public Game(String gameMode, int screenWidth, int screenHeight) {
        cells = new Cell[rows][cols];
        if (gameMode.equals("exp")) {
            mineCount = 100;
        } else if (gameMode.equals("inter")) {
            mineCount = 50;
        } else {
            mineCount = 10;
        }
        cellHeight = (double) screenHeight / rows;
        cellWidth = (double) screenWidth / cols;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        initCells();
    }

    private void initCells() {
        // TODO: create all cells, randomly assigning cells to be mines depending on difficulty.
        // HINT: 1. Create an ArrayList of Booleans
        //      2. set the first n (where n is number of mines you want) to be true
        //      3. set the remaining to be false (the total number of items in the list should be rows * cols)
        //      4. then shuffle the array list using Collections.shuffle()
        //      5. Then you can use this arraylist like a queue when iterating of your grid

        // Setup a boolean array to randomly assign the types for the cells
        ArrayList<Boolean> values = new ArrayList<>(rows*cols);
        int totalTrue = 0;
        for (int i = 0; i < rows*cols; i++) {
            if (totalTrue < this.mineCount) {
                values.add(true);
                totalTrue++;
            } else {
                values.add(false);
            }
        }
        Collections.shuffle(values);
        Log.d("The length of the values table is now: ", String.valueOf(values.size()));
        Log.d("Number of mines: ", String.valueOf(mineCount));


        // For each value in the array create a new cell object with specified positions

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                Cell.Type type = EMPTY;
                boolean value = values.remove(0);
                if (value) {type = MINE;}
                Log.d("The cell type is ", type.toString());
                cells[i][j] = new Cell(i* cellWidth, j * cellHeight, cellWidth, cellHeight, type);
            }
        }
    }



    private int countNeighbors(int row, int col, Cell[][] cells) {
        // TODO: Count how many mines surround the cell at (row, col);
        return 0;
    }

    private void revealMines() {
        // TODO: loop through the cells and select all mines
    }

    private void explodeBlankCells(int row, int col) {
        // TODO: recursively select all surrounding cells, only stopping when
        //      you reach a cell that has already been selected,
        //      or when you select a cell that is not Empty
    }


    // Both handle methods need to make the motion event.getX and getY into (int) type
    public void handleTap(MotionEvent e) {
        // TODO: find the cell the player tapped
        //      Depending on what type of cell they tapped
        //         mine: select the cell, reveal the mines, and set the game to the LOSE state
        //         empty cell: select the cell and explode the surrounding cells
        //         all others: simply select the cell
    }

    public void handleLongPress(MotionEvent e) {
        // TODO: find the cell and toggle its mark
        //       then check to see if the player won the game
    }


    public void draw(Canvas canvas, Paint paint) {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j].draw(canvas, paint);
                paint.reset();
            }
        }

        if (state == State.WIN) {
            // TODO: Some kind of a print object to get winner onto the screen
//            draw(this,"WINNER!!!");
        }
    }
}
