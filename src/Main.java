import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import javafx.scene.Parent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import java.awt.*;



public class Main extends Application implements NativeKeyListener {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gui.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();

        stage.setTitle("JJBotv3 by InfamousTurtle");

        stage.setScene(new Scene(root, 600, 500));
        stage.show();

        KeyListener listener = new KeyListener(controller);
        listener.registerKeyListener();

    }
}