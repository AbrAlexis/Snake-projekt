import java.util.*;

// Represents the Snake entity in the Snake game
public class Snake {
    private int headX;
    private int headY;
    private char direction;
    private ArrayList<SnakeBody> body;
    private int oldHeadX = headX;
    private int oldHeadY = headY;

    // Constructor for creating the initial snake at the start of the game
    public Snake(Grid grid) {
        // Initialization of the snake's properties at the center of the grid
        int gridMiddleX = (int) Math.floor(Double.valueOf(grid.getGridSizeX() / 2));
        int gridMiddleY = (int) Math.floor(Double.valueOf(grid.getGridSizeY() / 2));
        this.headX = gridMiddleX;
        this.headY = gridMiddleY;
        this.direction = 'L';
        this.body = new ArrayList<SnakeBody>(1);
        body.add(new SnakeBody(gridMiddleX, gridMiddleY + 1));

        this.oldHeadX = gridMiddleX;
        this.oldHeadY = gridMiddleY;
        updateGrid(grid);
    }

    // Constructor for creating snake
    public Snake(int headX, int headY, ArrayList<SnakeBody> body) {
        this.headX = headX;
        this.headY = headY;
        this.body = body;
    }

    // Getter/Setter methods for retrieving snake properties
    public int getHeadX() {
        return headX;
    }

    public int getHeadY() {
        return headY;
    }

    public void setHeadX(int x) {
        headX = x;
    }

    public void setheadY(int y) {
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

    // Method for snakes direction
    public char getDirection() {
        return direction;
    }

    public void setDirection(char newDirection) {
        direction = newDirection;
    }

    // Method to handle snake movement on the grid
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

    // Method to move the body parts of the snake
    private void moveBody(Grid grid) {
        // Updating the positions of body parts to follow the part in front
        if (body.size() > 0) {
            for (int i = body.size() - 1; i > 0; i--) {
                SnakeBody currentBodyPart = body.get(i);
                SnakeBody previousBodyPart = body.get(i - 1);
                currentBodyPart.setXpos(previousBodyPart.getXpos());
                currentBodyPart.setYpos(previousBodyPart.getYpos());
            }
            // Makes sure the frontmost body part follows the head.
            SnakeBody firstBodyPart = body.get(0);
            firstBodyPart.setXpos(oldHeadX);
            firstBodyPart.setYpos(oldHeadY);
        }
    }

    // Method to update the grid representation based on the snake's position
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

    // Method to check for self-collision
    public boolean selfCollision(SimpleSnakeController simpleSnakeController) {
        for (int i = 0; i < body.size(); i++) {
            if (headX == body.get(i).getXpos() && headY == body.get(i).getYpos()) {
                System.out.println("collision");
                simpleSnakeController.setGameOverFlag(true);
                return true;
            }
        }
        return false;
    }

    // Method to handle snake eating an apple
    public void hasEatenApple(Food food, Grid grid, SnakeBody snakeBody) {

        if (food.eatFood(this, food, grid) == true) {
            body.add(snakeBody);
        }
    }

}
