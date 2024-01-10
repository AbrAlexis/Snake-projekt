import java.util.HashMap;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.Scene;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.*;
import javafx.scene.control.Button;

public class AdvancedSnakeController {
    private AdvancedSnakeView snakeView;
    private EventHandler<ActionEvent> eventHandler;
    private Timeline timeline;
    private char snakeLastDirection;
    private char wormLastDirection;
    private boolean multiplayer;

    public AdvancedSnakeController(AdvancedSnakeView snakeView, Timeline timeline) {
        this.snakeView = snakeView;
        this.timeline = timeline;
        this.multiplayer = decideMultiplayer();
    }

    public void setupKeyPressHandler(Scene scene, Snake snake, Snake worm, Grid grid, Food food) {
        scene.setOnKeyPressed(e -> {
            KeyCode keyCode = e.getCode();
            handleKeyPressSnake(keyCode, snake, grid);
            handleKeyPressWorm(keyCode, worm, grid);
        });
    }

    public void setUpTimeline(Snake snake, Grid grid, Food food) {
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {

            SnakeBody tail = new SnakeBody(snake.getBody().get(snake.getBody().size() - 1).getXpos(),
                    snake.getBody().get(snake.getBody().size() - 1).getYpos());
            snakeLastDirection = snake.getDirection();
            snake.move(grid);
            snake.hasEatenApple(food, grid, tail);
            snake.updateGrid(grid);
            food.foodEaten(snake, grid);
            snake.selfCollision();
            snakeView.drawGrid(grid);
            snakeView.showFood(food);
            snakeView.showSnake(snake, snake.getColor());
            if (snake.isVictorious(snake, grid) == true) {
                this.timeline.stop();
            }
            snakeView.gameOverScreen(snake, snakeView.scene);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void setUpTimeline2p(Snake snake, Snake worm, Grid grid, Food food) {

        this.timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {

            SnakeBody snakeTail = new SnakeBody(snake.getBody().get(snake.getBody().size() - 1).getXpos(),
                    snake.getBody().get(snake.getBody().size() - 1).getYpos());
            this.snakeLastDirection = snake.getDirection();

            SnakeBody wormTail = new SnakeBody(worm.getBody().get(worm.getBody().size() - 1).getXpos(),
                    worm.getBody().get(worm.getBody().size() - 1).getYpos());
            this.wormLastDirection = worm.getDirection();

            snake.setToBufferDirection();
            worm.setToBufferDirection();

            snake.move(grid);
            worm.move(grid);

            snake.hasEatenApple(food, grid, snakeTail);
            worm.hasEatenApple(food, grid, wormTail);

            snake.updateGrid(grid);
            worm.updateGrid(grid);

            food.foodEaten(snake, grid);
            food.foodEaten(worm, grid);

            snake.selfCollision();
            worm.selfCollision();

            snake.otherCollision(worm);
            worm.otherCollision(snake);

            snakeView.gameOverScreen2p();

            snakeView.drawGrid(grid);

            snakeView.showFood(food);

            snakeView.showSnake(snake, snake.getColor());
            snakeView.showSnake(worm, worm.getColor());

        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void handleKeyPressSnake(KeyCode keyCode, Snake snake, Grid grid) {
        if (keyCode == KeyCode.UP && snakeLastDirection != 'D') {
            snake.setDirection('U');
        } else if (keyCode == KeyCode.DOWN && snakeLastDirection != 'U') {
            snake.setDirection('D');
        } else if (keyCode == KeyCode.LEFT && snakeLastDirection != 'R') {
            snake.setDirection('L');
        } else if (keyCode == KeyCode.RIGHT && snakeLastDirection != 'L') {
            snake.setDirection('R');
        }
    }

    public void handleKeyPressWorm(KeyCode keyCode, Snake snake, Grid grid) {
        Map<KeyCode, Character> keyCodeDict = new HashMap<>();
        // Add key-value pairs to the dictionary
        keyCodeDict.put(KeyCode.W, 'U');
        keyCodeDict.put(KeyCode.S, 'D');
        keyCodeDict.put(KeyCode.A, 'L');
        keyCodeDict.put(KeyCode.D, 'R');

        Map<KeyCode, Character> reverseDirecDict = new HashMap<>();
        // Add key-value pairs to the dictionary
        reverseDirecDict.put(KeyCode.W, 'D');
        reverseDirecDict.put(KeyCode.S, 'U');
        reverseDirecDict.put(KeyCode.A, 'R');
        reverseDirecDict.put(KeyCode.D, 'L');

        Map<Character, Character> charReverseDirecDict = new HashMap<>();
        // Add key-value pairs to the dictionary

        int lastBufferDirectionIndex = snake.getBuffer().size() - 1;

        if (keyCodeDict.containsKey(keyCode)) {
            if (snake.getBuffer().size() == 0) {
                if (wormLastDirection != reverseDirecDict.get(keyCode)) {
                    snake.setDirection(keyCodeDict.get(keyCode));
                    snake.getBuffer().add(keyCodeDict.get(keyCode));
                }
            } else if (snake.getBuffer().get(lastBufferDirectionIndex) != reverseDirecDict.get(keyCode)) {
                snake.getBuffer().add(keyCodeDict.get(keyCode));
            }

        }
    }

    public void startGame(Button button, Food food, Snake snake) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                snakeView.showFood(food);
                snakeView.showSnake(snake, snake.getColor());
                timeline.play();
                button.setTranslateX(100000);
                button.setDisable(true);

            }
        });
    }

    public void startGame2p(Button button, Food food, Snake snake, Snake worm) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                snakeView.showFood(food);
                snakeView.showSnake(snake, snake.getColor());
                snakeView.showSnake(worm, worm.getColor());
                timeline.play();
                button.setTranslateX(100000);
                button.setDisable(true);
            }
        });
    }

    public boolean decideMultiplayer() {
        Scanner console = new Scanner(System.in);
        String userInputNumber;
        int multiplayer;

        System.out.println("Type 1 for singleplayer or type 2 for multiplayer: ");
        while (true) {
            userInputNumber = console.nextLine();
            try {
                multiplayer = Integer.valueOf(userInputNumber);
                if (multiplayer != 1 && multiplayer != 2) {
                    System.out.println("Please enter either 1 or 2:  ");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Please enter either 1 or 2: ");
            }
        }
        if (multiplayer == 2) {
            return true;
        } else {
            return false;
        }
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public boolean getMultiplayer() {
        return multiplayer;
    }

}