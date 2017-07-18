package uk.co.rubendougall.tetrominoesgame;

import processing.core.PApplet;

public class Main extends PApplet {
    private Grid grid;
    private Game game;
    private int timeSinceGameUpdated = 0;

    public static void main(String[] args) {
        PApplet.main("uk.co.rubendougall.tetrominoesgame.Main");
    }

    @Override
    public void settings() {
        size(600, 800, P2D);
    }

    @Override
    public void setup() {
        grid = new Grid(this);
        game = new Game(grid);

        if (surface != null) {
            surface.setResizable(false); // TODO: Still need to disable maximising properly
        }
    }

    @Override
    public void draw() {
        background(50);

        final int updateGameInterval = 50;
        if (timeSinceGameUpdated == 0) {
            game.update();
        }
        timeSinceGameUpdated = (timeSinceGameUpdated + 1) % updateGameInterval;

        grid.update();
        grid.draw();
    }
}
