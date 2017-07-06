package uk.co.rubendougall.tetrominoesgame;

import org.jetbrains.annotations.Contract;
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
                PShape cell = parent.createShape(PConstants.RECT, i * CELL_WIDTH, j * CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
                cell.setStroke(false);

                // Highlight this cell if mouse hovering over this cell
                if (isMouseInCell(i, j)) {
                    cell.setFill(parent.color(127));
                } else {
                    cell.setFill(false);
                }

                shape.addChild(cell);
            }
        }
    }

    @Contract(pure = true)
    private boolean isMouseInCell(int column, int row) {
        return parent.mouseX >= (column) * CELL_WIDTH && parent.mouseX < (column + 1) * CELL_WIDTH
                && parent.mouseY >= (row) * CELL_HEIGHT && parent.mouseY < (row + 1) * CELL_HEIGHT
                && parent.mouseX != 0 && parent.mouseX != parent.width - 1 // Might change later
                && parent.mouseY != 0 && parent.mouseY != parent.height - 1;
    }

    public void draw() {
        parent.pushMatrix();
        parent.shape(shape);
        parent.popMatrix();
    }
}
