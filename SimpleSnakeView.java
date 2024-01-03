import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SimpleSnakeView extends Application {

    private SimpleSnakeModel Model;
    private SimpleSnakeController Controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("TicTacToe");

        // FXMLLoader loader = new FXMLLoader(getClass().getResource("Example.fxml"));
        // Parent root = loader.load();
        // Scene scene = new Scene(root);
        primaryStage.setScene(null); // null will be changed to root once, we have an example fxml file to use
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
