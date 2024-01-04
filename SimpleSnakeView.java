import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SimpleSnakeView extends Application {
    private Grid grid;
    private int cellSize = 10;
    private Food food;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        grid = new Grid();
        int sceneSizeX = cellSize * grid.getGridSizeX();
        int sceneSizeY = cellSize * grid.getGridSizeY();
        Scene scene = new Scene(root, sceneSizeX, sceneSizeY, Color.WHITE);
        final Canvas canvas = new Canvas(sceneSizeX, sceneSizeY);
        // Create grid lines (Rectangles)
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (int i = 0; i < sceneSizeX; i += cellSize) {
            for (int j = 0; j < sceneSizeY; j += cellSize) {
                if ((i + j) % 20 == 0) {
                    gc.setFill(Color.DARKGREEN);
                } else {
                    gc.setFill(Color.GREEN);
                }
                gc.fillRect(i, j, cellSize, cellSize);
            }
        }
        // Snakehead
        Rectangle head = new Rectangle(cellSize, cellSize);
        head.setFill(Color.LIMEGREEN);
        gridpane.add(head, grid.getGridSizeX() / 2, grid.getGridSizeY() / 2);

        // Food
        food = new Food(grid);
        Rectangle mad = new Rectangle(cellSize, cellSize);
        mad.setFill(Color.RED);
        gridpane.add(mad, food.getFoodX(), food.getFoodY());

        Scene scene = new Scene(gridpane, sceneSizeX, sceneSizeY);

        root.getChildren().add(canvas);
        primaryStage.setTitle("JavaFX Grid Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
