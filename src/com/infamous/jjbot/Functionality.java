package com.infamous.jjbot;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;


public class Functionality extends Task<Long> {
    // Here we will add the variables that the user has set
    // Write a method that updates the variable when element is changed on gui? I suppose
    // this will be called on the gui side when var is changed if there is not a simpler way.

    private int start;
    private int end;
    private int delay;
    private int pos;
    private UserSettings settings;

    Controller controller;
    public Functionality(int start, int end, int delay, Controller controller, UserSettings settings) {
        this.start = start;
        this.end = end;
        this.controller = controller;
        this.delay = delay;
        this.settings = settings;
    }

    public void runner() {
        System.out.println(start + " " + end);
        Robot robot;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e); // TODO: tell user their device isn't compatible
        }

        pos = start; // Position
        for (; (pos <= end && !isCancelled()); pos++) {
            // Get the number we're currently at, and apply punctuation and case settings.
            String input = settings.applyCase(settings.applyPunctuation(EnglishNumberToWords.convert(pos)));

            // If settings.getMode() returns "hell", then iterate through each character and put it through typeKeys
            if (settings.getMode().equals("hell")) {
                for (int i = 0; i < input.length(); i++) {
                    // Skip punctuation
                    if (input.charAt(i) == '.' || input.charAt(i) == '!') {
                        continue;
                    }
                    typeKeys(robot, input.substring(i, i + 1));
                }
            }

            // If settings.getMode() returns "death", then iterate through each character backwards and put it through typeKeys
            if (settings.getMode().equals("death")) {
                for (int i = input.length() - 1; i >= 0; i--) {
                    if (input.charAt(i) == '.' || input.charAt(i) == '!') {
                        continue;
                    }
                    typeKeys(robot, input.substring(i, i + 1));
                }
            }

            if (settings.getMode().equals("death")) {
                // reverse input string
                StringBuilder inputBuilder = new StringBuilder(input);
                inputBuilder.reverse();
                input = inputBuilder.toString();
                typeKeys(robot, input);
            } else {
                typeKeys(robot, input);
            }
        }
    }

    public void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void typeKeys(Robot robot, String text) {
        StringSelection textOutput = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(textOutput, textOutput);

        try {
            // Open chat
            robot.keyPress(KeyEvent.VK_SLASH);
            robot.keyRelease(KeyEvent.VK_SLASH);

            // Simulate typing time
            wait(1000 + text.length() * delay);

            // Paste text
            robot.keyPress(KeyEvent.VK_CONTROL); // TODO: Mac Friendly VK_MISC
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);

            wait(100);

            // Send message
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            System.out.println("Numbered");

            wait(100);

            // Jump or cheer
            if (settings.getAction().equals("cheer")) {
                StringSelection stringSelection = new StringSelection("/e cheer");
                Clipboard clipboard2 = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard2.setContents(stringSelection, stringSelection);

                robot.keyPress(KeyEvent.VK_SLASH);
                robot.keyRelease(KeyEvent.VK_SLASH);
                wait(400);
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_V);
                wait(100);
                robot.keyRelease(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                wait(100);
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                System.out.println("Cheered!");
                wait(500);
            } else {
                robot.keyPress(KeyEvent.VK_SPACE);
                wait(50);
                robot.keyRelease(KeyEvent.VK_SPACE);
            }
        } catch (IllegalArgumentException e) {
            // Keyboard probably not compatible
            Platform.runLater(() -> {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Oops! Use a English (US) Keyboard!");
                errorAlert.setContentText("This program only works with an English keyboard! Join the discord for help: " +
                        "https://discord.gg/jKepBd4qxY");
                errorAlert.showAndWait();
                System.exit(42);
            });
        }
    }
    @Override
    protected Long call() {
        try {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            runner();
        } finally {
            System.out.println("I'm ending!");
            if (pos < end) {
                System.out.println(" PREMATURELY!");
                Platform.runLater(() -> {
                    controller.setCurrentPosition(pos);
                });
            }
        }
        return null;
    }
}
