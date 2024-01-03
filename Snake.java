
public class Snake {
    private int headX;
    private int headY;
    private char direction;
    private int bodyLength;

    public Snake(Grid grid) {
        int gridMiddleX = (int) Math.floor(Double.valueOf(grid.getGridSizeX() / 2));
        int gridMiddleY = (int) Math.floor(Double.valueOf(grid.getGridSizeY() / 2));
        this.headX = gridMiddleX;
        this.headY = gridMiddleY;
        this.direction = 'L';
        this.bodyLength = 1;
        grid.updateCell(gridMiddleX, gridMiddleY + 1, 'L');
    }

}
