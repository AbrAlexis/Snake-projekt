import java.util.concurrent.ThreadLocalRandom;

public class Food {
    private int foodX;
    private int foodY;

    public Food(Grid grid, Snake snake) {
        moveFood(grid, snake);
    }

    public Food(Grid grid) {
        moveFood2p(grid);
    }

    public int getFoodX() {
        return foodX;
    }

    public int getFoodY() {
        return foodY;
    }

    // Moves food in singleplayer.
    public void moveFood(Grid grid, Snake snake) {
        do {
            this.foodX = ThreadLocalRandom.current().nextInt(0, grid.getGridSizeX());
            this.foodY = ThreadLocalRandom.current().nextInt(0, grid.getGridSizeY());
        } while (grid.getType(this.foodX, this.foodY) == 1
                && snake.isVictorious(snake, grid) == false);
    }

    public void moveFood2p(Grid grid) {
        do {
            this.foodX = ThreadLocalRandom.current().nextInt(0, grid.getGridSizeX());
            this.foodY = ThreadLocalRandom.current().nextInt(0, grid.getGridSizeY());
        } while (grid.getType(this.foodX, this.foodY) == 1);

    }

    public boolean foodEaten(Snake snake, Grid grid) {
        if (snake.getHeadX() == getFoodX() && snake.getHeadY() == getFoodY()) {
            moveFood(grid, snake);
            return true;
        }
        return false;
    }
}
