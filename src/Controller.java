import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.awt.*;

public class Controller {

    public Button startButton;

    public Button stopButton;

    public Button cancelButton;

    public TextField startField;

    public TextField endField;
    Functionality typer;

    public int currentPosition;

    Thread th = new Thread(typer);

    public void startClicked() {

        // Not best practice to check for valid input, but I'm lazy.
        try {
            Integer.parseInt(startField.getText());
        } catch (NumberFormatException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Start Field not Valid!");
            errorAlert.setContentText("Enter only numbers greater than 0");
            errorAlert.showAndWait();
            return;
        }

        try {
            Integer.parseInt(endField.getText());
        } catch (NumberFormatException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("End Field not Valid!");
            errorAlert.setContentText("Enter only numbers greater than 0");
            errorAlert.showAndWait();
            return;
        }

        if (Integer.parseInt(startField.getText()) >= Integer.parseInt(endField.getText())) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("End Field not Valid!");
            errorAlert.setContentText("End Field must be grater than Start Field");
            errorAlert.showAndWait();
            return;
        }


        disableInput();
        startButton.setDisable(true);
        stopButton.setDisable(false);
        typer = new Functionality(Integer.parseInt(startField.getText()),10);
//        th.setName("runnerThread");
        th.setDaemon(true);
        th.start();


        // Once finished, re-enable button.
        typer.setOnSucceeded(test ->
        {startButton.setDisable(false);});
        System.out.println(th.isAlive());


        // TODO: I can suspend/unsuspend thread - maybe not the best implementation
        // or I can pause, kill thread, store the value it was up to, and start from there
    }

    public void stopClicked() {
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
}
