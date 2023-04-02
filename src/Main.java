import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.awt.*;
import java.io.IOException;

public class Main extends Application {
    Button begin;
    TextField start;
    Functionality typer;

    public static void main(String[] args) throws InterruptedException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));

        stage.setTitle("JJBotv3 by InfamousTurtle");
        stage.setScene(new Scene(root, 600,400));
        stage.show();
//        begin = new Button();
//        begin.setText("Begin");
//        begin.setOnAction(this); // whenever clicked, use this class - see tute 2, can make custom class?
//
//        start = new TextField();
//
//
//        StackPane layout = new StackPane(); // customise layout
//        layout.getChildren().add(begin);
//
//        Scene scene = new Scene(layout, 300, 250);
//        stage.setScene(scene);
//        stage.show();
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