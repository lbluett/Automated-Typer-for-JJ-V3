package com.infamous.jjbot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Parent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Main extends Application implements NativeKeyListener {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        // Set your expiration date in the format Year-Month-Day
        String expirationDate = "2023-07-20";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate expireDate = LocalDate.parse(expirationDate, formatter);

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gui.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();

        if (currentDate.isAfter(expireDate)) {
            controller.disableInput();
            controller.startStop.setDisable(true);
            stage.setTitle("JJBotv3 by InfamousTurtle - BETA Trial Expired");
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("BETA Trial Expired");
            errorAlert.setContentText("The BETA trial has expired, join the discord for more info: " +
                    "https://discord.gg/jKepBd4qxY");
            errorAlert.showAndWait();
        } else {
            stage.setTitle("JJBotv3 by InfamousTurtle - BETA Version");
            KeyListener listener = new KeyListener(controller);
            listener.registerKeyListener();
        }
        stage.setScene(new Scene(root, 600, 500));
        stage.show();


    }
}