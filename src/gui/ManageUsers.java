package gui;

import model.User;
import service.UserService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

class Manageusers extends JPanel {
    private JTable userTable;
    private DefaultTableModel tableModel;

    private JTextField usernameField;
    private JTextField fullNameField;
    private JPasswordField passwordField;

    private UserService userService;

    public Manageusers() {
        userService=new UserService();
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        JLabel title=new JLabel("Manage Cashier Accounts");
        title.setFont(new Font("Arial",Font.BOLD,24));
        setBackground(Color.LIGHT_GRAY);
        add(title,BorderLayout.NORTH);

        String[] columns={"ID","Username","Full Name","Role"};
        tableModel=new DefaultTableModel(columns,0) {
            @Override
            public boolean isCellEditable(int row,int column) { return false; }
        };

        userTable=new JTable(tableModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userTable.setRowHeight(30);
        JScrollPane scrollPane=new JScrollPane(userTable);
        add(scrollPane,BorderLayout.CENTER);

        // Creates the form used to enter cashier information
        JPanel formPanel=new JPanel(new GridBagLayout());
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.insets=new Insets(5,5,5,5);
        gbc.fill=GridBagConstraints.HORIZONTAL;

        // Adds the username field
        gbc.gridx=0;
        gbc.gridy=0;
        formPanel.add(new JLabel("Username:"),gbc);
        usernameField=new JTextField(20);
        gbc.gridx=1;
        formPanel.add(usernameField,gbc);

        // Adds the full name field
        gbc.gridx=0;
        gbc.gridy=1;
        formPanel.add(new JLabel("Full Name:"),gbc);
        fullNameField=new JTextField(20);
        gbc.gridx=1;
        formPanel.add(fullNameField,gbc);

        // Adds the password field
        gbc.gridx=0;
        gbc.gridy=2;
        formPanel.add(new JLabel("Password:"),gbc);
        passwordField=new JPasswordField(20);
        gbc.gridx=1;
        formPanel.add(passwordField,gbc);

        //creates the buttons for account management
        JPanel buttonPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton createButton=new JButton("Create Cashier");
        JButton updateButton=new JButton("Update");
        JButton deleteButton=new JButton("Delete");
        JButton clearButton=new JButton("Clear");
        buttonPanel.add(createButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        //places the form and buttons at the bottom
        JPanel bottomPanel=new JPanel(new BorderLayout());
        bottomPanel.add(formPanel,BorderLayout.CENTER);
        bottomPanel.add(buttonPanel,BorderLayout.SOUTH);
        add(bottomPanel,BorderLayout.SOUTH);

        //adding functionality to buttons by connecting their function
        createButton.addActionListener(e->createCashier());
        updateButton.addActionListener(e->updateCashier());
        deleteButton.addActionListener(e->deleteCashier());
        clearButton.addActionListener(e->clearForm());

        //loads the selected cashier into the form
        userTable.getSelectionModel().addListSelectionListener(e->{
            if(!e.getValueIsAdjusting()) loadSelectedUser();
        });
        loadCashiers();
    }

    // Loads all cashier accounts into the table
    private void loadCashiers() {
        tableModel.setRowCount(0);
        List<User> users=userService.getCashiers();

        for(User user:users) {
            tableModel.addRow(new Object[]{
                    user.getUser_id(),user.getUsername(),
                    user.getFull_name(),user.getRole()
            });
        }
    }

    // Creates a new cashier account
    private void createCashier() {
        String username=usernameField.getText().trim();
        String fullName=fullNameField.getText().trim();
        String password=new String(passwordField.getPassword());

        // Checks that all required fields contain information
        if(username.isEmpty()||fullName.isEmpty()||password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please complete all fields.",
                    "Missing Information",JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Creates the cashier account through the service
        boolean success=userService.addUser(username,password,"CASHIER",fullName);

        // Refreshes the table after creating the account
        if(success) {
            JOptionPane.showMessageDialog(this,
                    "Cashier account created successfully.");
            clearForm();
            loadCashiers();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Could not create cashier account.",
                    "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    // Updates the selected cashier account
    private void updateCashier() {
        int selectedRow=userTable.getSelectedRow();

        // Makes sure a cashier has been selected
        if(selectedRow==-1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a cashier first.",
                    "No Selection",JOptionPane.WARNING_MESSAGE);
            return;
        }

        int userId=(int)tableModel.getValueAt(selectedRow,0);
        String username=usernameField.getText().trim();
        String fullName=fullNameField.getText().trim();
        String password=new String(passwordField.getPassword());

        // Checks the required fields
        if(username.isEmpty()||fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Username and full name cannot be empty.",
                    "Invalid Information",JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Gets the existing user so the current password can be kept
        User selectedUser=userService.getUserById(userId);

        if(selectedUser==null) {
            JOptionPane.showMessageDialog(this,
                    "User could not be found.",
                    "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Keeps the existing password when no new password is entered
        if(password.isEmpty()) password=selectedUser.getPassword();

        // Updates the cashier account through the service
        boolean success=userService.updateUser(userId,username,password,"CASHIER",fullName);

        // Refreshes the table after updating
        if(success) {
            JOptionPane.showMessageDialog(this,
                    "Cashier account updated successfully.");
            clearForm();
            loadCashiers();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Could not update cashier account.",
                    "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    // Deletes the selected cashier account
    private void deleteCashier() {
        int selectedRow=userTable.getSelectedRow();

        // Makes sure a cashier has been selected
        if(selectedRow==-1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a cashier first.",
                    "No Selection",JOptionPane.WARNING_MESSAGE);
            return;
        }

        int userId=(int)tableModel.getValueAt(selectedRow,0);
        String username=tableModel.getValueAt(selectedRow,1).toString();

        // Asks the user to confirm the deletion
        int confirmation=JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete "+username+"?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if(confirmation!=JOptionPane.YES_OPTION) return;

        boolean success=userService.deleteUser(userId);

        // Refreshes the table after deletion
        if(success) {
            JOptionPane.showMessageDialog(this,
                    "Cashier account successfully deleted.");
            clearForm();
            loadCashiers();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Could not delete cashier account.","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    // Loads the selected cashier's details into the form
    private void loadSelectedUser() {
        int selectedRow=userTable.getSelectedRow();
        if(selectedRow==-1) return;

        usernameField.setText(tableModel.getValueAt(selectedRow,1).toString());
        fullNameField.setText(tableModel.getValueAt(selectedRow,2).toString());

        // Keeps the password hidden and requires a new one only if needed
        passwordField.setText("");
    }

    // Clears the form and removes the table selection
    private void clearForm() {
        usernameField.setText("");
        fullNameField.setText("");
        passwordField.setText("");
        userTable.clearSelection();
    }
}
