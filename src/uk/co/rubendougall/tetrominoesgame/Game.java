package uk.co.rubendougall.tetrominoesgame;

import java.util.Arrays;
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

    public Game(Grid grid) {
        this.grid = grid;
    }

    public void update() {
        // Add shape to queue at specified interval
        final int maxShapeQueueSize = 3;
        if (shapeQueue.size() < maxShapeQueueSize) {
            addToQueue();
        }

        final int maxFallingShapeCount = 1;
        if (fallingShapes.size() < maxFallingShapeCount) {
            removeFromQueue();
        }

        // Clear falling shapes from previous update
        setShapes(fallingShapes, false);

        // Shift falling shapeTypes
        shiftFallingShapes();

        // "Draw" all shapes to grid
        setShapes(fallingShapes, true);
        setShapes(stationaryShapes, true);
    }

    private void addToQueue() {
        int index = random.nextInt(shapeTypes.length);
        ShapeType shapeType = shapeTypes[index];
        shapeQueue.offer(shapeType);

        System.out.println(shapeQueue.size());
    }

    private void removeFromQueue() {
        // Remove shape from queue and start displaying it
        System.out.println(shapeQueue.size());

        ShapeType shapeType = shapeQueue.poll();
        if (shapeType != null) {
            Shape shape = new Shape(shapeType);
            fallingShapes.add(shape);
        }
    }

    private void shiftFallingShapes() {
        for (Shape shape : fallingShapes) {
            shape.shiftDown();
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
                    if (x >= 0 && x < grid.getGridWidth() && y >= 0 && y < grid.getGridHeight()) {
                        grid.setState(x, y, state);
                    }
                }
            }
        }
    }

    static class ShapeType {
        private int[][] rows;

        public ShapeType(int[][] rows) {
            this.rows = rows;
        }

        public int calculateWidth() {
            return Arrays.stream(rows)
                    .mapToInt(row -> row.length)
                    .max()
                    .orElse(0);
        }

        public int calculateHeight() {
            return rows.length;
        }
    }

    class Shape {
        private ShapeType shapeType;
        private int x;
        private int y;

        public Shape(ShapeType shapeType) {
            // Create new Shape with default values
            // Default value for x depends on width of grid, hence requiring it as a parameter
            this.shapeType = shapeType;
            this.x = Math.max(0, (int) Math.floor((grid.getGridWidth() - shapeType.calculateWidth()) / 2));
            this.y = Math.min(0, 0 - shapeType.calculateHeight());
            System.out.println("x = " + x);
            System.out.println("y = " + y);
        }

        public void shiftDown() {
            if (isClearUnderneath()) {
                y++;
            }
        }

        private boolean isClearUnderneath() {
            // TODO: Implement properly
            return y + shapeType.calculateHeight() < grid.getGridHeight();
        }
    }
}
