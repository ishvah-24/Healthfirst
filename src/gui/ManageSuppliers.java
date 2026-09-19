package gui;

import model.Supplier;
import service.SupplierService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageSuppliers extends JPanel {

    private JTable supplierTable;
    private DefaultTableModel tableModel;

    private JTextField nameField;
    private JTextField contactPersonField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField addressField;

    private SupplierService supplierService;


    public ManageSuppliers() {

        supplierService = new SupplierService();

        setLayout(new BorderLayout(10, 10));

        setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );


        // =========================================
        // TITLE
        // =========================================

        JLabel title =
                new JLabel("Manage Suppliers");

        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        add(title, BorderLayout.NORTH);


        // =========================================
        // SUPPLIER TABLE
        // =========================================

        String[] columns = {
                "ID",
                "Name",
                "Contact Person",
                "Phone",
                "Email",
                "Address"
        };

        tableModel =
                new DefaultTableModel(
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


        supplierTable =
                new JTable(tableModel);

        supplierTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        supplierTable.setRowHeight(30);

        supplierTable.setAutoResizeMode(
                JTable.AUTO_RESIZE_LAST_COLUMN
        );


        JScrollPane scrollPane =
                new JScrollPane(supplierTable);

        add(
                scrollPane,
                BorderLayout.CENTER
        );


        // =========================================
        // FORM
        // =========================================

        JPanel formPanel =
                new JPanel(new GridBagLayout());

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(5, 5, 5, 5);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        // -----------------------------------------
        // Name
        // -----------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 0;

        formPanel.add(
                new JLabel("Supplier Name:"),
                gbc
        );

        nameField =
                new JTextField(20);

        gbc.gridx = 1;

        formPanel.add(
                nameField,
                gbc
        );


        // -----------------------------------------
        // Contact Person
        // -----------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 1;

        formPanel.add(
                new JLabel("Contact Person:"),
                gbc
        );

        contactPersonField =
                new JTextField(20);

        gbc.gridx = 1;

        formPanel.add(
                contactPersonField,
                gbc
        );


        // -----------------------------------------
        // Phone
        // -----------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 2;

        formPanel.add(
                new JLabel("Phone:"),
                gbc
        );

        phoneField =
                new JTextField(20);

        gbc.gridx = 1;

        formPanel.add(
                phoneField,
                gbc
        );


        // -----------------------------------------
        // Email
        // -----------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 3;

        formPanel.add(
                new JLabel("Email:"),
                gbc
        );

        emailField =
                new JTextField(20);

        gbc.gridx = 1;

        formPanel.add(
                emailField,
                gbc
        );


        // -----------------------------------------
        // Address
        // -----------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 4;

        formPanel.add(
                new JLabel("Address:"),
                gbc
        );

        addressField =
                new JTextField(20);

        gbc.gridx = 1;

        formPanel.add(
                addressField,
                gbc
        );


        // =========================================
        // BUTTONS
        // =========================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );


        JButton addButton =
                new JButton("Add Supplier");

        JButton updateButton =
                new JButton("Update");

        JButton deleteButton =
                new JButton("Delete");

        JButton clearButton =
                new JButton("Clear");


        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);


        // =========================================
        // BOTTOM PANEL
        // =========================================

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


        // =========================================
        // BUTTON EVENTS
        // =========================================

        addButton.addActionListener(
                e -> addSupplier()
        );

        updateButton.addActionListener(
                e -> updateSupplier()
        );

        deleteButton.addActionListener(
                e -> deleteSupplier()
        );

        clearButton.addActionListener(
                e -> clearForm()
        );


        // =========================================
        // TABLE SELECTION
        // =========================================

        supplierTable.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        loadSelectedSupplier();
                    }
                });


        // =========================================
        // LOAD SUPPLIERS
        // =========================================

        loadSuppliers();
    }


    // =========================================
    // LOAD SUPPLIERS
    // =========================================

    private void loadSuppliers() {

        tableModel.setRowCount(0);

        List<Supplier> suppliers =
                supplierService.getAllSuppliers();


        for (Supplier supplier : suppliers) {

            tableModel.addRow(
                    new Object[]{
                            supplier.getSupplier_id(),
                            supplier.getName(),
                            supplier.getContact_person(),
                            supplier.getPhone(),
                            supplier.getEmail(),
                            supplier.getAddress()
                    }
            );
        }
    }


    // =========================================
    // ADD SUPPLIER
    // =========================================

    private void addSupplier() {

        String name =
                nameField.getText().trim();

        String contactPerson =
                contactPersonField.getText().trim();

        String phone =
                phoneField.getText().trim();

        String email =
                emailField.getText().trim();

        String address =
                addressField.getText().trim();


        // =====================================
        // VALIDATION
        // =====================================

        if (name.isEmpty()
                || contactPerson.isEmpty()
                || phone.isEmpty()
                || email.isEmpty()
                || address.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please complete all fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        boolean success =
                supplierService.addSupplier(
                        name,
                        contactPerson,
                        phone,
                        email,
                        address
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Supplier added successfully."
            );

            clearForm();

            loadSuppliers();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not add supplier.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================
    // UPDATE SUPPLIER
    // =========================================

    private void updateSupplier() {

        int selectedRow =
                supplierTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a supplier first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        int supplierId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );


        String name =
                nameField.getText().trim();

        String contactPerson =
                contactPersonField.getText().trim();

        String phone =
                phoneField.getText().trim();

        String email =
                emailField.getText().trim();

        String address =
                addressField.getText().trim();


        // =====================================
        // VALIDATION
        // =====================================

        if (name.isEmpty()
                || contactPerson.isEmpty()
                || phone.isEmpty()
                || email.isEmpty()
                || address.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please complete all fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        boolean success =
                supplierService.updateSupplier(
                        supplierId,
                        name,
                        contactPerson,
                        phone,
                        email,
                        address
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Supplier updated successfully."
            );

            clearForm();

            loadSuppliers();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not update supplier.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================
    // DELETE SUPPLIER
    // =========================================

    private void deleteSupplier() {

        int selectedRow =
                supplierTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a supplier first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        int supplierId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );


        String supplierName =
                tableModel.getValueAt(
                        selectedRow,
                        1
                ).toString();


        // =====================================
        // CONFIRM DELETE
        // =====================================

        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete "
                                + supplierName + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (confirmation != JOptionPane.YES_OPTION) {

            return;
        }


        boolean success =
                supplierService.deleteSupplier(
                        supplierId
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Supplier deleted successfully."
            );

            clearForm();

            loadSuppliers();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not delete supplier.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================
    // LOAD SELECTED SUPPLIER
    // =========================================

    private void loadSelectedSupplier() {

        int selectedRow =
                supplierTable.getSelectedRow();


        if (selectedRow == -1) {
            return;
        }


        nameField.setText(
                tableModel.getValueAt(
                        selectedRow,
                        1
                ).toString()
        );


        contactPersonField.setText(
                tableModel.getValueAt(
                        selectedRow,
                        2
                ).toString()
        );


        phoneField.setText(
                tableModel.getValueAt(
                        selectedRow,
                        3
                ).toString()
        );


        emailField.setText(
                tableModel.getValueAt(
                        selectedRow,
                        4
                ).toString()
        );


        addressField.setText(
                tableModel.getValueAt(
                        selectedRow,
                        5
                ).toString()
        );
    }


    // =========================================
    // CLEAR FORM
    // =========================================

    private void clearForm() {

        nameField.setText("");

        contactPersonField.setText("");

        phoneField.setText("");

        emailField.setText("");

        addressField.setText("");

        supplierTable.clearSelection();
    }
}