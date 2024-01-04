import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SimpleSnakeView extends Application {
    private Grid grid;
    private int cellSize = 20;
    private Food food;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        grid = new Grid();
        int sceneSizeX = cellSize * grid.getGridSizeX();
        int sceneSizeY = cellSize * grid.getGridSizeY();
        Scene scene = new Scene(root, sceneSizeX, sceneSizeY, Color.WHITE);
        final Canvas canvas = new Canvas(sceneSizeX, sceneSizeY);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (int i = 0; i < sceneSizeX; i += cellSize) {
            for (int j = 0; j < sceneSizeY; j += cellSize) {
                if ((i + j) % (2* cellSize) == 0) {
                    gc.setFill(Color.DARKGREEN);
                } else {
                    gc.setFill(Color.GREEN);
                }
                gc.fillRect(i, j, cellSize, cellSize);
            }
        }

        food = new Food(grid);
        gc.setFill(Color.RED);
        gc.fillRect(food.getFoodX() * cellSize, food.getFoodY() * cellSize, cellSize, cellSize);

        root.getChildren().add(canvas);
        primaryStage.setTitle("JavaFX Grid Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
