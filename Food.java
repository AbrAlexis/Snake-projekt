import java.util.concurrent.ThreadLocalRandom;

public class Food {
    private int foodX;
    private int foodY;

    public Food(Grid grid, Snake snake) {
        moveFood(grid, snake);
    }

    public int getFoodX() {
        return foodX;
    }

    public int getFoodY() {
        return foodY;
    }

    public void moveFood(Grid grid, Snake snake) {
        do {
            this.foodX = ThreadLocalRandom.current().nextInt(0, grid.getGridSizeX());
            this.foodY = ThreadLocalRandom.current().nextInt(0, grid.getGridSizeY());
        } while (grid.getType(this.foodX, this.foodY) == 1
                && snake.getSize() != (grid.getGridSizeX() * grid.getGridSizeY() - 1));
    }

    public boolean eatFood(Snake snake, Food food, Grid grid) {
        if (snake.getHeadX() == food.getFoodX() && snake.getHeadY() == food.getFoodY()) {
            food.moveFood(grid,snake);
            return true;
        }
        return false;
    }
}
