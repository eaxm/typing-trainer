package controller;

import model.ProgressModel;
import view.ProgressView;

public class ProgressController {

    private ProgressModel progressModel;
    private ProgressView progressView;

    public ProgressController(ProgressModel progressModel, ProgressView progressView) {
        this.progressModel = progressModel;
        this.progressView = progressView;
    }
}
