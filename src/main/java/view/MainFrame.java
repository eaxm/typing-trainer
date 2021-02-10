package view;

import javax.swing.*;
import java.awt.*;

// TODO: Not really a view, might move somewhere else

/**
 * Main Frame implemented as singleton class
 */
public class MainFrame extends JFrame {

    private static MainFrame mainFrame;
    private Container lastPanel = null;

    private MainFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 750);
        setResizable(false);
        setTitle("Typing Trainer");
    }

    /**
     * Gets instance of the frame
     *
     * @return main frame
     */
    public static MainFrame getInstance() {
        if (mainFrame == null)
            mainFrame = new MainFrame();
        return mainFrame;
    }


    /**
     * Sets pane of the main frame and saves last pane, which can be recovered with {@link #setLastPanel()}
     *
     * @param contentPane
     */
    @Override
    public void setContentPane(Container contentPane) {
        lastPanel = getContentPane();
        super.setContentPane(contentPane);
    }

    /**
     * Sets pane of the main frame to the last pane
     */
    public void setLastPanel() {
        if (lastPanel == null)
            throw new IllegalStateException("last panel is null");

        super.setContentPane(lastPanel);
    }
}
