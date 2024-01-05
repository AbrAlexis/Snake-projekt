import java.util.*;

import javafx.scene.canvas.GraphicsContext;

public class Snake {
    private int headX;
    private int headY;
    private char direction;
    private ArrayList<SnakeBody> body;
    private Grid grid;
    int oldHeadX = headX;
    int oldHeadY = headY;

    public Snake(Grid grid) {
        int gridMiddleX = (int) Math.floor(Double.valueOf(grid.getGridSizeX() / 2));
        int gridMiddleY = (int) Math.floor(Double.valueOf(grid.getGridSizeY() / 2));
        this.headX = gridMiddleX;
        this.headY = gridMiddleY;
        this.direction = 'L';
        this.grid = grid;
        this.body = new ArrayList<SnakeBody>(1);
        body.add(new SnakeBody(gridMiddleX, gridMiddleY + 1));
        grid.updateCell(gridMiddleX, gridMiddleY + 1, 'L');
        this.oldHeadX = gridMiddleX;
        this.oldHeadY = gridMiddleY;
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
    public void move() {
        moveBody();
        oldHeadX = headX;
        oldHeadY = headY;

        if (direction == 'U') {
            headY--;
        } else if (direction == 'D') {
            headY++;
        } else if (direction == 'L') {
            headX--;
        } else if (direction == 'R') {
            headX++;
        }

    }

    // Metode der rykker kroppen og sørger for at den følger leddet foran.
    private void moveBody() {
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

    public GraphicsContext getGraphicsContext() {
        return grid.getCanvas().getGraphicsContext2D();
    }
}
