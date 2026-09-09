import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

class Main {
        public static void main(String[] args) {
            ImageIcon logo = new ImageIcon("health_low_res.png");

            JFrame frame = new JFrame(); //creates a JFrame instance
            frame.setSize(1350, 750);
            frame.setIconImage(logo.getImage());
            frame.setResizable(false);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setTitle("Healthfirst Pharmarcies");
            frame.setLayout(new BorderLayout());

            Panel panel_1 = new Panel();
            Panel panel_2 = new Panel();
            Panel panel_3 = new Panel();
            Panel panel_4 = new Panel();
            Panel panel_5 = new Panel();

            panel_1.setBackground(Color.pink);
            panel_2.setBackground(Color.orange);
            panel_3.setBackground(Color.blue);
            panel_4.setBackground(Color.yellow);
            panel_5.setBackground(Color.white);


            panel_1.setPreferredSize(new Dimension(100, 50));
            panel_2.setPreferredSize(new Dimension(100, 50));
            panel_3.setPreferredSize(new Dimension(100, 50));
            panel_4.setPreferredSize(new Dimension(100, 50));
            panel_5.setPreferredSize(new Dimension(100, 50));

            //create a new instance of the login screen class
            login loginScreen = new login();

            //add login instance to center panel
            panel_5.add(loginScreen, Component.CENTER_ALIGNMENT);

            frame.add(panel_1, BorderLayout.NORTH);
            frame.add(panel_2, BorderLayout.SOUTH);
            frame.add(panel_3, BorderLayout.EAST);
            frame.add(panel_4, BorderLayout.WEST);
            frame.add(panel_5, BorderLayout.CENTER);


            frame.setVisible(true); // makes frame visible

    }
}