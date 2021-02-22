package model;


import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.style.XYStyler;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents the progress
 */
public class ProgressModel {

    private PropertyChangeSupport propertyChangeSupport;

    public ProgressModel() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    /**
     * Loads progress from a db, creates a chart panel and notifies its listeners
     *
     * @throws SQLException
     */
    public void loadProgress() throws SQLException {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        List<GameEntry> gameEntries = databaseHandler.getProgress();

        List<Integer> x = new LinkedList<>();
        List<Integer> y = new LinkedList<>();
        int counter = 0;
        for (GameEntry gameEntry : gameEntries) {
            x.add(++counter); // TODO: use id of the relation instead of a counter
            y.add(gameEntry.getWpm());
        }

        XYChart chart = QuickChart.getChart("Progress", "Time", "WPM", "Words Per Minute", x, y);
        XChartPanel chartPanel = new XChartPanel(chart);

        XYStyler styler = chart.getStyler();
        styler.setChartBackgroundColor(new Color(0x3c3f41));
        styler.setChartFontColor(Color.LIGHT_GRAY);
        styler.setLegendBackgroundColor(new Color(0x323436));
        styler.setPlotBackgroundColor(new Color(0xAAAAAA));
        styler.setAxisTickLabelsColor(Color.LIGHT_GRAY);
        styler.setPlotGridLinesVisible(false);

        propertyChangeSupport.firePropertyChange("chartPanel", null, chartPanel);
    }
}
