import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import javafx.animation.Timeline;

public class Snake {
    private int headX;
    private int headY;
    private char direction;
    private ArrayList<SnakeBody> body;
    private int oldHeadX = headX;
    private int oldHeadY = headY;

    public Snake(Grid grid) { // Creates the Gamestart Snake
        int gridMiddleX = (int) Math.floor(Double.valueOf(grid.getGridSizeX() / 2));
        int gridMiddleY = (int) Math.floor(Double.valueOf(grid.getGridSizeY() / 2));
        this.headX = gridMiddleX;
        this.headY = gridMiddleY;
        this.direction = 'U';
        this.body = new ArrayList<SnakeBody>(1);
        body.add(new SnakeBody(gridMiddleX, gridMiddleY + 1));

        this.oldHeadX = gridMiddleX;
        this.oldHeadY = gridMiddleY;
    }

    public Snake(Grid grid, char direction) { // Creates Snake at random location (except the borders of the grid)
        int randXPos = ThreadLocalRandom.current().nextInt(1, grid.getGridSizeX() - 1);
        int randYPos = ThreadLocalRandom.current().nextInt(1, grid.getGridSizeY() - 1);
        this.headX = randXPos;
        this.headY = randYPos;
        this.direction = direction;
        this.body = new ArrayList<SnakeBody>(1);
        if (direction == 'L') {
            body.add(new SnakeBody(randXPos + 1, randYPos));
        } else if (direction == 'R') {
            body.add(new SnakeBody(randXPos - 1, randYPos));
        } else if (direction == 'D') {
            body.add(new SnakeBody(randXPos, randYPos - 1));
        } else {
            body.add(new SnakeBody(randXPos, randYPos + 1));
        }

        this.oldHeadX = randXPos;
        this.oldHeadY = randYPos;
    }

    // Metoder for snakkens krop og position.
    public int getHeadX() {
        return headX;
    }

    public int getHeadY() {
        return headY;
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

    public void hasEatenApple(Food food, Grid grid, SnakeBody snakeBody) {

        if (food.foodEaten(this, food, grid) == true) {

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
}
