import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Controller {
    static final String PREVIEW_STRING = "one hundred twenty five";

    public Text preview;
    public ToggleButton startStop;
    public Button cancelButton;
    public RadioButton camelToggl;
    public RadioButton upperToggl;
    public RadioButton normalCaseToggl;
    public RadioButton lowerToggl;
    public RadioButton nonePuncToggl;
    public RadioButton exclamationToggl;
    public RadioButton fullStopToggl;
    public RadioButton normalToggl;
    public RadioButton hellToggl;
    public RadioButton deathToggl;
    public RadioButton jumpToggl;
    public RadioButton cheerToggl;
    public TextField startField;
    public TextField endField;
    public TextField delayField;
    Functionality typer;

    private int currentPosition;

    private int end;

    Thread th;

    private boolean firstRun = true;

    public void startClicked() {
        if (firstRun) {
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
            end = Integer.parseInt(endField.getText());
            currentPosition = Integer.parseInt(startField.getText());

        }
        disableInput();
        cancelButton.setDisable(true);

        UserSettings settings = new UserSettings(camelToggl, upperToggl, normalCaseToggl, lowerToggl, nonePuncToggl,
                exclamationToggl, fullStopToggl, normalToggl, hellToggl, deathToggl, jumpToggl, cheerToggl);

        firstRun = false;
        System.out.println(currentPosition);
        typer = new Functionality(currentPosition, end, Integer.parseInt(delayField.getText()), this, settings);
        th = new Thread(typer);
        th.setDaemon(true);
        th.start();

        // Once finished, re-enable button.
        typer.setOnSucceeded(test ->
        {
            System.out.println("alive? " + th.isAlive());
            System.out.println(currentPosition); // struggling to change variable, pass by reference?
            cancelClicked();
        });
    }

    public void cancelClicked() {
        firstRun = true;
        startStop.setText("Start (F8)");
        enableInput();
    }

    public void disableInput() {
        startField.setDisable(true);
        endField.setDisable(true);

        // Disable all toggles
        camelToggl.setDisable(true);
        upperToggl.setDisable(true);
        normalCaseToggl.setDisable(true);
        lowerToggl.setDisable(true);
        nonePuncToggl.setDisable(true);
        exclamationToggl.setDisable(true);
        fullStopToggl.setDisable(true);
        normalToggl.setDisable(true);
        hellToggl.setDisable(true);
        deathToggl.setDisable(true);
        jumpToggl.setDisable(true);
        cheerToggl.setDisable(true);
    }

    public void enableInput() {
        startField.setDisable(false);
        endField.setDisable(false);

        // Enable all toggles
        camelToggl.setDisable(false);
        upperToggl.setDisable(false);
        normalCaseToggl.setDisable(false);
        lowerToggl.setDisable(false);
        nonePuncToggl.setDisable(false);
        exclamationToggl.setDisable(false);
        fullStopToggl.setDisable(false);
        normalToggl.setDisable(false);
        hellToggl.setDisable(false);
        deathToggl.setDisable(false);
        jumpToggl.setDisable(false);
        cheerToggl.setDisable(false);
    }

    public void groupLink() throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.roblox.com/groups/4088269/IT-SB-Apparel"));
    }

    public void helpLink() throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://discord.gg/jKepBd4qxY"));
    }


    /**
     * Register hotkey press as a click, run startStopClicked() function.
     */
    public void hotkeyPressed() {
        if (startStop.isSelected()) {
            startStop.setSelected(false);
            startStopClicked();
        } else {
            startStop.setSelected(true);
            startStopClicked();
        }
    }

    public void startStopClicked() {
        if (startStop.isSelected()) {
            startStop.setText("Stop (F8)");
            startClicked();
        } else {
            typer.cancel();
            if (firstRun) {
                startStop.setText("Start (F8)");
            } else {
                startStop.setText("Resume (F8)");
            }
            cancelButton.setDisable(false);
        }
    }

    public void setCurrentPosition(int newCurrent) {
        currentPosition = newCurrent;
    }

    public void updatePreview() {
        UserSettings settings = new UserSettings(camelToggl, upperToggl, normalCaseToggl, lowerToggl, nonePuncToggl,
                exclamationToggl, fullStopToggl, normalToggl, hellToggl, deathToggl, jumpToggl, cheerToggl);

        preview.setText(settings.applyPunctuation(PREVIEW_STRING));
        preview.setText(settings.applyCase(PREVIEW_STRING));
    }

    public void handleInputError(TextField input) {
        try {
            Integer.parseInt(input.getText());
        } catch (NumberFormatException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText(input.getText() + " field not valid!");
            errorAlert.setContentText("Enter only numbers greater than or equal to 0");
            errorAlert.showAndWait();
            cancelClicked();
        }
    }
}
