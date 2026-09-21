package gui;

import model.User;
import service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class login extends JPanel implements ActionListener {

    private Main mainFrame;

    public login(Main mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);

        //logo
        ImageIcon logo = new ImageIcon(getClass().getResource("/health_low_res.png"));
        JLabel logoImage = new JLabel(logo);
        logoImage.setAlignmentX(Component.CENTER_ALIGNMENT);

        // username
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField username = new JTextField();
        username.setPreferredSize(new Dimension(300, 40));
        username.setMaximumSize(new Dimension(300, 40));

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField password = new JPasswordField();
        password.setPreferredSize(new Dimension(300, 40));
        password.setMaximumSize(new Dimension(300, 40));

        // Submit button
        JButton submitBtn = new JButton("Submit");
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        //when the submit button is press, it performs the following tasks:
        submitBtn.addActionListener(e -> {

            //extracts the text the user has entered
            String username_ = username.getText();
            String password_ = new String(password.getPassword());
            User user = UserService.login(username_, password_);

            //retrieves the role of the user
            if (user != null) {
                if (user.getRole().equals("ADMIN")) {
                    mainFrame.showPanel("ADMIN");

                } else if (user.getRole().equals("CASHIER")) {
                    mainFrame.showPanel("CASHIER");
                }
                //user validation
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Username or Password.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Add components
        add(Box.createVerticalGlue());
        add(logoImage);
        add(Box.createVerticalStrut(30));
        add(usernameLabel);
        add(Box.createVerticalStrut(5));
        add(username);
        add(Box.createVerticalStrut(20));
        add(passwordLabel);
        add(Box.createVerticalStrut(5));
        add(password);
        add(Box.createVerticalStrut(25));
        add(submitBtn);
        add(Box.createVerticalGlue());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // this was not needed, however placed here to remove exception errors when implementing ActionListener
    }
}
