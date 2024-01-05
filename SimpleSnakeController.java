import javafx.scene.input.KeyCode;

public class SimpleSnakeController {
    private Snake snakeModel;
    private SimpleSnakeView snakeView;

    public SimpleSnakeController(Snake snakeModel, SimpleSnakeView snakeView) {
        this.snakeModel = snakeModel;
        this.snakeView = snakeView;
    }

    public void handleKeyPress(KeyCode keyCode) {
        if (keyCode == KeyCode.UP) {
            snakeModel.setDirection('U');
        } else if (keyCode == KeyCode.DOWN) {
            snakeModel.setDirection('D');
        } else if (keyCode == KeyCode.LEFT) {
            snakeModel.setDirection('L');
        } else if (keyCode == KeyCode.RIGHT) {
            snakeModel.setDirection('R');
        }

        // Opdaterer slangeinformation.
        snakeView.showSnake(new Snake(snakeModel.getHeadX(), snakeModel.getHeadY(), snakeModel.getBody()));
    }
}
