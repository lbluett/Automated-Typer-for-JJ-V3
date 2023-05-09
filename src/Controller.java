import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Controller {

    public ToggleButton startStop;

    public Button startButton;

    public Button stopButton;

    public Button cancelButton;

    public TextField startField;

    public TextField endField;
    Functionality typer;

    public int currentPosition;

    Thread th;

    public void startClicked() {

        // Not best practice to check for valid input, but I'm lazy.
        try {
            Integer.parseInt(startField.getText());
        } catch (NumberFormatException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Start field not valid!");
            errorAlert.setContentText("Enter only numbers greater than 0");
            errorAlert.showAndWait();
            return;
        }

        try {
            Integer.parseInt(endField.getText());
        } catch (NumberFormatException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("End field not valid!");
            errorAlert.setContentText("Enter only numbers greater than 0");
            errorAlert.showAndWait();
            return;
        }

        if (Integer.parseInt(startField.getText()) >= Integer.parseInt(endField.getText())) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("End field not valid!");
            errorAlert.setContentText("End Field must be grater than Start Field");
            errorAlert.showAndWait();
            return;
        }

        int start = Integer.parseInt(startField.getText());
        int end = Integer.parseInt(endField.getText());


        disableInput();
        stopButton.setDisable(false);
        currentPosition = Integer.parseInt(startField.getText());
        typer = new Functionality(start, end);
//        th.setName("runnerThread");
        th = new Thread(typer);
        th.setDaemon(true);
        th.start();

        // Once finished, re-enable button.
        typer.setOnSucceeded(test ->
        {
            System.out.println("alive? " + th.isAlive());
            System.out.println(start); // struggling to change variable, pass by reference?
        });
        System.out.println("alive? " +
                th.isAlive());


        // TODO: I can suspend/unsuspend thread - maybe not the best implementation
        // or I can pause, kill thread, store the value it was up to, and start from there
    }

    public void stopClicked() {
        System.out.println("Stopped");
        th.interrupt();
        stopButton.setDisable(true);
        startButton.setDisable(false);
        cancelButton.setDisable(false);
    }

    public void cancelClicked() {
        stopButton.setDisable(true);
        startButton.setDisable(false);
        cancelButton.setDisable(true);
        enableInput();
    }

    public void disableInput() {
        startField.setDisable(true);
        endField.setDisable(true);
    }

    public void enableInput() {
        startField.setDisable(false);
        endField.setDisable(false);
    }

    public void groupLink() throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.roblox.com/groups/4088269/IT-SB-Apparel"));
    }

    public void helpLink() throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://discord.gg/jKepBd4qxY"));
    }

    public void clickStartStop() {
        startStop.fire();
    }

    public void startStopClicked() {
        if (startStop.isSelected()) {/
            System.out.println("selected");
            startStop.setSelected(false);
            startStop.setText("Start");
        } else {
            System.out.println("not selected");
            startStop.setText("Stop");
            startStop.setSelected(true);
            startClicked();

        }
    }
}
