// Represents a body part of the snake in the Snake game
public class SnakeBody {
    private int xPos;
    private int yPos;

    // Constructor to initialize the snake body part with given coordinates
    public SnakeBody(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    public int getXpos() {
        return xPos;
    }

    public int getYpos() {
        return yPos;
    }

    public void setXpos(int x) {
        this.xPos = x;
    }

    public void setYpos(int y) {
        this.yPos = y;
    }

}
