import com.formdev.flatlaf.FlatDarculaLaf;
import controller.MenuController;
import view.MainFrame;
import view.MenuView;


/**
 * Main class
 */
public class Main {

    public static void main(String[] args) {

        FlatDarculaLaf.install(); // Set the new theme

        MainFrame mainFrame = MainFrame.getInstance();
        MenuView menuView = new MenuView();
        MenuController menuController = new MenuController(menuView);
        mainFrame.setContentPane(menuView);
        mainFrame.setVisible(true);
    }
}
