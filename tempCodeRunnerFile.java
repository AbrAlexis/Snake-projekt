import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GridExample extends Application {

    private static final int GRID_SIZE = 10;

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();

        // Create grid lines (Rectangles)
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Rectangle cell = new Rectangle(50, 50); // Adjust the size of each cell
                cell.setFill(Color.TRANSPARENT);
                cell.setStroke(Color.BLACK);
                grid.add(cell, col, row);
            }
        }

        Scene scene = new Scene(grid, 500, 500);

        primaryStage.setTitle("JavaFX Grid Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}