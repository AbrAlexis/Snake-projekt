import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SimpleSnakeView extends Application {

    Grid grid = new Grid();

    @Override
    public void start(Stage primaryStage) {
        GridPane gridpane = new GridPane();

        // Create grid lines (Rectangles)
        for (int row = 0; row < grid.getGridSizeX(); row++) {
            for (int col = 0; col < grid.getGridSizeY(); col++) {
                Rectangle cell = new Rectangle(500 / grid.getGridSizeX(), 500 / grid.getGridSizeY());
                cell.setFill(Color.TRANSPARENT);
                cell.setStroke(Color.BLACK);
                gridpane.add(cell, col, row);
            }
        }

        Scene scene = new Scene(gridpane, 500, 500);

        primaryStage.setTitle("JavaFX Grid Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
