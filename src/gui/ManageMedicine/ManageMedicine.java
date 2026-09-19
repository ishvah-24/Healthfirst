package gui.ManageMedicine;

import model.Medicine;
import model.Supplier;
import service.MedicineService;
import service.SupplierService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class ManageMedicine extends JPanel {

    private JTable medicineTable;
    private DefaultTableModel tableModel;

    private JTextField nameField;
    private JTextField companyField;
    private JTextField typeField;
    private JTextField priceField;
    private JTextField quantityField;
    private JTextField reorderLevelField;
    private JTextField expiryDateField;

    private JComboBox<SupplierItem> supplierComboBox;

    private MedicineService medicineService;
    private SupplierService supplierService;


    public ManageMedicine() {

        medicineService = new MedicineService();
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
                new JLabel("Manage Medicines");

        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        add(title, BorderLayout.NORTH);


        // =========================================
        // MEDICINE TABLE
        // =========================================

        String[] columns = {
                "ID",
                "Name",
                "Company",
                "Type",
                "Price",
                "Stock",
                "Reorder Level",
                "Expiry Date",
                "Supplier"
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


        medicineTable =
                new JTable(tableModel);

        medicineTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        medicineTable.setRowHeight(30);

        medicineTable.setAutoResizeMode(
                JTable.AUTO_RESIZE_LAST_COLUMN
        );


        JScrollPane scrollPane =
                new JScrollPane(medicineTable);

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
        // Medicine Name
        // -----------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 0;

        formPanel.add(
                new JLabel("Medicine Name:"),
                gbc
        );

        nameField =
                new JTextField(18);

        gbc.gridx = 1;

        formPanel.add(
                nameField,
                gbc
        );


        // -----------------------------------------
        // Company
        // -----------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 1;

        formPanel.add(
                new JLabel("Company:"),
                gbc
        );

        companyField =
                new JTextField(18);

        gbc.gridx = 1;

        formPanel.add(
                companyField,
                gbc
        );


        // -----------------------------------------
        // Medicine Type
        // -----------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 2;

        formPanel.add(
                new JLabel("Medicine Type:"),
                gbc
        );

        typeField =
                new JTextField(18);

        gbc.gridx = 1;

        formPanel.add(
                typeField,
                gbc
        );


        // -----------------------------------------
        // Price
        // -----------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 3;

        formPanel.add(
                new JLabel("Price:"),
                gbc
        );

        priceField =
                new JTextField(18);

        gbc.gridx = 1;

        formPanel.add(
                priceField,
                gbc
        );


        // -----------------------------------------
        // Quantity
        // -----------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 4;

        formPanel.add(
                new JLabel("Quantity in Stock:"),
                gbc
        );

        quantityField =
                new JTextField(18);

        gbc.gridx = 1;

        formPanel.add(
                quantityField,
                gbc
        );


        // -----------------------------------------
        // Reorder Level
        // -----------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 5;

        formPanel.add(
                new JLabel("Reorder Level:"),
                gbc
        );

        reorderLevelField =
                new JTextField(18);

        gbc.gridx = 1;

        formPanel.add(
                reorderLevelField,
                gbc
        );


        // -----------------------------------------
        // Expiry Date
        // -----------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 6;

        formPanel.add(
                new JLabel("Expiry Date:"),
                gbc
        );

        expiryDateField =
                new JTextField(18);

        expiryDateField.setToolTipText(
                "Format: YYYY-MM-DD"
        );

        gbc.gridx = 1;

        formPanel.add(
                expiryDateField,
                gbc
        );


        // -----------------------------------------
        // Supplier
        // -----------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 7;

        formPanel.add(
                new JLabel("Supplier:"),
                gbc
        );

        supplierComboBox =
                new JComboBox<>();

        gbc.gridx = 1;

        formPanel.add(
                supplierComboBox,
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
                new JButton("Add Medicine");

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
                e -> addMedicine()
        );

        updateButton.addActionListener(
                e -> updateMedicine()
        );

        deleteButton.addActionListener(
                e -> deleteMedicine()
        );

        clearButton.addActionListener(
                e -> clearForm()
        );


        // =========================================
        // TABLE SELECTION
        // =========================================

        medicineTable.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        loadSelectedMedicine();
                    }
                });


        // =========================================
        // LOAD SUPPLIERS
        // =========================================

        loadSuppliers();


        // =========================================
        // LOAD MEDICINES
        // =========================================

        loadMedicines();
    }


    // =========================================
    // LOAD SUPPLIERS
    // =========================================

    private void loadSuppliers() {

        supplierComboBox.removeAllItems();

        List<Supplier> suppliers =
                supplierService.getAllSuppliers();


        for (Supplier supplier : suppliers) {

            supplierComboBox.addItem(
                    new SupplierItem(
                            supplier.getSupplier_id(),
                            supplier.getName()
                    )
            );
        }
    }


    // =========================================
    // LOAD MEDICINES
    // =========================================

    private void loadMedicines() {

        tableModel.setRowCount(0);

        List<Medicine> medicines =
                medicineService.getAllMedicines();


        for (Medicine medicine : medicines) {

            String supplierName =
                    getSupplierName(
                            medicine.getSupplier_id()
                    );


            tableModel.addRow(
                    new Object[]{
                            medicine.getMedicine_id(),
                            medicine.getName(),
                            medicine.getCompany(),
                            medicine.getMedicine_type(),
                            medicine.getPrice(),
                            medicine.getQuantity_in_stock(),
                            medicine.getReorder_level(),
                            medicine.getExpiry_date(),
                            supplierName
                    }
            );
        }
    }


    // =========================================
    // GET SUPPLIER NAME
    // =========================================

    private String getSupplierName(int supplierId) {

        for (int i = 0;
             i < supplierComboBox.getItemCount();
             i++) {

            SupplierItem supplier =
                    supplierComboBox.getItemAt(i);


            if (supplier.getId() == supplierId) {

                return supplier.getName();
            }
        }

        return "Unknown";
    }


    // =========================================
    // ADD MEDICINE
    // =========================================

    private void addMedicine() {

        try {

            String name =
                    nameField.getText().trim();

            String company =
                    companyField.getText().trim();

            String type =
                    typeField.getText().trim();

            double price =
                    Double.parseDouble(
                            priceField.getText().trim()
                    );

            int quantity =
                    Integer.parseInt(
                            quantityField.getText().trim()
                    );

            int reorderLevel =
                    Integer.parseInt(
                            reorderLevelField
                                    .getText()
                                    .trim()
                    );

            Date expiryDate =
                    Date.valueOf(
                            expiryDateField
                                    .getText()
                                    .trim()
                    );


            SupplierItem supplier =
                    (SupplierItem)
                            supplierComboBox
                                    .getSelectedItem();


            // =====================================
            // VALIDATION
            // =====================================

            if (name.isEmpty()
                    || company.isEmpty()
                    || type.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please complete all fields.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            if (supplier == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a supplier.",
                        "Missing Supplier",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            if (price < 0
                    || quantity < 0
                    || reorderLevel < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Price, quantity and reorder level " +
                                "cannot be negative.",
                        "Invalid Information",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            boolean success =
                    medicineService.addMedicine(
                            name,
                            company,
                            type,
                            price,
                            quantity,
                            reorderLevel,
                            expiryDate,
                            supplier.getId()
                    );


            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Medicine added successfully."
                );

                clearForm();

                loadMedicines();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Could not add medicine.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }


        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Price, quantity and reorder level " +
                            "must contain valid numbers.",
                    "Invalid Number",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Expiry date must use the format " +
                            "YYYY-MM-DD.",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }


    // =========================================
    // UPDATE MEDICINE
    // =========================================

    private void updateMedicine() {

        int selectedRow =
                medicineTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a medicine first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        try {

            int medicineId =
                    (int) tableModel.getValueAt(
                            selectedRow,
                            0
                    );


            String name =
                    nameField.getText().trim();

            String company =
                    companyField.getText().trim();

            String type =
                    typeField.getText().trim();

            double price =
                    Double.parseDouble(
                            priceField.getText().trim()
                    );

            int quantity =
                    Integer.parseInt(
                            quantityField.getText().trim()
                    );

            int reorderLevel =
                    Integer.parseInt(
                            reorderLevelField
                                    .getText()
                                    .trim()
                    );

            Date expiryDate =
                    Date.valueOf(
                            expiryDateField
                                    .getText()
                                    .trim()
                    );


            SupplierItem supplier =
                    (SupplierItem)
                            supplierComboBox
                                    .getSelectedItem();


            // =====================================
            // VALIDATION
            // =====================================

            if (name.isEmpty()
                    || company.isEmpty()
                    || type.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please complete all fields.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            if (supplier == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a supplier.",
                        "Missing Supplier",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            if (price < 0
                    || quantity < 0
                    || reorderLevel < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Price, quantity and reorder level " +
                                "cannot be negative.",
                        "Invalid Information",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            boolean success =
                    medicineService.updateMedicine(
                            medicineId,
                            name,
                            company,
                            type,
                            price,
                            quantity,
                            reorderLevel,
                            expiryDate,
                            supplier.getId()
                    );


            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Medicine updated successfully."
                );

                clearForm();

                loadMedicines();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Could not update medicine.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }


        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Price, quantity and reorder level " +
                            "must contain valid numbers.",
                    "Invalid Number",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Expiry date must use the format " +
                            "YYYY-MM-DD.",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }


    // =========================================
    // DELETE MEDICINE
    // =========================================

    private void deleteMedicine() {

        int selectedRow =
                medicineTable.getSelectedRow();


        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a medicine first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        int medicineId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );


        String medicineName =
                tableModel.getValueAt(
                        selectedRow,
                        1
                ).toString();


        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete "
                                + medicineName + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (confirmation != JOptionPane.YES_OPTION) {

            return;
        }


        boolean success =
                medicineService.deleteMedicine(
                        medicineId
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Medicine deleted successfully."
            );

            clearForm();

            loadMedicines();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not delete medicine.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================
    // LOAD SELECTED MEDICINE
    // =========================================

    private void loadSelectedMedicine() {

        int selectedRow =
                medicineTable.getSelectedRow();


        if (selectedRow == -1) {
            return;
        }


        nameField.setText(
                tableModel.getValueAt(
                        selectedRow,
                        1
                ).toString()
        );


        companyField.setText(
                tableModel.getValueAt(
                        selectedRow,
                        2
                ).toString()
        );


        typeField.setText(
                tableModel.getValueAt(
                        selectedRow,
                        3
                ).toString()
        );


        priceField.setText(
                tableModel.getValueAt(
                        selectedRow,
                        4
                ).toString()
        );


        quantityField.setText(
                tableModel.getValueAt(
                        selectedRow,
                        5
                ).toString()
        );


        reorderLevelField.setText(
                tableModel.getValueAt(
                        selectedRow,
                        6
                ).toString()
        );


        expiryDateField.setText(
                tableModel.getValueAt(
                        selectedRow,
                        7
                ).toString()
        );


        // Find the supplier from the medicine's ID
        int medicineId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );


        Medicine medicine =
                medicineService.getMedicineById(
                        medicineId
                );


        if (medicine != null) {

            selectSupplier(
                    medicine.getSupplier_id()
            );
        }
    }


    // =========================================
    // SELECT SUPPLIER
    // =========================================

    private void selectSupplier(int supplierId) {

        for (int i = 0;
             i < supplierComboBox.getItemCount();
             i++) {

            SupplierItem supplier =
                    supplierComboBox.getItemAt(i);


            if (supplier.getId() == supplierId) {

                supplierComboBox.setSelectedIndex(i);

                return;
            }
        }
    }


    // =========================================
    // CLEAR FORM
    // =========================================

    private void clearForm() {

        nameField.setText("");

        companyField.setText("");

        typeField.setText("");

        priceField.setText("");

        quantityField.setText("");

        reorderLevelField.setText("");

        expiryDateField.setText("");

        if (supplierComboBox.getItemCount() > 0) {

            supplierComboBox.setSelectedIndex(0);
        }

        medicineTable.clearSelection();
    }


    // =========================================
    // SUPPLIER COMBOBOX ITEM
    // =========================================

    private static class SupplierItem {

        private int id;
        private String name;


        public SupplierItem(
                int id,
                String name) {

            this.id = id;
            this.name = name;
        }


        public int getId() {
            return id;
        }


        public String getName() {
            return name;
        }


        @Override
        public String toString() {

            return name;
        }
    }
}