import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javafx.stage.Stage;

public class SimpleSnakeView extends Application {

    public void start(Stage stage) {
        Canvas canvas = new Canvas(5000, 5000);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.LIGHTGRAY);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);

        StackPane root = new StackPane();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, 600, 500);

        GridPane gridpane = new GridPane();
        grid.setAlignment(Pos.CENTER);
        for (int i = 0; i < grid.getGridSizeX(); i++) {
            grid.addRow(i);
        }
        for (int i = 0; i < grid.getGridSizeY(); i++) {
            grid.addRow(i);
        }
        stage.setScene(scene);
        stage.setTitle("SimpleSnakeView");
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
