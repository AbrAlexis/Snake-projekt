import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.BorderPane;

public class SimpleSnakeView extends Application {
    private final int CELL_SIZE = 15;
    private SimpleSnakeController simpleSnakeController;
    private GraphicsContext gc;
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
        drawGrid(grid);

        simpleSnakeController = new SimpleSnakeController(this);
        simpleSnakeController.startGame(button, food, snake);
        simpleSnakeController.setupKeyPressHandler(scene, snake, grid, food);

        borderpane.setPadding(new Insets(sceneSizeY / 2, (sceneSizeX / 2), (sceneSizeY / 2), (sceneSizeX / 2) - 75));
        borderpane.setCenter(button);
        root.getChildren().add(borderpane);
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
