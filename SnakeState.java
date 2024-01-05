
// SnakeState.java
import java.util.ArrayList;

public class SnakeState {
    private int headX;
    private int headY;
    private ArrayList<SnakeBody> body;

    public SnakeState(int headX, int headY, ArrayList<SnakeBody> body) {
        this.headX = headX;
        this.headY = headY;
        this.body = body;
    }

    public int getHeadX() {
        return headX;
    }

    public int getHeadY() {
        return headY;
    }

    public ArrayList<SnakeBody> getBody() {
        return body;
    }
}
