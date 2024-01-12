import java.util.concurrent.ThreadLocalRandom;

public class Food {
    private int foodX;
    private int foodY;

    // Constructor to initialize food position
    public Food(Grid grid, Snake snake) {
        moveFood(grid, snake);
    }

    public int getFoodX() {
        return foodX;
    }

    public int getFoodY() {
        return foodY;
    }

    // Method to move the food to a new random position
    public void moveFood(Grid grid, Snake snake) {
        do {
            this.foodX = ThreadLocalRandom.current().nextInt(0, grid.getGridSizeX());
            this.foodY = ThreadLocalRandom.current().nextInt(0, grid.getGridSizeY());
        } while (grid.getType(this.foodX, this.foodY) == 1
                && snake.getSize() != (grid.getGridSizeX() * grid.getGridSizeY() - 1));
    }

    // Method to check if the snake has eaten the food
    public boolean eatFood(Snake snake, Food food, Grid grid) {
        if (snake.getHeadX() == food.getFoodX() && snake.getHeadY() == food.getFoodY()) {
            food.moveFood(grid, snake); // Moves the food to a new position
            return true;
        }
        return false;
    }
}
