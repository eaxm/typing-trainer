package view;

import org.knowm.xchart.XChartPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * User interface of the progress menu
 */
public class ProgressView extends JPanel implements PropertyChangeListener {

    private XChartPanel chartPanel;
    private JButton btnBack;
    private static final Font BUTTON_FONT = new Font(Font.DIALOG, Font.PLAIN, 25);

    /**
     * Constructor that initializes the Progress UI
     */
    public ProgressView() {
        super();
        btnBack = new JButton("Back");
        btnBack.setFont(BUTTON_FONT);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(btnBack);
    }


    public void addBackActionListener(ActionListener l) {
        btnBack.addActionListener(l);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("chartPanel")){
            if (chartPanel != null)
                this.remove(chartPanel);
            chartPanel = (XChartPanel) evt.getNewValue();
            this.add(chartPanel);
        }

    }
}
