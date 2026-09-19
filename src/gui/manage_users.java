package gui;

import model.User;
import service.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

class manage_users extends JPanel {

    private JTable userTable;
    private DefaultTableModel tableModel;

    private JTextField usernameField;
    private JTextField fullNameField;
    private JPasswordField passwordField;

    private UserService userService;

    public manage_users() {

        userService = new UserService();

        setLayout(new BorderLayout(10, 10));

        setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );

        // =====================================
        // TITLE
        // =====================================

        JLabel title = new JLabel("Manage Cashier Accounts");

        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        add(title, BorderLayout.NORTH);


        // =====================================
        // TABLE
        // =====================================

        String[] columns = {
                "ID",
                "Username",
                "Full Name",
                "Role"
        };

        tableModel = new DefaultTableModel(
                columns,
                0
        ) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };

        userTable = new JTable(tableModel);

        userTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        userTable.setRowHeight(30);

        JScrollPane scrollPane =
                new JScrollPane(userTable);

        add(
                scrollPane,
                BorderLayout.CENTER
        );


        // =====================================
        // FORM
        // =====================================

        JPanel formPanel =
                new JPanel(new GridBagLayout());

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(5, 5, 5, 5);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;

        formPanel.add(
                new JLabel("Username:"),
                gbc
        );

        usernameField =
                new JTextField(20);

        gbc.gridx = 1;

        formPanel.add(
                usernameField,
                gbc
        );


        // Full Name
        gbc.gridx = 0;
        gbc.gridy = 1;

        formPanel.add(
                new JLabel("Full Name:"),
                gbc
        );

        fullNameField =
                new JTextField(20);

        gbc.gridx = 1;

        formPanel.add(
                fullNameField,
                gbc
        );


        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;

        formPanel.add(
                new JLabel("Password:"),
                gbc
        );

        passwordField =
                new JPasswordField(20);

        gbc.gridx = 1;

        formPanel.add(
                passwordField,
                gbc
        );


        // =====================================
        // BUTTONS
        // =====================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        JButton createButton =
                new JButton("Create Cashier");

        JButton updateButton =
                new JButton("Update");

        JButton deleteButton =
                new JButton("Delete");

        JButton clearButton =
                new JButton("Clear");


        buttonPanel.add(createButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);


        // =====================================
        // BOTTOM PANEL
        // =====================================

        JPanel bottomPanel =
                new JPanel(new BorderLayout());

        bottomPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        bottomPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        // =====================================
        // BUTTON EVENTS
        // =====================================

        createButton.addActionListener(
                e -> createCashier()
        );

        updateButton.addActionListener(
                e -> updateCashier()
        );

        deleteButton.addActionListener(
                e -> deleteCashier()
        );

        clearButton.addActionListener(
                e -> clearForm()
        );


        // =====================================
        // TABLE SELECTION
        // =====================================

        userTable.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {
                        loadSelectedUser();
                    }

                });


        // =====================================
        // LOAD CASHIERS
        // =====================================

        loadCashiers();
    }


    // =========================================
    // LOAD CASHIERS
    // =========================================

    private void loadCashiers() {

        tableModel.setRowCount(0);

        List<User> users =
                userService.getCashiers();

        for (User user : users) {

            tableModel.addRow(
                    new Object[]{
                            user.getUser_id(),
                            user.getUsername(),
                            user.getFull_name(),
                            user.getRole()
                    }
            );
        }
    }


    // =========================================
    // CREATE CASHIER
    // =========================================

    private void createCashier() {

        String username =
                usernameField.getText().trim();

        String fullName =
                fullNameField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );


        // Validation
        if (username.isEmpty()
                || fullName.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please complete all fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        boolean success =
                userService.addUser(
                        username,
                        password,
                        "CASHIER",
                        fullName
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Cashier account created successfully."
            );

            clearForm();

            loadCashiers();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not create cashier account.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================
    // UPDATE CASHIER
    // =========================================

    private void updateCashier() {

        int selectedRow =
                userTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a cashier first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        int userId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );


        String username =
                usernameField.getText().trim();

        String fullName =
                fullNameField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );


        if (username.isEmpty()
                || fullName.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Username and full name cannot be empty.",
                    "Invalid Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        /*
         * If the password field is empty,
         * keep the existing password.
         */
        User selectedUser =
                userService.getUserById(userId);

        if (selectedUser == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "User could not be found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        if (password.isEmpty()) {

            password =
                    selectedUser.getPassword();
        }


        boolean success =
                userService.updateUser(
                        userId,
                        username,
                        password,
                        "CASHIER",
                        fullName
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Cashier account updated successfully."
            );

            clearForm();

            loadCashiers();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not update cashier account.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================
    // DELETE CASHIER
    // =========================================

    private void deleteCashier() {

        int selectedRow =
                userTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a cashier first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        int userId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );

        String username =
                tableModel.getValueAt(
                        selectedRow,
                        1
                ).toString();


        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete "
                                + username + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }


        boolean success =
                userService.deleteUser(userId);


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Cashier account deleted successfully."
            );

            clearForm();

            loadCashiers();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not delete cashier account.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================
    // LOAD SELECTED USER
    // =========================================

    private void loadSelectedUser() {

        int selectedRow =
                userTable.getSelectedRow();


        if (selectedRow == -1) {
            return;
        }


        usernameField.setText(
                tableModel.getValueAt(
                        selectedRow,
                        1
                ).toString()
        );


        fullNameField.setText(
                tableModel.getValueAt(
                        selectedRow,
                        2
                ).toString()
        );


        // Don't display the password
        passwordField.setText("");
    }


    // =========================================
    // CLEAR FORM
    // =========================================

    private void clearForm() {

        usernameField.setText("");

        fullNameField.setText("");

        passwordField.setText("");

        userTable.clearSelection();
    }
}