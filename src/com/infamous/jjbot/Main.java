package com.infamous.jjbot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.Parent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;


public class Main extends Application implements NativeKeyListener {
    public static void main(String[] args) {
        launch(args);
    }

    public void donatePrompt(Controller controller) {
        Alert donateAlert = new Alert(Alert.AlertType.CONFIRMATION);
        donateAlert.setHeaderText("Support the JJBot Project");
        donateAlert.setContentText("Support the JJBot Project by buying clothing in my group. All clothing 5 ROBUX!");

        Optional<ButtonType> result = donateAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            // User chose yes, run your function here
            try {
                controller.groupLink();
            } catch (URISyntaxException | IOException e) {
                System.out.println("URI Exception Thrown");
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {

        // Set your expiration date in the format Year-Month-Day
        String expirationDate = "2023-07-20";
        boolean betaTest = false;
        float donatePrompt = new Random().nextFloat();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate expireDate = LocalDate.parse(expirationDate, formatter);

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gui.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();

        if (currentDate.isAfter(expireDate) && betaTest) {
            controller.disableInput();
            controller.startStop.setDisable(true);
            stage.setTitle("JJBotv3 by InfamousTurtle - BETA Trial Expired");
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("BETA Trial Expired");
            errorAlert.setContentText("The BETA trial has expired, join the discord for more info: " +
                    "https://discord.gg/jKepBd4qxY");
            errorAlert.showAndWait();
        } else {
            stage.setTitle("JJBotv3 by InfamousTurtle");

            if (donatePrompt < 0.40) {
                donatePrompt(controller);
            }
            KeyListener listener = new KeyListener(controller);
            listener.registerKeyListener();
        }
        stage.setScene(new Scene(root, 600, 500));
        stage.show();


    }
}