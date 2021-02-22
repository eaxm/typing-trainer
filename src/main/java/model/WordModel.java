package model;

import java.text.AttributedString;

/**
 * Represents a word in a game
 */
public class WordModel {

    private int x, y;
    private String word;
    private double speedFactor = 1;
    private boolean bonus = false; // TODO: future feature: bonus points
    private AttributedString asWord;

    /**
     * Creates a word with the default speed factor of 1
     *
     * @param x    starting x coordinate
     * @param y    starting y coordinate
     * @param word text
     */
    public WordModel(int x, int y, String word) {
        this.x = x;
        this.y = y;
        this.word = word;
        asWord = new AttributedString(word);
    }

    /**
     * Creates a word
     *
     * @param x           starting x coordinate
     * @param y           starting y coordinate
     * @param word        text
     * @param speedFactor speed factor for movement
     */
    public WordModel(int x, int y, String word, double speedFactor) {
        this.x = x;
        this.y = y;
        this.word = word;
        this.speedFactor = speedFactor;
        asWord = new AttributedString(word);
    }

    /**
     * Increases the y coordinate of the word by the argument multiplied by the speed factor
     *
     * @param value
     */
    public void down(int value) {
        y += value * speedFactor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getWord() {
        return word;
    }

    public AttributedString getAsWord() {
        return asWord;
    }

    public void setAsWord(AttributedString asWord) {
        this.asWord = asWord;
    }


}
