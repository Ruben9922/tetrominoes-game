package uk.co.rubendougall.tetrominoesgame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tetrominoes Game");

        Group root = new Group();
        Canvas canvas = new Canvas(600, 800);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                draw(gc);
            }
        };
        animationTimer.start();

        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void draw(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(5);
        gc.strokeLine(0, 0, Math.floor(Math.random() * 600), Math.floor(Math.random() * 600));
    }
}