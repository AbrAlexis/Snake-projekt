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
        this.foodX = ThreadLocalRandom.current().nextInt(0, grid.getGridSizeX() + 1);
        this.foodY = ThreadLocalRandom.current().nextInt(0, grid.getGridSizeY() + 1);
    }
}
