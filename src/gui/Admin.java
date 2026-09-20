package gui;

import gui.ManageMedicine.ManageMedicine;
import gui.ManageSuppliers;
import gui.Manageusers;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class Admin extends JPanel {

    private Main mainFrame;

    public Admin(Main mainFrame) {

        this.mainFrame = mainFrame;

        // Admin fills the entire available space
        setLayout(new BorderLayout());
        setLayout(new BorderLayout());

        setBackground(Color.BLACK);

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

        // Customize tab sizes
        tabbedPane.setUI(new BasicTabbedPaneUI() {

            @Override
            protected int calculateTabWidth(
                    int tabPlacement,
                    int tabIndex,
                    FontMetrics metrics) {

                return 120;
            }

            @Override
            protected int calculateTabHeight(
                    int tabPlacement,
                    int tabIndex,
                    int fontHeight) {

                return 113;
            }
        });

        // Create management panels
        JPanel manageMedicines = new ManageMedicine();
        JPanel manageSuppliers = new ManageSuppliers();
        JPanel manageUsers = new Manageusers();
        JPanel reports = new Reports();

        // Add tabs
        tabbedPane.addTab("Medicines", manageMedicines);
        tabbedPane.addTab("Suppliers", manageSuppliers);
        tabbedPane.addTab("Users", manageUsers);
        tabbedPane.addTab("Reports", reports);

        // Tabbed pane fills the entire Admin panel
        add(tabbedPane, BorderLayout.CENTER);
    }
}