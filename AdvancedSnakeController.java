import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.*;
import javafx.scene.control.Button;

public class AdvancedSnakeController {
    private AdvancedSnakeView snakeView;
    private EventHandler<ActionEvent> eventHandler;

    public AdvancedSnakeController(AdvancedSnakeView snakeView) {
        this.snakeView = snakeView;
    }

    public void setupKeyPressHandler(Scene scene, Snake snake, Grid grid, Food food) {
        scene.setOnKeyPressed(e -> {
            KeyCode keyCode = e.getCode();
            handleKeyPress(keyCode, snake, grid);
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

    public void startGame(Button button, Food food, Snake snake, Timeline timeline) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                snakeView.showFood(food);
                snakeView.showSnake(snake);
                snakeView.timeline.play();
                button.setTranslateX(100000);
                button.setDisable(true);
                snakeView.root.getChildren().remove(snakeView.borderpane);

            }
        });
    }

}