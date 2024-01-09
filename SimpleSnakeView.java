import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.BorderPane;

public class SimpleSnakeView extends Application {
    private final int CELL_SIZE = 15;
    private SimpleSnakeController simpleSnakeController;
    private GraphicsContext gc;
    private Grid grid;
    public Button button;
    public Group root;
    public BorderPane borderpane;

    @Override
    public void start(Stage primaryStage) {
        this.grid = new Grid();

        Group root = new Group();
        Snake snake = new Snake(grid);
        Food food = new Food(grid, snake);
        this.button = new Button("LAD OS BEGYNDE!!!!!!!!!!!!!!!!!!!");
        this.borderpane = new BorderPane();

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

        borderpane.setPadding(new Insets(sceneSizeY / 2, (sceneSizeX / 2),
                (sceneSizeY / 2), (sceneSizeX / 2) - 75));
        borderpane.setCenter(button);
        root.getChildren().add(borderpane);
        drawGrid(grid);
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

    public void resetGameButton(Snake snake, Food food) {
        // Create a new button
        if (simpleSnakeController.getGameOverFlag()) {
            Button resetButton = new Button("Reset Game");

            // Set the button's action
            resetButton.setOnAction(e -> {
                // Reset the game here
                simpleSnakeController.resetGame(grid, snake, food);
                resetButton.setDisable(true);
                resetButton.setVisible(false); // Hide the button

            });

            int sceneSizeX = CELL_SIZE * grid.getGridSizeX();
            int sceneSizeY = CELL_SIZE * grid.getGridSizeY();
            // Add the button to the center of the borderpane

            borderpane.setTop(resetButton);

            resetButton.setTranslateX(((sceneSizeX / 4)));
            resetButton.setTranslateY(-(sceneSizeY / 2));
            // root.getChildren().add(borderpane);
        }
    }

    // Metode der tjekker selfcollision og derefter printer "Game Over".
    public void gameOverScreen(Snake snake, Scene scene) {
        int size;
        int minGridSize = Math.min(grid.getGridSizeX(), grid.getGridSizeY());
        size = Math.min(Math.max(5, minGridSize * CELL_SIZE / 6), 75);
        if (simpleSnakeController.getGameOverFlag() == true) {

            gc.setFill(Color.BLUE);
            gc.setFont(new Font("Times New Roman", size));
            gc.fillText("Game Over" + "\n Score: " + snake.getBody().size(),
                    scene.getWidth() / 8,
                    scene.getHeight() / 2);

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
