package model;

import view.MainFrame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.font.TextAttribute;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Represents a game
 */
public class GameModel {


    private final long GAME_TIME;

    private long startTime;
    private ArrayList<String> strWords = new ArrayList<>();
    private List<WordModel> words = null;
    private int framesPerSecond = 30;
    private int waitTime = 1000 / framesPerSecond;
    private int width;
    private int height;
    private Font font = new Font(Font.DIALOG, Font.PLAIN, 20);
    private int wpm;
    private int correctWords = 0;
    private String currentString = "";
    private boolean ongoing = true;
    private int maxWords = 10;
    private double minSpeedFactor = 1;
    private double maxSpeedFactor = 3;

    /**
     * Creates a game and initializes its values
     *
     * @param optionsModel Used to apply settings
     * @throws IOException If file can't be read
     */
    public GameModel(OptionsModel optionsModel) throws IOException {
        this.words = Collections.synchronizedList(new ArrayList<>());

        this.width = MainFrame.getInstance().getWidth();
        this.height = MainFrame.getInstance().getHeight();
        this.GAME_TIME = optionsModel.getGameTime();
        File wordlistDir = new File(optionsModel.getWordlistDirectory());

        if (!wordlistDir.exists())
            throw new IllegalArgumentException("wordlistdir doesn't exist");
        if (!wordlistDir.isDirectory())
            throw new IllegalArgumentException("worldlistdir is not a directory");

        // Get every file that ends with .txt
        File[] wordlists = wordlistDir.listFiles(f -> f.isFile() && f.getName().toLowerCase().endsWith(".txt"));


        for (File wordlist : wordlists) {
            BufferedReader br = new BufferedReader(new FileReader(wordlist));
            String line = br.readLine();
            while (line != null) {
                // don't add lines that start with a comment symbol
                if (!line.startsWith("#")) {
                    if (optionsModel.isLowercase())
                        line = line.toLowerCase();
                    strWords.add(line);
                }
                line = br.readLine();
            }
        }


    }

    /**
     * Starts the game and creates new thread for the game loop
     */
    public void startGame() {
        startTime = new Date().getTime();
        Runnable r = () -> {
            try {
                gameLoop();
                // Save data in db after the game has ended
                try {
                    DatabaseHandler dbHandler = new DatabaseHandler();
                    dbHandler.saveProgress(wpm, new Date().getTime());
                } catch (SQLException e) {
                    e.printStackTrace(); // TODO: Show alert
                }

                MainFrame.getInstance().setLastPanel();
            } catch (InterruptedException e) {
                e.printStackTrace(); // TODO: handle exception
            }
        };

        Thread t = new Thread(r);
        t.start();

    }


    private void gameLoop() throws InterruptedException {
        while (ongoing) {

            // update current time and check if game has ended
            long currentTime = new Date().getTime();
            long timeDiff = currentTime - startTime;
            long remainingTime = GAME_TIME * 1000 - timeDiff;
            if (remainingTime <= 0) {
                ongoing = false;
                continue;
            } else {
                setRemainingTime((int) (remainingTime / 1000));
            }


            synchronized (words) {

                // create a new word when conditions are met
                if (words.size() < maxWords && ThreadLocalRandom.current().nextInt(0, 10) < 1) {
                    int index = ThreadLocalRandom.current().nextInt(0, strWords.size());
                    String chosenWord = strWords.get(index);
                    boolean exists = false;
                    for (WordModel w : words) {
                        // break if the chosen word already exists on the screen
                        if (w.getWord().equals(chosenWord)) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        double speedFactor = ThreadLocalRandom.current().nextDouble(minSpeedFactor, maxSpeedFactor);
                        int wordX = ThreadLocalRandom.current().nextInt(0, width);

                        WordModel w = new WordModel(wordX, 0, chosenWord, speedFactor);
                        w.getAsWord().addAttribute(TextAttribute.FONT, font);
                        words.add(w);
                    }
                }

                // set all words one unit down
                words.forEach(w -> w.down(1));


                // check for words below pane size and remove them
                words.removeIf(w -> w.getY() >= height);
            }

            // calculate words per minute
            wpm = (int) Math.round((correctWords * 60) / ((currentTime - startTime) / 1000.0));
            setWPM(wpm);

            // wait till next frame
            Thread.sleep(waitTime);

        }
    }

    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }


    /**
     * Handles key input
     *
     * @param e
     */
    public void addKeyInput(KeyEvent e) {
        switch (e.getKeyChar()) {
            case KeyEvent.VK_BACK_SPACE:
                if (currentString.length() != 0) {
                    setCurrentString(currentString.substring(0, currentString.length() - 1)); // remove last character

                    // highlight text
                    if (currentString.length() != 0) {
                        for (WordModel w : words) {
                            if (w.getWord().startsWith(currentString)) {
                                w.getAsWord().addAttribute(TextAttribute.FOREGROUND, Color.LIGHT_GRAY, 0, w.getWord().length());
                                w.getAsWord().addAttribute(TextAttribute.FOREGROUND, Color.PINK, 0, currentString.length());
                            }
                        }
                    } else {
                        for (WordModel w : words) {
                            w.getAsWord().addAttribute(TextAttribute.FOREGROUND, Color.LIGHT_GRAY, 0, w.getWord().length());
                        }
                    }
                } else {
                    // TODO: might move to view
                    Toolkit.getDefaultToolkit().beep(); // make sound if current string is empty
                }
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_ENTER:
                synchronized (words) {

                    // remove word if its equal to the input and increase correctWords counter
                    if (words.removeIf(w -> w.getWord().equals(currentString))) {
                        correctWords++;
                    }
                    setCurrentString(""); // set input to an empty string

                    // reset highlighting
                    for (WordModel w : words) {
                        if (w.getWord().startsWith(currentString)) {
                            w.getAsWord().addAttribute(TextAttribute.FOREGROUND, Color.LIGHT_GRAY, 0, w.getWord().length());
                        }
                    }
                }
                // TODO
                break;
            default:
                currentString += e.getKeyChar(); // add char to input

                // check if input matches words and highlight the words accordingly
                for (WordModel w : words) {
                    if (w.getWord().startsWith(currentString)) {
                        w.getAsWord().addAttribute(TextAttribute.FOREGROUND, Color.PINK, 0, currentString.length());
                    } else {
                        w.getAsWord().addAttribute(TextAttribute.FOREGROUND, Color.LIGHT_GRAY, 0, w.getWord().length());
                    }
                }
                setCurrentString(currentString);
                break;
        }

    }

    // TODO: Might use an enum instead of plain Strings
    private void setCurrentString(String currentString) {
        this.currentString = currentString;
        propertyChangeSupport.firePropertyChange("STRING", null, currentString);
    }

    private void setRemainingTime(int time) {
        propertyChangeSupport.firePropertyChange("TIME", null, time);
    }

    private void setWPM(int wpm) {
        propertyChangeSupport.firePropertyChange("WPM", null, wpm);
    }


    public List<WordModel> getWords() {
        return words;
    }
}
