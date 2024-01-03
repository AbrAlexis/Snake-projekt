import java.util.concurrent.ThreadLocalRandom;

public class Food {
    private int foodX;
    private int foodY;
    private boolean alive;

    public Food() {
        this.alive = true;
        this.foodX = ThreadLocalRandom.current().nextInt(0, getGridSizeX() + 1);
        this.foodY = ThreadLocalRandom.current().nextInt(0, getGridSizeY() + 1);
    }

    public void eatFood(){
        this.alive = false;
    }
}
