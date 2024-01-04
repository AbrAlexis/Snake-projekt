import java.util.*;

public class Snake {
    private int headX;
    private int headY;
    private char direction;
    private ArrayList<SnakeBody> body;

    public Snake(Grid grid) {
        int gridMiddleX = (int) Math.floor(Double.valueOf(grid.getGridSizeX() / 2));
        int gridMiddleY = (int) Math.floor(Double.valueOf(grid.getGridSizeY() / 2));
        this.headX = gridMiddleX;
        this.headY = gridMiddleY;
        this.direction = 'L';
        this.body = new ArrayList<SnakeBody>(1);
        body.add(new SnakeBody(gridMiddleX, gridMiddleY + 1));
        grid.updateCell(gridMiddleX, gridMiddleY + 1, 'L');
    }

    public int getHeadX() {
        return headX;
    }

    public int getHeadY() {
        return headY;
    }

    public int getSize() {
        return this.body.size();
    }

    public ArrayList<SnakeBody> getBody() {
        return this.body;
    }

    public void createBodypart(int x, int y) {
        body.add(new SnakeBody(x, y));
    }

}
