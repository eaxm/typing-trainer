package controller;

import model.OptionsModel;
import view.MainFrame;
import view.OptionsView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;

/**
 * Handles options interactions
 */
public class OptionsController {

    private OptionsView optionsView;
    private OptionsModel optionsModel;

    /**
     * Creates an OptionsController
     *
     * @param optionsView
     * @param optionsModel
     */
    public OptionsController(OptionsView optionsView, OptionsModel optionsModel) {
        this.optionsView = optionsView;
        this.optionsModel = optionsModel;

        optionsView.addLowercaseActionListener(e -> handleLowercaseAction(e));
        optionsView.addTimeChangeListener(e -> handleTimeChange(e));
        optionsView.addBackActionListener(e -> MainFrame.getInstance().setLastPanel());
    }

    private void handleLowercaseAction(ActionEvent e) {
        JCheckBox checkBox = (JCheckBox) e.getSource();
        optionsModel.setLowercase(checkBox.isSelected());
    }

    private void handleTimeChange(ChangeEvent e) {
        JSpinner spinner = (JSpinner) e.getSource();
        optionsModel.setGameTime((Integer) spinner.getValue());
    }
}
