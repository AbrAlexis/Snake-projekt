import java.util.*;

public class Grid {
    private int gridSizeX;
    private int gridSizeY;
    private boolean[][] grid;

    public Grid(int gridSizeX, int gridSizeY) {

        this.grid = new boolean[gridSizeX][gridSizeY];

    }

    public int getGridSizeX() {

        return gridSizeX;
    }

    public int getGridSizeY() {
        return gridSizeY;
    }

    public int[] createGrid(){
        return {xSize, ySize};
    }

}