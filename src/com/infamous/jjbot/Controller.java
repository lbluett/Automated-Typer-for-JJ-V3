package com.infamous.jjbot;

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
    public CheckBox includePunct;
    public CheckBox robloxChat;
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
                handleInputError(startField);
                return;
            }

            try {
                Integer.parseInt(delayField.getText());
            } catch (NumberFormatException e) {
                handleInputError(delayField);
                return;
            }

            try {
                Integer.parseInt(endField.getText());
            } catch (NumberFormatException e) {
                handleInputError(endField);
                return;
            }

            if (Integer.parseInt(startField.getText()) < 0) {
                handleInputError(startField);
                return;
            }

            if (Integer.parseInt(endField.getText()) < 0) {
                handleInputError(endField);
                return;
            }

            if (Integer.parseInt(delayField.getText()) < 0) {
                handleInputError(delayField);
                return;
            }

            if (Integer.parseInt(startField.getText()) >= Integer.parseInt(endField.getText())) {
                handleInputError(endField);
                return;
            }
            end = Integer.parseInt(endField.getText());
            currentPosition = Integer.parseInt(startField.getText());

        }
        disableInput();
        cancelButton.setDisable(true);

        UserSettings settings = new UserSettings(camelToggl, upperToggl, normalCaseToggl, lowerToggl, nonePuncToggl,
                exclamationToggl, fullStopToggl, normalToggl, hellToggl, deathToggl, jumpToggl, cheerToggl, includePunct,
                robloxChat);

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
        cancelButton.setDisable(true);
        startStop.setSelected(false);
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
        delayField.setDisable(true);
        includePunct.setDisable(true);
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
        delayField.setDisable(false);
        includePunct.setDisable(false);
        updatePreview();
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
            // If start is pressed.
            startStop.setText("Stop (F8)");
            startClicked();
        } else {
            typer.cancel(); // Kill the typer thread - we will collect its data and start from where it stopped.
            if (firstRun) {
                startStop.setText("Start (F8)"); // If the user hasn't paused before.
            } else {
                startStop.setText("Resume (F8)"); // If the user has paused.
            }
            cancelButton.setDisable(false);
        }
    }

    public void setCurrentPosition(int newCurrent) {
        currentPosition = newCurrent;
    }

    /**
     * Updates the preview.
     */
    public void updatePreview() {
        UserSettings settings = new UserSettings(camelToggl, upperToggl, normalCaseToggl, lowerToggl, nonePuncToggl,
                exclamationToggl, fullStopToggl, normalToggl, hellToggl, deathToggl, jumpToggl, cheerToggl, includePunct,
                robloxChat);

        preview.setText(settings.applyPunctuation(PREVIEW_STRING));
        preview.setText(settings.applyCase(preview.getText()));
        // Only enable include punctuation toggl when conditions met.
        includePunct.setDisable((!deathToggl.isSelected() && !hellToggl.isSelected()) ||
                (!exclamationToggl.isSelected() && !fullStopToggl.isSelected()));
    }

    public void handleInputError(TextField input) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("\"" + input.getText() +"\" is not a valid input!");
        errorAlert.setContentText("Enter only numbers greater than or equal to 0. Start must be less than end!");
        errorAlert.showAndWait();
        cancelClicked();
        startStop.setSelected(false);
    }
}
