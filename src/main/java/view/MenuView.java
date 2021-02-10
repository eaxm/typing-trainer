package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * User interface of the menu
 */
public class MenuView extends JPanel {

    private JLabel lblTitle;
    private JButton btnPlay;
    private JButton btnProgress;
    private JButton btnOptions;
    private JButton btnExit;

    private static final Font TITLE_FONT = new Font(Font.DIALOG, Font.BOLD, 40);
    private static final Font BUTTON_FONT = new Font(Font.DIALOG, Font.PLAIN, 25);
    private static final int SPACING = 10;

    /**
     * Constructor which initializes the Menu UI
     */
    public MenuView() {
        super();

        setLayout(new BorderLayout());

        lblTitle = new JLabel("Typing Trainer", JLabel.CENTER);
        lblTitle.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        lblTitle.setFont(TITLE_FONT);


        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));


        btnPlay = new JButton("Play");
        btnPlay.setFont(BUTTON_FONT);
        btnProgress = new JButton("Progress");
        btnProgress.setFont(BUTTON_FONT);
        btnOptions = new JButton("Options");
        btnOptions.setFont(BUTTON_FONT);
        btnExit = new JButton("Exit");
        btnExit.setFont(BUTTON_FONT);


        // Center menu buttons with space in between
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(btnPlay);
        buttonPanel.add(Box.createHorizontalStrut(SPACING));
        buttonPanel.add(btnProgress);
        buttonPanel.add(Box.createHorizontalStrut(SPACING));
        buttonPanel.add(btnOptions);
        buttonPanel.add(Box.createHorizontalStrut(SPACING));
        buttonPanel.add(btnExit);
        buttonPanel.add(Box.createHorizontalGlue());

        // Center title
        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(lblTitle);
        menuPanel.add(buttonPanel);
        menuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(Box.createVerticalGlue());


        add(menuPanel, BorderLayout.CENTER);


    }

    /**
     * Adds an ActionListener to the play button
     *
     * @param l
     */
    public void addPlayActionListener(ActionListener l) {
        btnPlay.addActionListener(l);
    }

    /**
     * Adds an ActionListener to the options button
     *
     * @param l
     */
    public void addOptionsActionListener(ActionListener l) {
        btnOptions.addActionListener(l);
    }

    /**
     * Adds an ActionListener to the exit button
     *
     * @param l
     */
    public void addExitActionListener(ActionListener l) {
        btnExit.addActionListener(l);
    }
}
