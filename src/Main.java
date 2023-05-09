import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.io.IOException;


public class Main extends Application implements NativeKeyListener {
    Button begin;
    TextField start;
    Functionality typer;

    ToggleButton startStop;




    public static void main(String[] args) throws InterruptedException {

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

// https://unicode-org.github.io/icu-docs/apidoc/released/icu4j/

// GUI (running on parent thread essentially) -> spawn new functionality thread, and wait until that thread dies
// when it dies, make start button usable again.
// cannot do this at then the gui will not be interactable, just grey out the thing somehow until done

// The object stores the current jack that its at, only starts again when you press cancel and press
// start again - otherwise the button is just play/pause

// Start, Pause, Resume, Cancel
// Can only cancel when paused


// Does the iteration of the current variable get saved when I kill the thread?

// Stop and Cancel both kill thread - cancel just resets the current variable to start.