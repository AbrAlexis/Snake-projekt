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
    private int cellSize = 10;
    private Food food;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        grid = new Grid();
        Snake snake = new Snake(grid);
        int sceneSizeX = cellSize * grid.getGridSizeX();
        int sceneSizeY = cellSize * grid.getGridSizeY();
        Scene scene = new Scene(root, sceneSizeX, sceneSizeY, Color.WHITE);
        final Canvas canvas = new Canvas(sceneSizeX, sceneSizeY);
        // Create grid lines (Rectangles)
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (int i = 0; i < sceneSizeX; i += cellSize) {
            for (int j = 0; j < sceneSizeY; j += cellSize) {
                if ((i + j) % (2 * cellSize) == 0) {
                    gc.setFill(Color.DARKGREEN);
                } else {
                    gc.setFill(Color.GREEN);
                }
                gc.fillRect(i, j, cellSize, cellSize);
            }
        }
        showSnake(gc, snake);
        root.getChildren().add(canvas);
        primaryStage.setTitle("JavaFX Grid Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showSnake(GraphicsContext gc, Snake snake) {
        gc.setFill(Color.BLUE);
        gc.fillRect(snake.getHeadX() * cellSize, snake.getHeadY() * cellSize, cellSize, cellSize);
        for (int i = 0; i < snake.getSize(); i++) {
            gc.setFill(Color.BLACK);
            gc.fillRect(snake.getBody().get(i).getXpos() * cellSize, snake.getBody().get(i).getYpos() * cellSize,
                    cellSize, cellSize);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
