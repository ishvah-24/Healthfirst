import javax.swing.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class login extends JPanel {
    public login(){

        //this layout manager allows for components to be stacked one on top of each other
        //instead of being in line on the x-axis
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        //image icon on login screen
        ImageIcon logo = new ImageIcon("health_low_res.png");
        JLabel logoImage = new JLabel(logo);

        //labels for textfiel indication
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");

        JTextField username = new JTextField();
        JTextField password = new JTextField();

        //configuring size of textfields
        username.setPreferredSize(new Dimension(50, 40));
        password.setPreferredSize(new Dimension(50, 40));

        JButton submitBtn = new JButton("Submit");

        add(logoImage, CENTER_ALIGNMENT);
        add(usernameLabel);
        add(username, CENTER_ALIGNMENT);
        add(passwordLabel);
        add(password, CENTER_ALIGNMENT);
        add(submitBtn, CENTER_ALIGNMENT);
    }

}
