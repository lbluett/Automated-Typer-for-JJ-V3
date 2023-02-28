import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import javafx.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.event.Event;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.event.EventHandler;

import javax.swing.*;

public class Main extends Application implements EventHandler<ActionEvent> {
    Button begin;
    Functionality typer;

    public static void main(String[] args) throws InterruptedException {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

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
        if (actionEvent.getSource() == begin) {
            begin.setDisable(true);
            typer = new Functionality(1,10);
            Thread th = new Thread(typer);
            th.setDaemon(true);
            th.start();


            // Once finished, re-enable button.
            typer.setOnSucceeded(test ->
                {begin.setDisable(false);});
            System.out.println(th.isAlive());
        }

        // TODO: I can suspend/unsuspend thread - maybe not the best implementation
        // or I can pause, kill thread, store the value it was up to, and start from there


    }
}

// https://unicode-org.github.io/icu-docs/apidoc/released/icu4j/

// GUI (running on parent thread essentially) -> spawn new functionality thread, and wait until that thread dies
// when it dies, make start button usable again.
// cannot do this at then the gui will not be interactable, just grey out the thing somehow until done