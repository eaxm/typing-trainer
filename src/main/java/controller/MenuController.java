package controller;

import model.GameModel;
import model.OptionsModel;
import model.ProgressModel;
import view.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Handles menu interactions
 */
public class MenuController {

    private MenuView menuView;

    private OptionsView optionsView;
    private OptionsModel optionsModel;
    private OptionsController optionsController;

    private MainFrame mainFrame = MainFrame.getInstance();

    /**
     * @param menuView
     */
    public MenuController(MenuView menuView) {
        this.menuView = menuView;

        optionsModel = new OptionsModel();
        optionsView = new OptionsView();
        optionsController = new OptionsController(optionsView, optionsModel);

        menuView.addPlayActionListener(e -> handlePlayClick());
        menuView.addProgressActionListener(e -> handleProgressClick());
        menuView.addOptionsActionListener(e -> handleOptionsClick());
        menuView.addExitActionListener(e -> handleExitClick());
    }

    private void handlePlayClick() {
        GameView gameView = new GameView();
        try {
            GameModel gameModel = new GameModel(optionsModel);
            GameController gameController = new GameController(gameModel, gameView);
            mainFrame.setContentPane(gameView);
            // request focus to the game pane to get the key listener working
            gameView.requestFocus();
        } catch (IOException e) {
            // TODO: show error dialog that the game could not be initialized
        }
    }

    private void handleProgressClick() {
        ProgressView progressView = new ProgressView();
        ProgressModel progressModel = new ProgressModel();
        try{
            ProgressController progressController = new ProgressController(progressModel, progressView);
            mainFrame.setContentPane(progressView);
        }catch (SQLException e){
            e.printStackTrace();
            // TODO: show alert
        }

    }

    private void handleOptionsClick() {
        mainFrame.setContentPane(optionsView);
        mainFrame.repaint();
        mainFrame.revalidate();
    }

    private void handleExitClick() {
        mainFrame.dispose();
    }
}
