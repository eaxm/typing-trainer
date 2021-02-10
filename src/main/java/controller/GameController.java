package controller;

import model.GameModel;
import view.GameView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles game interactions
 */
public class GameController {

    private GameModel gameModel;
    private GameView gameView;

    /**
     * Creates a GameController and initializes the game controls
     *
     * @param gameModel
     * @param gameView
     */
    public GameController(GameModel gameModel, GameView gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;

        gameModel.addPropertyChangeListener(gameView);
        gameView.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                handleKeyInput(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        gameView.setWords(gameModel.getWords());
        gameModel.startGame();
    }

    private void handleKeyInput(KeyEvent e) {
        gameModel.addKeyInput(e);
    }
}
