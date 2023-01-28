import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import javafx.event.ActionEvent;
import java.awt.event.KeyEvent;

import javafx.application.Application;
import javafx.event.Event;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.event.EventHandler;

public class Main extends Application implements EventHandler<ActionEvent> {
    Button begin;
    Functionality program = new Functionality();


    public static void main(String[] args) throws InterruptedException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("JJBotv3 by InfamousTurtle");
        begin = new Button();
        begin.setText("Begin");

        begin.setOnAction(this); // whenever clicked, use this class - see tute 2, can make custom class?

        StackPane layout = new StackPane(); // customise layout
        layout.getChildren().add(begin);

        Scene scene = new Scene(layout, 300, 250);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (actionEvent.getSource()==begin) {
            program.runner(1, 10);
        }

    }
}

// https://unicode-org.github.io/icu-docs/apidoc/released/icu4j/