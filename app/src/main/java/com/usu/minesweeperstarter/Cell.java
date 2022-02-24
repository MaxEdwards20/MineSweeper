package com.usu.minesweeperstarter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Cell {

    // FEEL FREE TO CHANGE THESE!
    private int[] colors = {
            Color.BLUE,
            Color.GREEN,
            Color.RED,
            Color.rgb(0,0,100),
            Color.YELLOW,
            Color.CYAN,
            Color.MAGENTA,
            Color.BLACK
    };

    public enum Type {
        MINE,
        NUMBER,
        EMPTY
    }

    private boolean isMarked = false;
    private boolean isSelected = false;
    private Type type;
    private double xPos;
    private double yPos;
    private double width;
    private double height;
    private int numNeighbors = 0;

    public Cell(double xPos, double yPos, double width, double height, Type type) {
        this.type = type;
        this.yPos = yPos;
        this.xPos = xPos;
        this.width = width;
        this.height = height;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void toggleMark() {
        isMarked = !isMarked;
    }

    public void select() {
        isSelected = true;
        isMarked = false;
    }

    public void setNumNeighbors(int numNeighbors) {
        this.numNeighbors = numNeighbors;
    }

    public int getNumNeighbors(){return this.numNeighbors;}

    public void draw(Canvas canvas, Paint paint) {


        // Outline of cell
        paint.setColor(Color.BLACK);
        canvas.drawRect((float) xPos, (float) yPos, (float) (xPos + width), (float) (yPos + height), paint);
        paint.reset();

        // Inner Cell
        paint.setColor(Color.GRAY);
        canvas.drawRect((float) xPos, (float) yPos, (float) (xPos + width) - 1, (float) (yPos + height) -1 , paint);
        paint.reset();

        if (type == Type.MINE){Log.d("Mine is on the screen at ", String.valueOf(xPos) + ", " + String.valueOf(yPos));}

        if (isMarked) {
            paint.setColor(Color.RED);
            canvas.drawRect((float) xPos, (float) yPos, (float) (xPos + width) - 1, (float) (yPos + height) -1 , paint);} // Marked
        if (isSelected){
            if (type == Type.EMPTY){
                paint.setColor(Color.WHITE);
                canvas.drawRect((float) xPos, (float) yPos, (float) (xPos + width) - 1, (float) (yPos + height) -1 , paint);
            } // Mines
            if (type == Type.MINE){
                paint.setColor(Color.BLACK);
                canvas.drawRect((float) xPos, (float) yPos, (float) (xPos + width) - 1, (float) (yPos + height) -1 , paint);
            } // Neighors
            if (type == Type.NUMBER){
                paint.setTextSize(40);
                paint.setColor(colors[numNeighbors]);
                canvas.drawText(String.valueOf(numNeighbors),(float) (xPos + width/4), (float) (yPos + height - 10), paint);
                paint.reset();
            }

        }
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
