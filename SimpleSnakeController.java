import javafx.scene.input.KeyCode;

public class SimpleSnakeController {
    private SimpleSnakeView snakeView;

    public SimpleSnakeController(SimpleSnakeView snakeView) {
        this.snakeView = snakeView;

    }

    public void handleKeyPress(KeyCode keyCode, Snake snake) {
        if (keyCode == KeyCode.UP) {
            snake.setDirection('U');
        } else if (keyCode == KeyCode.DOWN) {
            snake.setDirection('D');
        } else if (keyCode == KeyCode.LEFT) {
            snake.setDirection('L');
        } else if (keyCode == KeyCode.RIGHT) {
            snake.setDirection('R');
        }

        // Opdaterer slangeinformation.
        snakeView.showSnake(snake);

    }
}
