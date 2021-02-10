package view;

import model.OptionsModel;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * User interface of the options menu
 */
public class OptionsView extends JPanel {

    private JCheckBox cbLowercase;
    private JLabel lblTime;
    private JSpinner sTime;
    private JButton btnBack;

    private static final Font OPTIONS_FONT = new Font(Font.DIALOG, Font.PLAIN, 20);

    /**
     * Constructor which initializes the Options UI
     */
    public OptionsView() {
        super();
        // TODO: Use another layout
        cbLowercase = new JCheckBox("Lowercase");
        cbLowercase.setFont(OPTIONS_FONT);
        lblTime = new JLabel("Game time:");
        lblTime.setFont(OPTIONS_FONT);
        SpinnerModel spinnerModel = new SpinnerNumberModel(60, OptionsModel.MIN_GAME_TIME, OptionsModel.MAX_GAME_TIME, OptionsModel.TIME_STEP);
        sTime = new JSpinner(spinnerModel);
        sTime.setFont(OPTIONS_FONT);


        btnBack = new JButton("Back");
        btnBack.setFont(OPTIONS_FONT);

        add(cbLowercase);
        add(lblTime);
        add(sTime);
        add(btnBack);

    }

    /**
     * Adds an ActionListener to the lowercase checkbox
     *
     * @param l
     */
    public void addLowercaseActionListener(ActionListener l) {
        cbLowercase.addActionListener(l);
    }

    /**
     * Adds a ChangeListener to the time spinner
     *
     * @param l
     */
    public void addTimeChangeListener(ChangeListener l) {
        sTime.addChangeListener(l);
    }

    /**
     * Adds an ActionListener to the back button
     *
     * @param l
     */
    public void addBackActionListener(ActionListener l) {
        btnBack.addActionListener(l);
    }


}
