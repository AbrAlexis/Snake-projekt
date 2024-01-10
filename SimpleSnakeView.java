import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

public class SimpleSnakeView extends Application {
    private final int CELL_SIZE = 15;
    private final double buttonWidth = CELL_SIZE * 8;
    private final double buttonHeight = CELL_SIZE * 3;
    private final double smallButtonWidth = buttonWidth / 2.0;
    private final double smallButtonHeight = buttonHeight / 2.0;
    private SimpleSnakeController simpleSnakeController;
    private GraphicsContext gc;
    private Grid grid;
    private Button button;
    private BorderPane borderpane;
    private Group root;
    private Scene scene;

    @Override
    public void start(Stage primaryStage) {
        this.grid = new Grid();
        this.root = new Group();
        Snake snake = new Snake(grid);
        Food food = new Food(grid, snake);
        simpleSnakeController = new SimpleSnakeController(this);
        scene = new Scene(root, CELL_SIZE * grid.getGridSizeX(), CELL_SIZE * grid.getGridSizeY(), Color.WHITE);

        Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        createBorderPaneWithButton(button, scene, "Start Game");
        drawGrid(grid);

        simpleSnakeController.startGame(button, food, snake);
        simpleSnakeController.setupKeyPressHandler(scene, snake, grid, food, button);

        primaryStage.setTitle("Snake");
        primaryStage.setScene(scene);
        primaryStage.show();
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

    public void createBorderPaneWithButton(Button button, Scene scene, String text) {
        button = new Button(text);

        if (grid.getGridSizeX() < 10) {
            this.button.setPrefSize(smallButtonWidth, smallButtonHeight);
        } else {
            this.button.setPrefSize(buttonWidth, buttonHeight);
        }

        this.borderpane = new BorderPane(); // Pane for button is created
        if (grid.getGridSizeX() < 10) {
            borderpane.setPadding(new Insets((scene.getHeight() / 2) - (smallButtonHeight / 2), (scene.getWidth() / 2),
                    (scene.getHeight() / 2), (scene.getWidth() / 2) - (smallButtonWidth / 2)));
        } else {
            borderpane.setPadding(new Insets((scene.getHeight() / 2) - (buttonHeight / 2), (scene.getWidth() / 2),
                    (scene.getHeight() / 2), (scene.getWidth() / 2) - (buttonWidth / 2)));
        }
        borderpane.setCenter(button);
        root.getChildren().add(borderpane);
    }

    public void resetGameButton(Snake snake, Food food, Scene scene, Button button) {
        // Create a new button
        if (simpleSnakeController.getGameOverFlag()) {
            createBorderPaneWithButton(button, scene, "Reset Game");

            // Set the button's action
            button.setOnAction(e -> {
                // Reset the game here
                simpleSnakeController.resetGame(grid, snake, food);
                button.setDisable(true);
                button.setVisible(false); // Hide the button

            });
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
