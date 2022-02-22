package com.usu.minesweeperstarter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

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

    public void draw(Canvas canvas, Paint paint) {
        if (type == Type.NUMBER && numNeighbors < 2){paint.setColor(Color.GREEN);} // 1 Neighbor
        else if (type == Type.NUMBER && numNeighbors < 3 ){paint.setColor(Color.MAGENTA);} // 2 neighbors
        else if (type == Type.NUMBER ){paint.setColor(Color.BLUE);} // 3 neighbors
        else if (type == Type.MINE){paint.setColor(Color.BLACK);} // Mine
        else {paint.setColor(Color.WHITE);} // Default

        if (isMarked) {paint.setColor(Color.RED);} // Marked
        canvas.drawRect((float) xPos, (float) yPos, (float) width, (float) height, paint);
        // TODO: Draw the cell at its position depending on the state it is in
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
