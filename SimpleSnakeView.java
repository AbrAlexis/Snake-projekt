import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SimpleSnakeView extends Application {
    private Grid grid;
    private int cellSize = 50;
    private Food food;
    private SimpleSnakeController simpleSnakeController;
    private GraphicsContext gc;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        grid = new Grid();
        Snake snake = new Snake(grid);
        food = new Food(grid);
        simpleSnakeController = new SimpleSnakeController(snake, this);

        int sceneSizeX = cellSize * grid.getGridSizeX();
        int sceneSizeY = cellSize * grid.getGridSizeY();
        Scene scene = new Scene(root, sceneSizeX, sceneSizeY, Color.WHITE);

        scene.setOnKeyPressed(e -> {
            KeyCode keyCode = e.getCode();
            simpleSnakeController.handleKeyPress(keyCode);
        });

        final Canvas canvas = new Canvas(sceneSizeX, sceneSizeY);
        gc = canvas.getGraphicsContext2D();

        // Tid og start på spil:
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            snake.move();
            gc.clearRect(0, 0, sceneSizeX, sceneSizeY);
            drawGrid();
            createFood();
            showSnake(new SnakeState(snake.getHeadX(), snake.getHeadY(), snake.getBody()));
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        for (int i = 0; i < 5; i++) {
            snake.createBodypart(snake.getHeadX(), snake.getBody().get(i).getYpos() + 1);
        }

        // Tegn gitter og initialiser scenen
        drawGrid();
        createFood();
        showSnake(new SnakeState(snake.getHeadX(), snake.getHeadY(), snake.getBody()));
        root.getChildren().add(canvas);
        primaryStage.setTitle("JavaFX Grid Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawGrid() {
        for (int i = 0; i < grid.getGridSizeX(); i++) {
            for (int j = 0; j < grid.getGridSizeY(); j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(Color.DARKGREEN);
                } else {
                    gc.setFill(Color.GREEN);
                }
                gc.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }
    }

    private void createFood() {
        food.moveFood(grid);
        gc.setFill(Color.RED);
        gc.fillRect(food.getFoodX() * cellSize, food.getFoodY() * cellSize, cellSize, cellSize);
    }

    public void showSnake(SnakeState snakeState) {
        // Farve på hoved
        gc.setFill(Color.BLUE);
        gc.fillRect(snakeState.getHeadX() * cellSize, snakeState.getHeadY() * cellSize, cellSize, cellSize);
        // Farve på krop
        ArrayList<SnakeBody> body = snakeState.getBody();
        for (int i = 0; i < body.size(); i++) {
            gc.setFill(Color.BLACK);
            gc.fillRect(body.get(i + 1).getXpos() * cellSize, body.get(i).getYpos() * cellSize, cellSize, cellSize);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
