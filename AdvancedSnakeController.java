import javafx.scene.input.KeyCode;
import javafx.scene.Scene;

public class AdvancedSnakeController {
    private AdvancedSnakeView snakeView;

    public AdvancedSnakeController(AdvancedSnakeView snakeView) {
        this.snakeView = snakeView;
    }

    public void setupKeyPressHandler(Scene scene, Snake snake, Grid grid, Food food) {
        scene.setOnKeyPressed(e -> {
            SnakeBody tail = new SnakeBody(snake.getBody().get(snake.getBody().size() - 1).getXpos(),
                    snake.getBody().get(snake.getBody().size() - 1).getYpos());
            KeyCode keyCode = e.getCode();
            handleKeyPress(keyCode, snake, grid);
            snake.selfCollision();
            snake.hasEatenApple(food, grid, tail);
            snake.updateGrid(grid);
            food.eatFood(snake, food, grid);
            snakeView.drawGrid(grid);
            snakeView.showFood(food);
            snakeView.showSnake(snake);
        });
    }

    public void handleKeyPress(KeyCode keyCode, Snake snake, Grid grid) {
        char direction = snake.getDirection();

        if (keyCode == KeyCode.UP && direction != 'D') {
            snake.setDirection('U');
        } else if (keyCode == KeyCode.DOWN && direction != 'U') {
            snake.setDirection('D');
        } else if (keyCode == KeyCode.LEFT && direction != 'R') {
            snake.setDirection('L');
        } else if (keyCode == KeyCode.RIGHT && direction != 'L') {
            snake.setDirection('R');
        }

    }
}