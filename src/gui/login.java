package gui;

import gui.Admin;
import gui.Cashier;
import gui.Main;
import model.User;
import service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class login extends JPanel implements ActionListener {

    private Main mainFrame;

    public login(Main mainFrame){
        this.mainFrame = mainFrame;

        //this layout manager allows for components to be stacked one on top of each other
        //instead of being in line on the x-axis
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.setBackground(Color.WHITE);

        //image icon on gui.login screen
        ImageIcon logo = new ImageIcon("health_low_res.png");
        JLabel logoImage = new JLabel(logo);

        //labels for textfield indication
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");

        JTextField username = new JTextField();
        JTextField password = new JTextField();

        //configuring size of textfields
        username.setPreferredSize(new Dimension(20, 40));
        password.setPreferredSize(new Dimension(20, 40));

        JButton submitBtn = new JButton(new AbstractAction("Submit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username_ = username.getText();
                String password_ = password.getText();

                UserService.login(username_, password_);
                System.out.println(username_ + password_);

                User user = UserService.login(username_, password_);

                if( user != null) {
                    if(user.getRole().equals("ADMIN")){
                        Admin admin = new Admin(mainFrame);
                        mainFrame.showPanel("ADMIN");

                    }else if (user.getRole().equals("CASHIER")) {
                        Cashier cashier = new Cashier(mainFrame);
                        mainFrame.showPanel("CASHIER");
                    }

                }else{
                    System.out.println("Invalid Username or Password, try again.");
                    JLabel invalidLogin = new JLabel("Invalid Username or Password, try again.");
                    add(invalidLogin);
                }

            }
        });

        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);


        add(logoImage, Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(20));
        add(usernameLabel, Component.LEFT_ALIGNMENT);
        add(username, Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(20));
        add(passwordLabel, Component.LEFT_ALIGNMENT);
        add(password, Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(20));
        add(submitBtn, Component.CENTER_ALIGNMENT);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
