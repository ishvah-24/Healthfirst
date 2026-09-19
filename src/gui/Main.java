package gui;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public Main() {

        cardLayout = new CardLayout();

        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(Color.WHITE);

        // Add all main panels
        mainPanel.add(new login(this), "LOGIN");
        mainPanel.add(new Admin(this), "ADMIN");
        mainPanel.add(new Cashier(this), "CASHIER");

        // Main panel fills the entire JFrame
        add(mainPanel, BorderLayout.CENTER);

        setTitle("HealthFirst Pharmacy");
        setSize(1366, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setVisible(true);
    }

    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new Main();
        });
    }
}