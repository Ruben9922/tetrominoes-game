package uk.co.rubendougall.tetrominoesgame;

import org.jetbrains.annotations.Contract;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;

class Grid {
    private final PApplet parent;
    private final int gridWidth = 5;
    private final int gridHeight = 10;
    private final int cellWidth;
    private final int cellHeight;
    private final int horizontalPadding = 20;
    private final int verticalPadding = 20;
    private PShape shape;

    public Grid(PApplet parent) {
        this.parent = parent;

        cellWidth = (parent.width - (horizontalPadding * 2)) / gridWidth;
        cellHeight = (parent.height - (verticalPadding * 2)) / gridHeight;

        updateShape();
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public void updateShape() {
        shape = parent.createShape(PConstants.GROUP);

        shape.translate(horizontalPadding, verticalPadding);

        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                PShape cell = parent.createShape(PConstants.RECT, i * cellWidth, j * cellHeight, cellWidth, cellHeight);
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
        return parent.mouseX >= (column * cellWidth) + horizontalPadding
                && parent.mouseX < ((column + 1) * cellWidth) + horizontalPadding
                && parent.mouseY >= (row * cellHeight) + verticalPadding
                && parent.mouseY < ((row + 1) * cellHeight) + verticalPadding;
    }

    public void draw() {
        parent.pushMatrix();
        parent.shape(shape);
        parent.popMatrix();
    }
}
