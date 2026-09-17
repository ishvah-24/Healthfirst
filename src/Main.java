import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public Main() {

        cardLayout = new CardLayout(20, 0);

        mainPanel = new JPanel(cardLayout);
        mainPanel.setPreferredSize(new Dimension(1000, 700));
        mainPanel.setBackground(Color.WHITE);

        //JPanel wrapper = new JPanel(new GridBagLayout());
        //wrapper.add(mainPanel);

        mainPanel.add(new login(this), "LOGIN");
        mainPanel.add(new Admin(this), "ADMIN");
        mainPanel.add(new Cashier(this), "CASHIER");

        add(mainPanel);

        setTitle("HealthFirst Pharmacy");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    public static void main(String[] args) {
        new Main();
    }
}