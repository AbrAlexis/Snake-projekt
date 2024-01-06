import java.util.concurrent.ThreadLocalRandom;

public class Food {
    private int foodX;
    private int foodY;

    public Food(Grid grid) {
        moveFood(grid);
    }

    public int getFoodX() {
        return foodX;
    }

    public int getFoodY() {
        return foodY;
    }

    public void moveFood(Grid grid) {
        do {
            this.foodX = ThreadLocalRandom.current().nextInt(0, grid.getGridSizeX());
            this.foodY = ThreadLocalRandom.current().nextInt(0, grid.getGridSizeY());
        } while (grid.getType(this.foodX, this.foodY) == 1);
    }

    public boolean eatFood(Snake snake, Food food, Grid grid) {
        if (snake.getHeadX() == food.getFoodX() && snake.getHeadY() == food.getFoodY()) {
            food.moveFood(grid);
            // int tail = snake.getBody().size() - 1;
            // snake.createBodypart(snake.getBody().get(tail).getXpos(),
            // snake.getBody().get(tail).getYpos());
            return true;
        }
        return false;
    }
}
