package uk.co.rubendougall.tetrominoesgame;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

class Game {
    private ShapeType[] shapeTypes = new ShapeType[] {
            new ShapeType(new int[][] {
                    new int[] {1},
                    new int[] {0, 1, 2}
            })
    };
    private Queue<ShapeType> shapeQueue = new LinkedList<>();
    private Random random = new Random();
    private Grid grid;
    private List<Shape> fallingShapes = new LinkedList<>();
    private List<Shape> stationaryShapes = new LinkedList<>();

    Game(Grid grid) {
        this.grid = grid;
    }

    void update() {
        // Clear falling shapes from previous update
        setShapes(fallingShapes, false);

        // Shift falling shapeTypes
        shiftFallingShapes();

        // Redraw falling shapes now that they have been shifted down
        setShapes(fallingShapes, true);

        // Draw stationary shapes since shapes may have been moved from falling shapes list to stationary shapes list
        // and therefore have been cleared but not redrawn
        setShapes(stationaryShapes, true);

        // Add shape to queue if max queue size not reached
        final int maxShapeQueueSize = 3;
        if (shapeQueue.size() < maxShapeQueueSize) {
            addToQueue();
        }

        // Remove shape from queue, ready to display on next update, if max number of falling shapes not reached
        final int maxFallingShapeCount = 1;
        if (fallingShapes.size() < maxFallingShapeCount) {
            removeFromQueue();
        }
    }

    private void addToQueue() {
        int index = random.nextInt(shapeTypes.length);
        ShapeType shapeType = shapeTypes[index];
        shapeQueue.offer(shapeType);

        System.out.println("Added: shapeQueue.size() = " + shapeQueue.size());
    }

    private void removeFromQueue() {
        ShapeType shapeType = shapeQueue.poll();
        if (shapeType != null) {
            Shape shape = new Shape(shapeType);
            fallingShapes.add(shape);
        }

        System.out.println("Removed: shapeQueue.size() = " + shapeQueue.size());
    }

    private void shiftFallingShapes() {
        Iterator<Shape> iterator = fallingShapes.iterator();
        while (iterator.hasNext()) {
            Shape shape = iterator.next();

            if (shape.isClearUnderneath()) {
                // If clear underneath this shape, shift shape down
                shape.shift();
            } else {
                // If not clear, shape must stop falling, so remove from falling shapes list and add to stationary
                // shapes list
                iterator.remove();
                stationaryShapes.add(shape);
            }
        }
    }

    private void setShapes(List<Shape> shapes, boolean state) {
        for (Shape shape : shapes) {
            int[][] rows = shape.shapeType.rows;
            for (int i = 0; i < rows.length; i++) {
                int[] row = rows[i];
                for (int column : row) {
                    int x = shape.x + column;
                    int y = shape.y + i;
                    if (grid.areCoordsValid(x, y)) {
                        grid.setState(x, y, state);
                    }
                }
            }
        }
    }

    static class ShapeType {
        private int[][] rows;
        private int width; // Store width and height as fields as they can't change
        private int height;

        ShapeType(int[][] rows) {
            this.rows = rows;

            // Calculate width by finding length of longest row
            this.width = Arrays.stream(rows)
                    .mapToInt(row -> row.length)
                    .max()
                    .orElse(0);

            // Calculate height; assumes rows are non-empty
            this.height = rows.length;
        }

        int getWidth() {
            return width;
        }

        int getHeight() {
            return height;
        }
    }

    class Shape {
        private ShapeType shapeType;
        private int x;
        private int y;

        Shape(ShapeType shapeType) {
            // Create new Shape with default values
            // Default value for x depends on width of grid, hence requiring it as a parameter
            this.shapeType = shapeType;
            this.x = Math.max(0, (int) Math.floor((grid.getGridWidth() - shapeType.getWidth()) / 2));
            this.y = Math.min(0, 0 - shapeType.getHeight());
            System.out.println("x = " + x);
            System.out.println("y = " + y);
        }

        void shift() {
            y++;
        }

        boolean isClearUnderneath() {
            return notAtBottom() && noShapesUnderneath();
        }

        private boolean notAtBottom() {
            return y + shapeType.getHeight() < grid.getGridHeight();
        }

        private boolean noShapesUnderneath() { // TODO: Need to check for "game over"; currently continues trying to add another shape
            for (int i = 0; i < shapeType.rows.length; i++) {
                int[] row = shapeType.rows[i];
                for (int column : row) {
                    int x = this.x + column;
                    int y = this.y + i + 1; // Adding 1 since concerned with the square below

                    // Crucial that this check is done after all falling shapes (incl. this one) are cleared and before
                    // they're redrawn
                    if (grid.areCoordsValid(x, y) && grid.getState(x, y)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
}
