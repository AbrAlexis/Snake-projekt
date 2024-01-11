import java.util.ArrayList;

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
import javafx.scene.text.Font;

public class AdvancedSnakeView extends Application {
    private final int CELL_SIZE = 15;
    private double buttonWidth;
    private double buttonHeight;
    private AdvancedSnakeController simpleSnakeController;
    private GraphicsContext gc;
    private Snake snake;
    private Snake worm;
    public Timeline timeline;
    private Group root;
    public Scene scene;
    private Grid grid;

    @Override
    // The main method for running the program.
    // Uses previous made methods.
    public void start(Stage primaryStage) {
        this.root = new Group();
        this.grid = new Grid();
        this.scene = new Scene(root, CELL_SIZE * grid.getGridSizeX(), CELL_SIZE * grid.getGridSizeY(), Color.WHITE);

        simpleSnakeController = new AdvancedSnakeController(this, timeline);

        if (simpleSnakeController.getMultiplayer()) {
            snake = new Snake(grid, Color.BLUE);
            worm = new Snake(grid, Color.GREY);
        } else {
            snake = new Snake(grid, Color.BEIGE);
        }

        Food food = new Food(grid, snake);

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
        simpleSnakeController.setupKeyPressHandler(scene, snake, worm, grid);

        drawGrid(grid);
        primaryStage.setTitle("Snake");
        primaryStage.setScene(scene);
        primaryStage.show();

        if (simpleSnakeController.getMultiplayer()) {
            simpleSnakeController.startGame2p(startButton, food, snake, worm);
        } else {
            simpleSnakeController.startGame(startButton, food, snake);
        }

        if (simpleSnakeController.getMultiplayer()) {
            simpleSnakeController.setUpTimeline2p(snake, worm, grid, food, scene);
        } else {
            simpleSnakeController.setUpTimeline(snake, worm, grid, food, scene);
        }
    }

    // Draws grid in checkered pattern.
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

    // Paints food on grid in chosen color.
    public void showFood(Food food) {
        gc.setFill(Color.RED);
        gc.fillRect(food.getFoodX() * CELL_SIZE, food.getFoodY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    // Paints snake in graphicscontext.
    public void showSnake(Snake snake, Color color) {
        // Paints head
        gc.setFill(color);
        gc.fillRect(snake.getHeadX() * CELL_SIZE, snake.getHeadY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        // Paints body
        ArrayList<SnakeBody> body = snake.getBody();
        for (int i = 0; i < body.size(); i++) {
            gc.setFill(Color.BLACK);
            gc.fillRect(body.get(i).getXpos() * CELL_SIZE, body.get(i).getYpos() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    public void gameOverScreen(Snake snake, Scene scene) {
        if (snake.selfCollision()) {
            simpleSnakeController.getTimeline().stop();
            gc.setFill(Color.RED);
            gc.setFont(new Font("Times New Roman", 30));
            gc.fillText("Game Over" + "\nScore: " + snake.getSize(), (grid.getGridSizeX() * CELL_SIZE) / 5,
                    scene.getHeight() / 2);

        }
    }

    // Game Over in Multiplayer.
    public void gameOverScreen2p() {
        if (snake.selfCollision() || snake.otherCollision(worm)) {
            System.out.println("Worm wins!!!");
            simpleSnakeController.getTimeline().stop();
            gc.setFill(Color.RED);
            gc.setFont(new Font("Times New Roman", 30));
            gc.fillText("Game Over", scene.getWidth() / 4, scene.getHeight() / 2);

        }

        if (worm.selfCollision() || worm.otherCollision(snake)) {
            System.out.println("Snake wins!!!");
            simpleSnakeController.getTimeline().stop();
            gc.setFill(Color.RED);
            gc.setFont(new Font("Times New Roman", 30));
            gc.fillText("Game Over", scene.getWidth() / 4, scene.getHeight() / 2);
        }
    }

    // Creates button with preferred size.
    public Button createButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(buttonWidth, buttonHeight);
        return button;
    }

    // Method for placing BorderPane in 9 locations.
    // If empty, placed in middle.
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

    // Creates a reset button
    public void resetGameButton(Snake snake, Snake worm, Food food, Scene scene) {
        if (simpleSnakeController.getGameOver() == true) {
            Button resetButton = createButton("Reset Game");
            BorderPane borderpane = createBorderPaneInLocation("TOP", "");
            borderpane.setCenter(resetButton);

            // Set the button's action
            resetButton.setOnAction(e -> {
                simpleSnakeController.resetGame(grid, snake, worm, food);
                simpleSnakeController.setGameOver(false);
                resetButton.setDisable(true);
                resetButton.setVisible(false); // Hide the button

            });
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
