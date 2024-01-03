import java.util.*;

public class Grid {
    private int gridSizeX;
    private int gridSizeY;
    private char[][] grid;

    public Grid() {
        this.gridSizeX = createGrid("Please enter grid width: ");
        this.gridSizeY = createGrid("Please enter grid height: ");
        this.grid = new char[gridSizeX][gridSizeY];
    }

    public int getGridSizeX() {
        return gridSizeX;
    }

    public int getGridSizeY() {
        return gridSizeY;
    }

    public char[][] getgrid() {
        return grid;
    }

    public void updateCell(int row, int col, char direction) {
        grid[row][col] = direction;
    }

    public int createGrid(String prompt) { // Gets user input for grid width and grid height
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