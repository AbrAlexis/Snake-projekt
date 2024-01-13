package src.Model;

public class SnakeBody {
    private int xPos;
    private int yPos;

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
