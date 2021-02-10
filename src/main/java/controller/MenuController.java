package controller;

import model.GameModel;
import model.OptionsModel;
import view.GameView;
import view.MainFrame;
import view.MenuView;
import view.OptionsView;

import java.io.IOException;

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
        menuView.addOptionsActionListener(e -> handleOptionsClick());
        menuView.addExitActionListener(e -> handleExitClick());
    }

    private void handlePlayClick() {
        GameView gameView = new GameView();
        try {
            GameModel gameModel = new GameModel(optionsModel);
            GameController gameController = new GameController(gameModel, gameView);
            mainFrame.setContentPane(gameView);
            // repaint and revalidate main frame to make the game pane visible to the user
            mainFrame.repaint();
            mainFrame.revalidate();
            // request focus to the game pane to get the key listener working
            gameView.requestFocus();
        } catch (IOException e) {
            // TODO: show error dialog that the game could not be initialized
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
