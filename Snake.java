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

    public Snake(Grid grid, Color color) { // Creates the Gamestart Snake
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

    public Snake(Grid grid, int middleOffsetX, Color color) { // Creates Snake at random location (except the borders of
                                                              // the grid)
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

    // Metoder for snakkens krop og position.
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

    // Metoder til retningen for slangen.
    public char getDirection() {
        return direction;
    }

    public void setDirection(char newDirection) {
        direction = newDirection;
    }

    public Color getColor() {
        return color;
    }

    // Metode til at opdatere slangebevægelse baseret på retningen.
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

    // Metode der rykker kroppen og sørger for at den følger leddet foran.
    private void moveBody(Grid grid) {
        if (body.size() > 0) {
            for (int i = body.size() - 1; i > 0; i--) {
                SnakeBody currentBodyPart = body.get(i);
                SnakeBody previousBodyPart = body.get(i - 1);
                currentBodyPart.setXpos(previousBodyPart.getXpos());
                currentBodyPart.setYpos(previousBodyPart.getYpos());
            }
            // Opdatering der sørger for at den forreste kropsdel følger hovedet.
            SnakeBody firstBodyPart = body.get(0);
            firstBodyPart.setXpos(oldHeadX);
            firstBodyPart.setYpos(oldHeadY);
        }
    }

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

    public boolean selfCollision() {
        for (int i = 0; i < body.size(); i++) {
            if (headX == body.get(i).getXpos() && headY == body.get(i).getYpos()) {
                return true;
            }
        }
        return false;
    }

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

    public void hasEatenApple(Food food, Grid grid, SnakeBody snakeBody) {

        if (food.foodEaten(this, grid) == true) {

            body.add(snakeBody);
        }
    }

    public boolean isVictorious(Snake snake, Grid grid) {
        if (grid.getGridSizeX() * grid.getGridSizeY() == snake.getSize() + 1) {
            System.out.println("victory");
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
