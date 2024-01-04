import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SimpleSnakeView extends Application {
    private Grid grid;
    private int cellSize = 10;

    @Override
    public void start(Stage primaryStage) {
        grid = new Grid();
        int sceneSizeX = 10 * grid.getGridSizeX();
        int sceneSizeY = 10 * grid.getGridSizeY();
        GridPane gridpane = new GridPane();
        // Create grid lines (Rectangles)
        for (int row = 0; row < grid.getGridSizeX(); row++) {
            for (int col = 0; col < grid.getGridSizeY(); col++) {
                Rectangle cell = new Rectangle(cellSize, cellSize);
                cell.setFill(Color.TRANSPARENT);
                cell.setStroke(Color.BLACK);
                gridpane.add(cell, row, col);
            }

        }

        Scene scene = new Scene(gridpane, sceneSizeX, sceneSizeY);

        primaryStage.setTitle("JavaFX Grid Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
