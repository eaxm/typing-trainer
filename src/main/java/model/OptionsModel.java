package model;

/**
 * Represents options
 */
public class OptionsModel {

    public static final int MIN_GAME_TIME = 30;
    public static final int MAX_GAME_TIME = 600;
    public static final int TIME_STEP = 10;

    private int gameTime = 60;
    private boolean lowercase = false;
    private String wordlistDirectory = "wordlists/";

    public int getGameTime() {
        return gameTime;
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    public String getWordlistDirectory() {
        return wordlistDirectory;
    }

    public boolean isLowercase() {
        return lowercase;
    }

    public void setLowercase(boolean lowercase) {
        this.lowercase = lowercase;
    }
}
