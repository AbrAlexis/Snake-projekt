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
    private double buttonWidth;
    private double buttonHeight;
    private SimpleSnakeController simpleSnakeController;
    private GraphicsContext gc;
    private Grid grid;
    private Group root;
    private Scene scene;

    @Override
    public void start(Stage primaryStage) {
        this.grid = new Grid();
        this.root = new Group();
        Snake snake = new Snake(grid);
        Food food = new Food(grid, snake);
        simpleSnakeController = new SimpleSnakeController(this);
        this.scene = new Scene(root, CELL_SIZE * grid.getGridSizeX(), CELL_SIZE * grid.getGridSizeY(), Color.WHITE);

        Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        this.buttonWidth = grid.getGridSizeX() * CELL_SIZE / 2.0;
        this.buttonHeight = grid.getGridSizeY();
        Button startButton = createButton("Start Game");
        BorderPane borderpane = createBorderPaneInLocation("MIDDLE", "MIDDLE");
        borderpane.setCenter(startButton);
        drawGrid(grid);

        simpleSnakeController.startGame(startButton, food, snake);
        simpleSnakeController.setupKeyPressHandler(scene, snake, grid, food, startButton);

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

    public Button createButton(String text) { // Creates button with preferred size.
        Button button = new Button(text);
        button.setPrefSize(buttonWidth, buttonHeight);
        return button;
    }

    public BorderPane createBorderPaneInLocation(String verticalPlacement, String horizontalPlacement) {
        BorderPane borderPane = new BorderPane();
        double topMultiplier = 0.5;
        double rightMultiplier = 0.5;
        double bottomMultiplier = 0.5;
        double leftMultiplier = 0.5;
        if (verticalPlacement == "TOP") {
            topMultiplier = 0.20;
            bottomMultiplier = 0.80;
        } else if (verticalPlacement == "BOTTOM") {
            topMultiplier = 0.80;
            bottomMultiplier = 0.20;
        }
        if (horizontalPlacement == "LEFT") {
            leftMultiplier = 0.20;
            rightMultiplier = 0.80;
        } else if (horizontalPlacement == "RIGHT") {
            leftMultiplier = 0.80;
            rightMultiplier = 0.20;
        }
        borderPane.setPadding(new Insets((scene.getHeight() * topMultiplier) - (buttonHeight * 0.5),
                (scene.getWidth() * rightMultiplier),
                (scene.getHeight() * bottomMultiplier), (scene.getWidth() * leftMultiplier) - (buttonWidth * 0.5)));
        root.getChildren().add(borderPane);
        return borderPane;
    }

    public void resetGameButton(Snake snake, Food food, Scene scene) {
        // Create a new button
        if (simpleSnakeController.getGameOverFlag()) {
            Button resetButton = createButton("Reset Game");
            BorderPane borderpane = createBorderPaneInLocation("TOP", "");
            borderpane.setCenter(resetButton);

            // Set the button's action
            resetButton.setOnAction(e -> {
                // Reset the game here
                simpleSnakeController.resetGame(grid, snake, food);
                resetButton.setDisable(true);
                resetButton.setVisible(false); // Hide the button

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
