import javafx.concurrent.Task;

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
    public Functionality(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void runner(int start, int end) {
        System.out.println(start + " " + end);
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e); // TODO: tell user their device isn't compatible
        }

        for (int i = start; i <= end; i++) {

            typeKeys(robot, EnglishNumberToWords.convert(i));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);

        // Paste* the clipboard.
        robot.keyPress(KeyEvent.VK_SLASH);
        robot.keyRelease(KeyEvent.VK_SLASH);

        wait(1000);

        robot.keyPress(KeyEvent.VK_CONTROL); // TODO: Mac Friendly VK_MISC
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        wait(1000);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        wait(1000);

        robot.keyPress(KeyEvent.VK_SPACE);
        wait(100);
        robot.keyRelease(KeyEvent.VK_SPACE);

    }

    @Override
    protected Long call() throws Exception {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("hello");
        runner(this.start, this.end);
        return null;
    }
}
