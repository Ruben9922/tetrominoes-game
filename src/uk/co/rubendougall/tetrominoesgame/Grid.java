package uk.co.rubendougall.tetrominoesgame;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;

class Grid {
    private final PApplet parent;
    private final int GRID_WIDTH = 5;
    private final int GRID_HEIGHT = 10;
    private final int CELL_WIDTH;
    private final int CELL_HEIGHT;
    private PShape shape;

    public Grid(PApplet parent) {
        this.parent = parent;

        CELL_WIDTH = parent.width / GRID_WIDTH;
        CELL_HEIGHT = parent.height / GRID_HEIGHT;

        updateShape();
    }

    public int getGridWidth() {
        return GRID_WIDTH;
    }

    public int getGridHeight() {
        return GRID_HEIGHT;
    }

    public int getCellWidth() {
        return CELL_WIDTH;
    }

    public int getCellHeight() {
        return CELL_HEIGHT;
    }

    public void updateShape() {
        shape = parent.createShape(PConstants.GROUP);

        for (int i = 0; i < GRID_WIDTH; i++) {
            for (int j = 0; j < GRID_HEIGHT; j++) {
                PShape rectangle = parent.createShape(PConstants.RECT, i * CELL_WIDTH, j * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
                rectangle.setStroke(false);
                rectangle.setFill(false);
                shape.addChild(rectangle);
            }
        }
    }

    public void draw() {
        parent.pushMatrix();
        parent.shape(shape);
        parent.popMatrix();
    }
}
