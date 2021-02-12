package view;

import model.WordModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * User interface of the game
 */
public class GameView extends JPanel implements PropertyChangeListener {

    private String currentString = "";
    private int wpm;
    private int remainingTime;
    private List<WordModel> words = new ArrayList<>();

    private Font uiFont = new Font(Font.DIALOG, Font.PLAIN, 25);
    private Dimension panelSize;


    /**
     * Sets the word list that is going to be used by the game
     *
     * @param words
     */
    public void setWords(List<WordModel> words) {
        this.words = words;
    }

    /**
     * Paints the game to the panel
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        panelSize = getSize();

        // draws all words
        synchronized (words) {
            for (WordModel w : words) {
                g.drawString(w.getAsWord().getIterator(), w.getX(), w.getY());
            }
        }

        // UI stuff which gets drawn above all other elements
        g.setFont(uiFont);
        FontMetrics fontMetrics = g.getFontMetrics(uiFont);

        // draws UI at the top
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Time: " + remainingTime, 10, 25);
        g.drawString("WPM: " + wpm, 10, 25 + fontMetrics.getHeight());

        // draws UI at the bottom
        g.setColor(new Color(0x2F2F2F));
        g.fillRect(0, panelSize.height - fontMetrics.getHeight(), panelSize.width, fontMetrics.getHeight());
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Input: " + currentString, 5, panelSize.height - 7);

    }

    /**
     * Manipulates UI elements as soon as they are updated in the model and repaints the game
     *
     * @param evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        switch (propertyName) {
            case "STRING":
                currentString = evt.getNewValue().toString();
                break;
            case "TIME":
                remainingTime = (int) evt.getNewValue();
                break;
            case "WPM":
                wpm = (int) evt.getNewValue();
                break;
            default:
                System.out.println("something went wrong"); // TODO: Log message instead of printing it
                break;
        }

        repaint(); // TODO: Only repaint once a frame and not multiple times
    }
}
