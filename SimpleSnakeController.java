import javafx.scene.input.KeyCode;

public class SimpleSnakeController {
    private SimpleSnakeView snakeView;

    public SimpleSnakeController(SimpleSnakeView snakeView) {
        this.snakeView = snakeView;

    }

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

        // Opdaterer slangeinformation.
        snakeView.showSnake(snake);
    }

    public void torus() {

    }
}
