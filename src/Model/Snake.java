package Model;
import java.util.*;

import javafx.scene.paint.Color;

public class Snake {
    private int headX;
    private int headY;
    private char direction;
    private ArrayList<SnakeBody> body;
    private ArrayList<Character> movementBuffer;
    private int oldHeadX = headX;
    private int oldHeadY = headY;
    private Color color;

    // Creates the Gamestart Snake
    public Snake(Grid grid, Color color) {
        int gridMiddleX = (int) Math.floor(Double.valueOf(grid.getGridSizeX() / 2) - 1);
        int gridMiddleY = (int) Math.floor(Double.valueOf(grid.getGridSizeY() / 2));
        this.headX = gridMiddleX;
        this.headY = gridMiddleY;
        this.direction = 'U';
        this.body = new ArrayList<SnakeBody>(1);
        body.add(new SnakeBody(gridMiddleX, gridMiddleY + 1));

        this.oldHeadX = gridMiddleX;
        this.oldHeadY = gridMiddleY;

        this.color = color;

        this.movementBuffer = new ArrayList<Character>(0);

    }

    public Snake(Grid grid, int middleOffsetX, Color color) {
        int gridMiddleX = (int) Math.floor(Double.valueOf(grid.getGridSizeX() / 2) + middleOffsetX);
        int gridMiddleY = (int) Math.floor(Double.valueOf(grid.getGridSizeY() / 2));
        this.headX = gridMiddleX;
        this.headY = gridMiddleY;
        this.direction = 'U';
        this.body = new ArrayList<SnakeBody>(1);
        body.add(new SnakeBody(gridMiddleX, gridMiddleY + 1));

        this.oldHeadX = gridMiddleX;
        this.oldHeadY = gridMiddleY;

        this.color = color;

        this.movementBuffer = new ArrayList<Character>(0);
    }

    public int getHeadX() {
        return headX;
    }

    public int getHeadY() {
        return headY;
    }

    public void setHeadX(int x) {
        headX = x;
    }

    public void setHeadY(int y) {
        headY = y;
    }

    public int getSize() {
        return this.body.size();
    }

    public ArrayList<SnakeBody> getBody() {
        return this.body;
    }

    public void createBodypart(int x, int y) {
        body.add(new SnakeBody(x, y));
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char newDirection) {
        direction = newDirection;
    }

    public Color getColor() {
        return color;
    }

    // Updates snakemovement based on direction
    public void move(Grid grid) {
        oldHeadX = headX;
        oldHeadY = headY;
        // The first four statements handle the torus aspect
        if (oldHeadY == 0 && direction == 'U') {
            headY = grid.getGridSizeY() - 1;
        } else if (oldHeadY == grid.getGridSizeY() - 1 && direction == 'D') {
            headY = 0;
        } else if (oldHeadX == 0 && direction == 'L') {
            headX = grid.getGridSizeX() - 1;
        } else if (oldHeadX == grid.getGridSizeX() - 1 && direction == 'R') {
            headX = 0;
            // These four statements handle general movement
        } else if (direction == 'U') {
            headY--;
        } else if (direction == 'D') {
            headY++;
        } else if (direction == 'L') {
            headX--;
        } else if (direction == 'R') {
            headX++;
        }
        moveBody(grid);
    }

    // Moves body and makes sure the bodypart follows the previous one.
    private void moveBody(Grid grid) {
        if (body.size() > 0) {
            for (int i = body.size() - 1; i > 0; i--) {
                SnakeBody currentBodyPart = body.get(i);
                SnakeBody previousBodyPart = body.get(i - 1);
                currentBodyPart.setXpos(previousBodyPart.getXpos());
                currentBodyPart.setYpos(previousBodyPart.getYpos());
            }
            // The frontmost bodypart follows head.
            SnakeBody firstBodyPart = body.get(0);
            firstBodyPart.setXpos(oldHeadX);
            firstBodyPart.setYpos(oldHeadY);
        }
    }

    // Resets grid value and updates snakes coordinates.
    public void updateGrid(Grid grid) {
        for (int i = 0; i < grid.getGridSizeX(); i++) {
            for (int j = 0; j < grid.getGridSizeY(); j++) {
                grid.updateCell(i, j, 0);
            }
        }
        grid.updateCell(headX, headY, 1);
        for (int i = 0; i < body.size(); i++) {
            grid.updateCell(body.get(i).getXpos(), body.get(i).getYpos(), 1);
        }
    }

    // Checks for selfcollision
    public boolean selfCollision() {
        for (int i = 0; i < body.size(); i++) {
            if (headX == body.get(i).getXpos() && headY == body.get(i).getYpos()) {
                return true;
            }
        }
        return false;
    }

    // Checks for othercollision in multiplayer.
    public boolean otherCollision(Snake other) {

        if (headX == other.getHeadX() && headY == other.getHeadY()) {
            return true;
        }
        for (int i = 0; i <= other.getSize() - 1; i++) {
            if (headX == other.getBody().get(i).getXpos() && headY == other.getBody().get(i).getYpos()) {

                return true;
            }
        }
        return false;
    }

    // Method for eating apple.
    public void hasEatenApple(Food food, Grid grid, SnakeBody snakeBody) {

        if (food.foodEaten(this, grid) == true) {

            body.add(snakeBody);
        }
    }

    // Method for winning game
    public boolean isVictorious(Snake snake, Grid grid) {
        if (grid.getGridSizeX() * grid.getGridSizeY() == snake.getSize() + 1) {
            System.out.println("Victory");
            return true;
        } else {
            return false;
        }
    }

    public void setToBufferDirection() {
        if (movementBuffer.size() > 0) {
            direction = movementBuffer.get(0);
            movementBuffer.remove(0);
        }
    }

    public ArrayList<Character> getBuffer() {
        return movementBuffer;
    }

}
