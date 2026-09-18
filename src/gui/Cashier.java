package gui;

import gui.Main;

import javax.swing.*;
import java.awt.*;

public class Cashier extends JPanel {
    private Main mainFrame;
    public Cashier(Main mainFrame){
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        JLabel cashier = new JLabel("gui.Cashier");
        add(cashier);

    }
}
