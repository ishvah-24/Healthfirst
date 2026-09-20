package gui;

import model.Medicine;
import service.MedicineService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MedicinePanel extends JPanel {

    private MedicineService medicineService;

    private JTable medicineTable;
    private DefaultTableModel medicineTableModel;

    private JTextField searchField;
    private JTextField quantityField;

    // Used to tell CartPanel when a medicine is added
    private CartPanel cartPanel;

    //build the medicine panel
    public MedicinePanel(CartPanel cartPanel) {

        this.cartPanel = cartPanel;
        medicineService = new MedicineService();

        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createTitledBorder("Available Medicines"));

        //search section
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));

        searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        JButton refreshButton = new JButton("Refresh");

        searchPanel.add(new JLabel("Search:"), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        add(searchPanel, BorderLayout.NORTH);

        // Medicine table
        String[] columns = {
                "ID", "Name", "Company", "Type", "Price", "Stock"
        };

        medicineTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        medicineTable = new JTable(medicineTableModel);
        medicineTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        medicineTable.setRowHeight(28);

        add(new JScrollPane(medicineTable), BorderLayout.CENTER);

        // Quantity and add button
        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        addPanel.add(new JLabel("Quantity:"));

        quantityField = new JTextField("1", 5);

        JButton addButton = new JButton("Add to Cart");

        addPanel.add(quantityField);
        addPanel.add(addButton);

        add(addPanel, BorderLayout.SOUTH);

        // Button actions
        searchButton.addActionListener(e -> searchMedicines());
        refreshButton.addActionListener(e -> loadMedicines());
        addButton.addActionListener(e -> addToCart());

        // Load medicines when panel opens
        loadMedicines();
    }

    // Load all medicines
    private void loadMedicines() {

        medicineTableModel.setRowCount(0);

        try {
            List<Medicine> medicines = medicineService.getAllMedicines();

            for (Medicine medicine : medicines) {
                medicineTableModel.addRow(new Object[]{
                        medicine.getMedicine_id(),
                        medicine.getName(),
                        medicine.getCompany(),
                        medicine.getMedicine_type(),
                        medicine.getPrice(),
                        medicine.getQuantity_in_stock()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load medicines.\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Search medicines
    private void searchMedicines() {

        String search = searchField.getText().trim().toLowerCase();

        if (search.isEmpty()) {
            loadMedicines();
            return;
        }

        medicineTableModel.setRowCount(0);

        try {
            List<Medicine> medicines = medicineService.getAllMedicines();

            for (Medicine medicine : medicines) {

                if (medicine.getName().toLowerCase().contains(search)
                        || medicine.getCompany().toLowerCase().contains(search)
                        || medicine.getMedicine_type().toLowerCase().contains(search)) {

                    medicineTableModel.addRow(new Object[]{
                            medicine.getMedicine_id(),
                            medicine.getName(),
                            medicine.getCompany(),
                            medicine.getMedicine_type(),
                            medicine.getPrice(),
                            medicine.getQuantity_in_stock()
                    });
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Search failed.\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Add selected medicine to cart
    private void addToCart() {

        int selectedRow = medicineTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a medicine first.",
                    "No Medicine Selected",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        int quantity;

        try {
            quantity = Integer.parseInt(quantityField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid quantity.",
                    "Invalid Quantity",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (quantity <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Quantity must be greater than 0.",
                    "Invalid Quantity",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Get selected medicine information
        int medicineId = (Integer) medicineTable.getValueAt(selectedRow, 0);
        String name = medicineTable.getValueAt(selectedRow, 1).toString();
        double price = ((Number) medicineTable.getValueAt(selectedRow, 4)).doubleValue();
        int stock = ((Number) medicineTable.getValueAt(selectedRow, 5)).intValue();

        // Check stock
        if (quantity > stock) {
            JOptionPane.showMessageDialog(
                    this,
                    "Not enough stock available.\n\nAvailable stock: " + stock,
                    "Insufficient Stock",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Send medicine to cart
        cartPanel.addMedicine(medicineId, name, price, quantity);
        quantityField.setText("1");
    }
}