import javafx.scene.input.KeyCode;
import javafx.scene.Scene;
import javafx.event.*;
import javafx.scene.control.Button;

public class SimpleSnakeController {
    private SimpleSnakeView snakeView;
    private boolean gameOverFlag;

    // Constructor, initializes SnakeController with a reference to SnakeView
    public SimpleSnakeController(SimpleSnakeView snakeView) {
        this.snakeView = snakeView;
        this.gameOverFlag = false;
    }

    public boolean getGameOverFlag() {
        return gameOverFlag;
    }

    public void setGameOverFlag(Boolean value) {
        gameOverFlag = value;
    }

    // Sets up key press handler to control the snake
    public void setupKeyPressHandler(Scene scene, Snake snake, Grid grid, Food food, Button button) {
        scene.setOnKeyPressed(e -> {
            // Updates snake's tail position before movement.
            SnakeBody tail = new SnakeBody(snake.getBody().get(snake.getBody().size() - 1).getXpos(),
                    snake.getBody().get(snake.getBody().size() - 1).getYpos());
            KeyCode keyCode = e.getCode();

            handleKeyPress(keyCode, snake, grid);
            snake.hasEatenApple(food, grid, tail);
            snake.updateGrid(grid);
            food.eatFood(snake, food, grid);
            snake.selfCollision(this);
            snakeView.drawGrid(grid);
            snakeView.showFood(food);
            snakeView.showSnake(snake);
            snakeView.gameOverScreen(snake, scene);
            snakeView.resetGameButton(snake, food, scene);
        });
    }

    // Handles key presses and controls the snake's movement
    public void handleKeyPress(KeyCode keyCode, Snake snake, Grid grid) {
        char direction = snake.getDirection();

        if (keyCode == KeyCode.UP && direction != 'D') {
            snake.setDirection('U');
            snake.move(grid);
        } else if (keyCode == KeyCode.DOWN && direction != 'U') {
            snake.setDirection('D');
            snake.move(grid);
        } else if (keyCode == KeyCode.LEFT && direction != 'R') {
            snake.setDirection('L');
            snake.move(grid);
        } else if (keyCode == KeyCode.RIGHT && direction != 'L') {
            snake.setDirection('R');
            snake.move(grid);
        }
    }

    // Starts the game by hiding the start button and showing the snake and food
    public void startGame(Button button, Food food, Snake snake) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                snakeView.showFood(food);
                snakeView.showSnake(snake);
                button.setTranslateX(100000);
                button.setDisable(true);

            }
        });
    }

    // Resets the game by removing the snake body, moving the head, and updating the
    // view
    public void resetGame(Grid grid, Snake snake, Food food) {
        int size = snake.getSize();
        for (int i = size - 1; i > 0; i--) {
            // deletes all bodyparts expect one
            snake.getBody().remove(i);
        }
        // Moves the head back to the middle
        int gridMiddleX = (int) Math.floor(Double.valueOf(grid.getGridSizeX() / 2));
        int gridMiddleY = (int) Math.floor(Double.valueOf(grid.getGridSizeY() / 2));
        snake.setHeadX(gridMiddleX);
        snake.setheadY(gridMiddleY);

        snake.getBody().get(0).setXpos(gridMiddleX);
        snake.getBody().get(0).setYpos(gridMiddleY + 1);

        snake.setDirection('U');

        gameOverFlag = false; // Set the flag to true to avoid multiple calls

        snakeView.drawGrid(grid);
        snakeView.showSnake(snake);
        food.moveFood(grid, snake);
        snakeView.showFood(food);
    }
}