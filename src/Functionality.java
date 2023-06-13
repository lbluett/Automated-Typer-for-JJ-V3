import javafx.application.Platform;
import javafx.concurrent.Task;

import javax.naming.ldap.Control;
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

    Controller controller;
    public Functionality(int start, int end, int delay, Controller controller, UserSettings settings) {
        this.start = start;
        this.end = end;
        this.controller = controller;
        this.delay = delay;
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
            System.out.println("here!!");
            typeKeys(robot, EnglishNumberToWords.convert(pos)); // Type keys
            System.out.println(pos);
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
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);

        // Paste* the clipboard.
        robot.keyPress(KeyEvent.VK_SLASH);
        robot.keyRelease(KeyEvent.VK_SLASH);

        wait(1000 + text.length() * delay);

        robot.keyPress(KeyEvent.VK_CONTROL); // TODO: Mac Friendly VK_MISC
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        wait(50);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        wait(50);

        robot.keyPress(KeyEvent.VK_SPACE);
        wait(50);
        robot.keyRelease(KeyEvent.VK_SPACE);
    }

    @Override
    protected Long call() throws Exception {
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
