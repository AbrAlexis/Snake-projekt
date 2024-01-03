import java.util.concurrent.ThreadLocalRandom;

public class Food {
    private int foodX;
    private int foodY;
    private boolean alive;

    public Food(Grid grid) {
        this.alive = true;
        this.foodX = ThreadLocalRandom.current().nextInt(0, grid.getGridSizeX() + 1);
        this.foodY = ThreadLocalRandom.current().nextInt(0, grid.getGridSizeY() + 1);
    }

    public void eatFood() {
        this.alive = false;
    }
}
