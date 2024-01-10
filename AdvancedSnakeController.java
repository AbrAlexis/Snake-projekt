import java.util.HashMap;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
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
    private boolean gameOver;

    public AdvancedSnakeController(AdvancedSnakeView snakeView, Timeline timeline) {
        this.snakeView = snakeView;
        this.timeline = timeline;
        this.multiplayer = decideMultiplayer();
    }

    public void setupKeyPressHandler(Scene scene, Snake snake, Snake worm, Grid grid) {
        scene.setOnKeyPressed(e -> {
            KeyCode keyCode = e.getCode();
            if (multiplayer == true) {
                handleKeyPressSnake(keyCode, snake, grid);
                handleKeyPressWorm(keyCode, worm, grid);
            } else {
                handleKeyPressSnake(keyCode, snake, grid);
            }

        });
    }

    public void setUpTimeline(Snake snake, Snake worm, Grid grid, Food food, Scene scene) {
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(0.2), event -> {

            SnakeBody tail = new SnakeBody(snake.getBody().get(snake.getBody().size() - 1).getXpos(),
                    snake.getBody().get(snake.getBody().size() - 1).getYpos());
            snakeLastDirection = snake.getDirection();
            snake.setToBufferDirection();
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
            snakeView.resetGameButton(snake, worm, food, scene);

            isGameOver(snake, worm);

            snakeView.gameOverScreen(snake, snakeView.scene);
            snakeView.resetGameButton(snake, worm, food, scene);

        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void setUpTimeline2p(Snake snake, Snake worm, Grid grid, Food food, Scene scene) {

        this.timeline = new Timeline(new KeyFrame(Duration.seconds(0.2), event -> {

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

            isGameOver(snake, worm);

            snakeView.gameOverScreen(snake, snakeView.scene);
            snakeView.resetGameButton(snake, worm, food, scene);
            System.out.println(gameOver);

        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void handleKeyPressSnake(KeyCode keyCode, Snake snake, Grid grid) {
        Map<KeyCode, Character> keyCodeDict = new HashMap<>();
        // Add key-value pairs to the dictionary
        keyCodeDict.put(KeyCode.UP, 'U');
        keyCodeDict.put(KeyCode.DOWN, 'D');
        keyCodeDict.put(KeyCode.LEFT, 'L');
        keyCodeDict.put(KeyCode.RIGHT, 'R');

        Map<KeyCode, Character> reverseDirecDict = new HashMap<>();
        // Add key-value pairs to the dictionary
        reverseDirecDict.put(KeyCode.UP, 'D');
        reverseDirecDict.put(KeyCode.DOWN, 'U');
        reverseDirecDict.put(KeyCode.LEFT, 'R');
        reverseDirecDict.put(KeyCode.RIGHT, 'L');

        int lastBufferDirectionIndex = snake.getBuffer().size() - 1;

        if (keyCodeDict.containsKey(keyCode)) {
            if (snake.getBuffer().size() == 0) {
                if (snakeLastDirection != reverseDirecDict.get(keyCode)) {
                    snake.setDirection(keyCodeDict.get(keyCode));
                    snake.getBuffer().add(keyCodeDict.get(keyCode));
                }
            } else if (snake.getBuffer().get(lastBufferDirectionIndex) != reverseDirecDict.get(keyCode)) {
                snake.getBuffer().add(keyCodeDict.get(keyCode));
            }

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

    public void resetGame(Grid grid, Snake snake, Snake worm, Food food) {
        if (gameOver == true) {
            int snakeSize = snake.getSize();
            for (int i = snakeSize - 1; i > 0; i--) {
                // deletes all but one of snakes bodypart
                snake.getBody().remove(i);
            }
            if (multiplayer == true) {
                int wormSize = worm.getSize();
                for (int i = wormSize - 1; i > 0; i--) {
                    worm.getBody().remove(i);
                }
            }
            // Moves the head back to the middle
            int gridMiddleX = (int) Math.floor(Double.valueOf(grid.getGridSizeX() / 2));
            int gridMiddleY = (int) Math.floor(Double.valueOf(grid.getGridSizeY() / 2));
            if (multiplayer == true) {
                snake.setHeadX(gridMiddleX + 2);
            } else {
                snake.setHeadX(gridMiddleX);
            }

            snake.setHeadY(gridMiddleY);

            if (multiplayer == true) {
                System.out.println("wtf!!!!");
                worm.setHeadX(gridMiddleX - 2);
                worm.setHeadY(gridMiddleY);
            }
            snake.getBody().get(0).setXpos(gridMiddleX);
            snake.getBody().get(0).setYpos(gridMiddleY + 1);

            if (multiplayer == true) {
                worm.getBody().get(0).setXpos(gridMiddleX - 2);
                worm.getBody().get(0).setYpos(gridMiddleY + 1);
            }
            snake.setDirection('U');
            if (multiplayer == true) {
                worm.setDirection('U');
            }
            // gameOverFlag = false; // Set the flag to true to avoid multiple calls

            snakeView.drawGrid(grid);
            snakeView.showSnake(snake, snake.getColor());
            if (multiplayer == true) {
                snakeView.showSnake(worm, worm.getColor());
            }
            food.moveFood(grid, snake);
            snakeView.showFood(food);
            timeline.play();
        }
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public boolean getMultiplayer() {
        return multiplayer;
    }

    public void isGameOver(Snake snake, Snake worm) {
        if (multiplayer == true) {
            if (snake.selfCollision() || worm.selfCollision() || snake.otherCollision(worm)
                    || worm.otherCollision(snake)) {
                gameOver = true;
                timeline.stop();
            }
        } else {
            if (snake.selfCollision()) {
                gameOver = true;
                timeline.stop();
            }

        }
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(Boolean value) {
        gameOver = value;
    }
}