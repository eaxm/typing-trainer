package controller;

import model.ProgressModel;
import view.MainFrame;
import view.ProgressView;

import java.sql.SQLException;

/**
 * Handles progress interactions
 */
public class ProgressController {

    private ProgressModel progressModel;
    private ProgressView progressView;

    public ProgressController(ProgressModel progressModel, ProgressView progressView) throws SQLException {
        this.progressModel = progressModel;
        this.progressView = progressView;

        progressView.addBackActionListener(e -> handleBackAction());
        progressModel.addPropertyChangeListener(progressView);
        progressModel.loadProgress();
    }

    private void handleBackAction() {
        MainFrame.getInstance().setLastPanel();
    }
}
