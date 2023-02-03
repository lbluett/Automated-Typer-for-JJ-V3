import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class Functionality {
    // Here we will add the variables that the user has set
    // Write a method that updates the variable when element is changed on gui? I suppose
    // this will be called on the gui side when var is changed if there is not a simpler way.

    public void runner(int start, int end) {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e); // TODO: tell user their device isn't compatible
        }

        for (int i = start; i <= end; i++) {
            typeKeys(robot, "Hello A");
        }
    }

    public void typeKeys(Robot robot, String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);

        // Paste* the clipboard.
        robot.keyPress(KeyEvent.VK_CONTROL); // TODO: Mac Friendly VK_MISC
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

    }
}
