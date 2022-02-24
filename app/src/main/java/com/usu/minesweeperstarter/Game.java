package com.usu.minesweeperstarter;

import static com.usu.minesweeperstarter.Cell.Type.EMPTY;
import static com.usu.minesweeperstarter.Cell.Type.MINE;
import static com.usu.minesweeperstarter.Cell.Type.NUMBER;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

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
    Context context;
    boolean firstTime;

    public Game(String gameMode, int screenWidth, int screenHeight, Context context) {
        cells = new Cell[rows][cols];
        this.context = context;
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
        // Setup a boolean array to randomly assign the types for the cells
        ArrayList<Boolean> values = new ArrayList<>(rows * cols);
        int totalTrue = 0;
        firstTime = true;
        for (int i = 0; i < rows * cols; i++) {
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
        double height = 0;
        int mines = 0;
        for (int i = 0; i < cells.length; i++) {
            double width = 0;
            for (int j = 0; j < cells[i].length; j++) {
                Cell.Type type = NUMBER;
                boolean value = values.remove(0);
                if (value) {
                    type = MINE;
                    mines++;
                    Log.d("Mine has been added", String.valueOf(mines));

                }
//                Log.d("The cell type is ", type.toString());
//                Log.d("Location is ", String.valueOf(width) + ", " + String.valueOf(height));
                cells[i][j] = new Cell(width, height, cellWidth, cellHeight, type);
                width += cellWidth;
            }
            height += cellHeight;
        }

        Log.d("All values have been added to the screen", "");

        // Now set all neighbor counts
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j].setNumNeighbors(countNeighbors(i, j, cells));
                if (cells[i][j].getNumNeighbors() == 0 && cells[i][j].getType() != MINE) {cells[i][j].setType(EMPTY);}
            }
        }
    }

    private int countNeighbors(int row, int col, Cell[][] cells) {
        int neighbors = 0;
        // Row above the cell
        if (row -1 > -1){
            if (cells[row -1][col].getType() == MINE){neighbors++;}
            if ((col - 1 > -1) && cells[row -1][col - 1].getType() == MINE){neighbors++;}
            if ((col + 1 < cells[row].length) && cells[row -1][col + 1].getType() == MINE){neighbors++;}
        }
        // Left and right of cell
        if (col - 1 > -1){
            if (cells[row][col - 1].getType() == MINE){neighbors++;}
            if ((col + 1 < cells[row].length) && cells[row][col + 1].getType() == MINE){neighbors++;}
        }
        // Row below the cell
        if (row + 1 < cells.length){
            if (cells[row + 1][col].getType() == MINE){neighbors++;}
            if ((col - 1 > -1) && cells[row + 1][col - 1].getType() == MINE){neighbors++;}
            if ((col + 1 < cells[row].length) && cells[row + 1][col + 1].getType() == MINE){neighbors++;}
        }
        return neighbors;
    }

    private void revealMines() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].getType() == MINE){
                    cells[i][j].select();
                }
            }
        }
    }

    private void explodeBlankCells(int row, int col) {
        if (row < 0 || row >= cells.length) return;
        if (col < 0 || col >= cells[row].length) return;
        if (cells[row][col].isSelected()) return;
        cells[row][col].select();
        if (cells[row][col].getType() == Cell.Type.NUMBER) return;

        explodeBlankCells(row - 1, col - 1);
        explodeBlankCells(row - 1, col);
        explodeBlankCells(row - 1, col + 1);
        explodeBlankCells(row, col - 1);
        explodeBlankCells(row, col + 1);
        explodeBlankCells(row + 1, col - 1);
        explodeBlankCells(row + 1, col);
        explodeBlankCells(row, col + 1);
    }


    // Both handle methods need to make the motion event.getX and getY into (int) type
    public void handleTap(MotionEvent e) {

        Log.d("Tapping cell at: ", String.valueOf((int) (e.getY() / cellHeight)) + ", " + String.valueOf((int) (e.getX() / cellWidth)));
        Cell cell = cells[(int) (e.getY() / cellHeight)][(int) (e.getX() / cellWidth)];
        if (firstTime && cell.getType() == MINE) {initCells();}
        else {
            firstTime = false;
            Log.d("This is cell type: ", String.valueOf(cell.getType()));
            selectCell((int) (e.getY() / cellHeight), (int) (e.getX() / cellWidth));
        }
    }

    private void selectCell(int row, int col){
        if (state != State.PLAY) return;
        if (cells[row][col].getType() == MINE){
            cells[row][col].select();
            state = State.LOSE;
            revealMines();
        } else if (cells[row][col].getType() == EMPTY){
            explodeBlankCells(row, col);
        } else{
            cells[row][col].select();
        }
    }

    public void handleLongPress(MotionEvent e) {
        Log.d("Marking the long press at: ", String.valueOf((int) (e.getY() / cellHeight)) + ", " + String.valueOf((int) (e.getX() / cellWidth)));
        Cell cell = cells[(int) (e.getY() / cellHeight)][(int) (e.getX() / cellWidth)];
        cell.toggleMark();

        // Check if player has won
        if (checkWinner()) {
            this.state = State.WIN;
        }
    }

    private boolean checkWinner(){
        int mines = 0;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].getType() == MINE && cells[i][j].isMarked() ) {
                    mines++;
                }
            }
        }
        return mines == mineCount;
    }

    public void draw(Canvas canvas, Paint paint) {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j].draw(canvas, paint);
                paint.reset();
            }
        }
        if (state == State.WIN) {
            int color = ContextCompat.getColor(context, R.color.primary);
//            int color = Color.BLUE;
            paint.setColor(color);
            paint.setTextSize(150);
            canvas.drawText("You win!", screenWidth/ 4, screenHeight/ 3, paint);
        }
        else if (state == State.LOSE){
            int color = ContextCompat.getColor(context, R.color.secondary);
            paint.setColor(color);
            paint.setTextSize(150);
            canvas.drawText("You LOSE!", screenWidth/ 4, screenHeight/ 3, paint);
        }
    }
}
