import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.Font;

public class AdvancedSnakeView extends Application {
    private final int CELL_SIZE = 50;
    private AdvancedSnakeController simpleSnakeController;
    private GraphicsContext gc;
    public Timeline timeline;
    public Button button;
    public Group root;
    public BorderPane borderpane;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Grid grid = new Grid();
        Snake snake = new Snake(grid);
        Food food = new Food(grid);
        this.button = new Button("LAD OS BEGYNDE!!!!!!!!!!!!!!!!!!!");
        BorderPane borderpane = new BorderPane();

        int sceneSizeX = CELL_SIZE * grid.getGridSizeX();
        int sceneSizeY = CELL_SIZE * grid.getGridSizeY();

        Scene scene = new Scene(root, sceneSizeX, sceneSizeY, Color.WHITE);
        final Canvas canvas = new Canvas(sceneSizeX, sceneSizeY);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        primaryStage.setTitle("JavaFX Grid Example");
        primaryStage.setScene(scene);
        primaryStage.show();

        simpleSnakeController = new AdvancedSnakeController(this);
        simpleSnakeController.startGame(button, food, snake, timeline);
        simpleSnakeController.setupKeyPressHandler(scene, snake, grid, food);

<<<<<<< HEAD
        borderpane.setPadding(new Insets(sceneSizeY / 2, (sceneSizeX / 2), (sceneSizeY / 2), (sceneSizeX / 2) - 75));
        borderpane.setCenter(button);
        root.getChildren().add(borderpane);

        drawGrid(grid);

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
=======
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), event -> {
            SnakeBody tail = new SnakeBody(snake.getBody().get(snake.getBody().size() - 1).getXpos(),
                    snake.getBody().get(snake.getBody().size() - 1).getYpos());
>>>>>>> 2e599a574fd3d699eed5750eda9f6e1a431e2337
            drawGrid(grid);
            snake.move(grid);
            showFood(food);

            snake.hasEatenApple(food, grid, tail);
            showSnake(snake);
            if (snake.selfCollision()) {
                timeline.stop();
                gc.setFill(Color.RED);
                gc.setFont(new Font("Times New Roman", 30));
                gc.fillText("Game Over" + "\n Score: " + snake.getSize(), scene.getWidth() / 4, scene.getHeight() / 2);

            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void drawGrid(Grid grid) {
        for (int i = 0; i < grid.getGridSizeX(); i++) {
            for (int j = 0; j < grid.getGridSizeY(); j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(Color.DARKGREEN);
                } else {
                    gc.setFill(Color.GREEN);
                }
                gc.fillRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    public void showFood(Food food) {
        gc.setFill(Color.RED);
        gc.fillRect(food.getFoodX() * CELL_SIZE, food.getFoodY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    public void showSnake(Snake snake) {
        // Farve på hoved
        gc.setFill(Color.BLUE);
        gc.fillRect(snake.getHeadX() * CELL_SIZE, snake.getHeadY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        // Farve på krop
        ArrayList<SnakeBody> body = snake.getBody();
        for (int i = 0; i < body.size(); i++) {
            gc.setFill(Color.BLACK);
            gc.fillRect(body.get(i).getXpos() * CELL_SIZE, body.get(i).getYpos() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
