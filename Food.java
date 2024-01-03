import java.util.concurrent.ThreadLocalRandom;

public class Food {
    private int foodX;
    private int foodY;

    public Food() {
        this.foodX = ThreadLocalRandom.current().nextInt(0, getGridSizeX() + 1);
        this.foodY = ThreadLocalRandom.current().nextInt(0, getGridSizeY() + 1);
    }
}
