package model;

import java.util.Date;

/**
 * Represents the data of a game
 */
public class GameEntry {
    private Date time; // Currently not used
    private int wpm;

    public GameEntry(Date time, int wpm) {
        this.time = time;
        this.wpm = wpm;
    }


    public Date getTime() {
        return time;
    }

    public int getWpm() {
        return wpm;
    }
}
