import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SimpleSnakeView extends Application {
    private Grid grid;
    private int cellSize = 15; // Can be reduced, if the game needs to be played where grid height is set to
                               // 100 and the monitor is to small.
    private Food food;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        grid = new Grid();
        Snake snake = new Snake(grid);
        food = new Food(grid);
        for (int i = 0; i < 5; i++) {
            snake.createBodypart(snake.getHeadX(), snake.getBody().get(i).getYpos() + 1);
        }
        int sceneSizeX = cellSize * grid.getGridSizeX();
        int sceneSizeY = cellSize * grid.getGridSizeY();
        Scene scene = new Scene(root, sceneSizeX, sceneSizeY, Color.WHITE);
        final Canvas canvas = new Canvas(sceneSizeX, sceneSizeY);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (int i = 0; i < grid.getGridSizeX(); i++) {
            for (int j = 0; j < grid.getGridSizeY(); j++) {
                if ((i + j) % 2 * cellSize == 0) {
                    gc.setFill(Color.DARKGREEN);
                } else {
                    gc.setFill(Color.GREEN);
                }
                gc.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }
        createFood(gc, food);
        showSnake(gc, snake);
        root.getChildren().add(canvas);
        primaryStage.setTitle("JavaFX Grid Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void createFood(GraphicsContext gc, Food food) {
        food.moveFood(grid);
        gc.setFill(Color.RED);
        gc.fillRect(food.getFoodX() * cellSize, food.getFoodY() * cellSize, cellSize, cellSize);
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

    public void colorCheck(GraphicsContext gc, Snake snake) {
        ArrayList<SnakeBody> body = snake.getBody();
        if (((snake.getBody()).get(body.size() - 1).getXpos() + (snake.getBody()).get(body.size() - 1).getYpos())
                % (2 * cellSize) == 0) {
            gc.setFill(Color.DARKGREEN);
        } else {
            gc.setFill(Color.GREEN);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
