package uk.co.rubendougall.tetrominoesgame;

import processing.core.PApplet;

public class TetrominoesGame extends PApplet {
    private Grid grid;

    public static void main(String[] args) {
        PApplet.main("uk.co.rubendougall.tetrominoesgame.TetrominoesGame");
    }

    @Override
    public void settings() {
        size(600, 800, P2D);
    }

    @Override
    public void setup() {
        grid = new Grid(this);

        if (surface != null) {
            surface.setResizable(false); // TODO: Still need to disable maximising properly
        }
    }

    @Override
    public void draw() {
        background(50);

        grid.update();
        grid.draw();
    }
}
