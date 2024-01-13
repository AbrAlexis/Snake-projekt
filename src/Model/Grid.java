package src.Model;

import java.util.*;
import javafx.scene.canvas.Canvas;

//Grid with constructor that generates an object of type grid.
public class Grid {
    private int gridSizeX;
    private int gridSizeY;
    private int[][] grid;

    public Grid() {
        this.gridSizeX = getGridDimension("Please enter grid width: ");
        this.gridSizeY = getGridDimension("Please enter grid height: ");
        this.grid = new int[gridSizeX][gridSizeY];
    }

    public int getGridSizeX() {
        return gridSizeX;
    }

    public int getGridSizeY() {
        return gridSizeY;
    }

    public int[][] getGrid() {
        return grid;
    }

    public int getType(int row, int col) {
        return grid[row][col];
    }

    public void updateCell(int row, int col, int type) {
        grid[row][col] = type;
    }

    // Creates grid from user inputs.
    public int getGridDimension(String prompt) {
        int size = 0;
        Scanner console = new Scanner(System.in);
        System.out.print(prompt);
        while (true) {
            String userInputNumber = console.nextLine();
            // Try catch for making sure the user only inputs integers
            try {
                size = (int) Integer.valueOf(userInputNumber);
                if (size >= 5 && size <= 100) {
                    break;
                } else {
                    System.out.print("Please enter an integer between 5 and 100: ");
                    continue;
                }
            } catch (Exception e) {
                System.out.print(" Please enter an integer between 5 and 100: ");
                continue;
            }
        }
        return size;
    }

}