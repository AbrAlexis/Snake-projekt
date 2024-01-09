import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.*;
import javafx.scene.control.Button;

public class AdvancedSnakeController {
    private AdvancedSnakeView snakeView;
    private EventHandler<ActionEvent> eventHandler;
    private Timeline timeline;
    private char lastDirection;

    public AdvancedSnakeController(AdvancedSnakeView snakeView, Timeline timeline) {
        this.snakeView = snakeView;
        this.timeline = timeline;
    }

    public void setupKeyPressHandler(Scene scene, Snake snake, Grid grid, Food food) {
        scene.setOnKeyPressed(e -> {
            KeyCode keyCode = e.getCode();
            handleKeyPress(keyCode, snake, grid, lastDirection);
        });
    }

    public void setUpTimeline(Snake snake, Grid grid, Food food) {
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {

            SnakeBody tail = new SnakeBody(snake.getBody().get(snake.getBody().size() - 1).getXpos(),
                    snake.getBody().get(snake.getBody().size() - 1).getYpos());
            lastDirection = snake.getDirection();
            snake.move(grid);
            snake.hasEatenApple(food, grid, tail);
            snake.updateGrid(grid);
            food.foodEaten(snake, food, grid);
            snake.selfCollision();
            snakeView.drawGrid(grid);
            snakeView.showFood(food);
            snakeView.showSnake(snake);
            if (snake.isVictorious(snake, grid) == true) {
                this.timeline.stop();
            }
            snakeView.gameOverScreen(snake, snakeView.scene);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);

    }

    public void handleKeyPress(KeyCode keyCode, Snake snake, Grid grid, char lastDirection) {

        if (keyCode == KeyCode.UP && lastDirection != 'D') {
            snake.setDirection('U');
        } else if (keyCode == KeyCode.DOWN && lastDirection != 'U') {
            snake.setDirection('D');
        } else if (keyCode == KeyCode.LEFT && lastDirection != 'R') {
            snake.setDirection('L');
        } else if (keyCode == KeyCode.RIGHT && lastDirection != 'L') {
            snake.setDirection('R');
        }
    }

    public void startGame(Button button, Food food, Snake snake) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                snakeView.showFood(food);
                snakeView.showSnake(snake);
                timeline.play();
                button.setTranslateX(100000);
                button.setDisable(true);

            }
        });
    }

    public Timeline getTimeline() {
        return timeline;
    }
}