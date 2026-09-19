package gui;

import model.Medicine;
import service.MedicineService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

public class Cashier extends JPanel {

    private Main mainFrame;
    private MedicineService medicineService;

    // Medicine table
    private JTable medicineTable;
    private DefaultTableModel medicineTableModel;

    // Cart table
    private JTable cartTable;
    private DefaultTableModel cartTableModel;

    // Fields
    private JTextField searchField;
    private JTextField quantityField;

    // Total labels
    private JLabel subtotalLabel;
    private JLabel vatLabel;
    private JLabel totalLabel;

    private DecimalFormat moneyFormat =
            new DecimalFormat("R #,##0.00");

    private static final double VAT_RATE = 0.15;


    public Cashier(Main mainFrame) {

        this.mainFrame = mainFrame;
        this.medicineService = new MedicineService();

        setLayout(new BorderLayout(10, 10));

        setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );

        setBackground(Color.WHITE);


        // =====================================================
        // TOP PANEL
        // =====================================================

        JPanel topPanel =
                new JPanel(new BorderLayout());

        JLabel title =
                new JLabel("HealthFirst Pharmacy - Point of Sale");

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        JButton logoutButton =
                new JButton("Logout");

        logoutButton.addActionListener(e -> {
            mainFrame.showPanel("LOGIN");
        });

        topPanel.add(
                title,
                BorderLayout.WEST
        );

        topPanel.add(
                logoutButton,
                BorderLayout.EAST
        );

        add(
                topPanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // CENTER
        // =====================================================

        JPanel centerPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                10,
                                0
                        )
                );

        centerPanel.setBackground(Color.WHITE);


        // =====================================================
        // MEDICINES PANEL
        // =====================================================

        JPanel medicinePanel =
                new JPanel(
                        new BorderLayout(
                                5,
                                5
                        )
                );

        medicinePanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Available Medicines"
                )
        );


        // -------------------------
        // Search
        // -------------------------

        JPanel searchPanel =
                new JPanel(
                        new BorderLayout(5, 5)
                );

        searchField =
                new JTextField();

        JButton searchButton =
                new JButton("Search");

        JButton refreshButton =
                new JButton("Refresh");

        searchPanel.add(
                new JLabel("Search:"),
                BorderLayout.WEST
        );

        searchPanel.add(
                searchField,
                BorderLayout.CENTER
        );

        searchPanel.add(
                searchButton,
                BorderLayout.EAST
        );

        medicinePanel.add(
                searchPanel,
                BorderLayout.NORTH
        );


        // -------------------------
        // Medicine table
        // -------------------------

        medicineTableModel =
                new DefaultTableModel(
                        new Object[]{
                                "ID",
                                "Name",
                                "Company",
                                "Type",
                                "Price",
                                "Stock"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };


        medicineTable =
                new JTable(
                        medicineTableModel
                );

        medicineTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        medicineTable.setRowHeight(28);


        JScrollPane medicineScrollPane =
                new JScrollPane(
                        medicineTable
                );

        medicinePanel.add(
                medicineScrollPane,
                BorderLayout.CENTER
        );


        // -------------------------
        // Quantity / Add button
        // -------------------------

        JPanel addCartPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        addCartPanel.add(
                new JLabel("Quantity:")
        );

        quantityField =
                new JTextField(
                        "1",
                        5
                );

        JButton addToCartButton =
                new JButton("Add to Cart");

        addCartPanel.add(
                quantityField
        );

        addCartPanel.add(
                addToCartButton
        );

        medicinePanel.add(
                addCartPanel,
                BorderLayout.SOUTH
        );


        // =====================================================
        // CART PANEL
        // =====================================================

        JPanel cartPanel =
                new JPanel(
                        new BorderLayout(
                                5,
                                5
                        )
                );

        cartPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Current Sale"
                )
        );


        // -------------------------
        // Cart buttons
        // -------------------------

        JPanel cartButtonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        JButton removeButton =
                new JButton("Remove Item");

        JButton clearCartButton =
                new JButton("Clear Cart");

        cartButtonPanel.add(
                removeButton
        );

        cartButtonPanel.add(
                clearCartButton
        );

        cartPanel.add(
                cartButtonPanel,
                BorderLayout.NORTH
        );


        // -------------------------
        // Cart table
        // -------------------------

        cartTableModel =
                new DefaultTableModel(
                        new Object[]{
                                "ID",
                                "Medicine",
                                "Price",
                                "Qty",
                                "Total"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };


        cartTable =
                new JTable(
                        cartTableModel
                );

        cartTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        cartTable.setRowHeight(28);


        JScrollPane cartScrollPane =
                new JScrollPane(
                        cartTable
                );

        cartPanel.add(
                cartScrollPane,
                BorderLayout.CENTER
        );


        // =====================================================
        // TOTALS
        // =====================================================

        JPanel totalsPanel =
                new JPanel(
                        new GridLayout(
                                3,
                                2,
                                5,
                                5
                        )
                );

        totalsPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );


        // Subtotal

        totalsPanel.add(
                new JLabel("Subtotal:")
        );

        subtotalLabel =
                new JLabel("R 0.00");

        subtotalLabel.setHorizontalAlignment(
                SwingConstants.RIGHT
        );

        totalsPanel.add(
                subtotalLabel
        );


        // VAT

        totalsPanel.add(
                new JLabel("VAT (15%):")
        );

        vatLabel =
                new JLabel("R 0.00");

        vatLabel.setHorizontalAlignment(
                SwingConstants.RIGHT
        );

        totalsPanel.add(
                vatLabel
        );


        // Total

        JLabel totalText =
                new JLabel("TOTAL:");

        totalText.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        totalsPanel.add(
                totalText
        );


        totalLabel =
                new JLabel("R 0.00");

        totalLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        totalLabel.setHorizontalAlignment(
                SwingConstants.RIGHT
        );

        totalsPanel.add(
                totalLabel
        );


        cartPanel.add(
                totalsPanel,
                BorderLayout.SOUTH
        );


        // Add both panels

        centerPanel.add(
                medicinePanel
        );

        centerPanel.add(
                cartPanel
        );


        add(
                centerPanel,
                BorderLayout.CENTER
        );


        // =====================================================
        // BOTTOM
        // =====================================================

        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                5
                        )
                );

        JButton billButton =
                new JButton("View Bill");

        JButton checkoutButton =
                new JButton("Checkout");

        checkoutButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        bottomPanel.add(
                billButton
        );

        bottomPanel.add(
                checkoutButton
        );

        add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        // =====================================================
        // BUTTON ACTIONS
        // =====================================================

        searchButton.addActionListener(
                e -> searchMedicines()
        );

        refreshButton.addActionListener(
                e -> loadMedicines()
        );

        addToCartButton.addActionListener(
                e -> addToCart()
        );

        removeButton.addActionListener(
                e -> removeFromCart()
        );

        clearCartButton.addActionListener(
                e -> clearCart()
        );

        billButton.addActionListener(
                e -> showBill()
        );

        checkoutButton.addActionListener(
                e -> checkout()
        );


        // =====================================================
        // LOAD MEDICINES
        // =====================================================

        loadMedicines();
    }


    // =====================================================
    // LOAD MEDICINES
    // =====================================================

    private void loadMedicines() {

        medicineTableModel.setRowCount(0);

        try {

            List<Medicine> medicines =
                    medicineService.getAllMedicines();

            for (Medicine medicine : medicines) {

                medicineTableModel.addRow(
                        new Object[]{
                                medicine.getMedicine_id(),
                                medicine.getName(),
                                medicine.getCompany(),
                                medicine.getMedicine_type(),

                                // Store the actual number as a Double
                                medicine.getPrice(),

                                medicine.getQuantity_in_stock()
                        }
                );
            }

            // Display prices nicely
            medicineTable.getColumnModel()
                    .getColumn(4)
                    .setCellRenderer(
                            new PriceRenderer()
                    );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not load medicines.\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =====================================================
    // SEARCH MEDICINES
    // =====================================================

    private void searchMedicines() {

        String search =
                searchField.getText()
                        .trim()
                        .toLowerCase();

        if (search.isEmpty()) {

            loadMedicines();

            return;
        }


        medicineTableModel.setRowCount(0);

        try {

            List<Medicine> medicines =
                    medicineService.getAllMedicines();

            for (Medicine medicine : medicines) {

                if (
                        medicine.getName()
                                .toLowerCase()
                                .contains(search)

                                ||

                                medicine.getCompany()
                                        .toLowerCase()
                                        .contains(search)

                                ||

                                medicine.getMedicine_type()
                                        .toLowerCase()
                                        .contains(search)
                ) {

                    medicineTableModel.addRow(
                            new Object[]{
                                    medicine.getMedicine_id(),
                                    medicine.getName(),
                                    medicine.getCompany(),
                                    medicine.getMedicine_type(),
                                    medicine.getPrice(),
                                    medicine.getQuantity_in_stock()
                            }
                    );
                }
            }

            medicineTable.getColumnModel()
                    .getColumn(4)
                    .setCellRenderer(
                            new PriceRenderer()
                    );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Search failed.\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =====================================================
    // ADD TO CART
    // =====================================================

    private void addToCart() {

        // First make sure a medicine was selected

        int selectedRow =
                medicineTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a medicine first.",
                    "No Medicine Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // Only validate quantity when
        // Add to Cart is pressed

        int quantity;

        try {

            quantity =
                    Integer.parseInt(
                            quantityField
                                    .getText()
                                    .trim()
                    );

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


        // Get medicine information

        int medicineId =
                (Integer)
                        medicineTable
                                .getValueAt(
                                        selectedRow,
                                        0
                                );


        String medicineName =
                medicineTable
                        .getValueAt(
                                selectedRow,
                                1
                        )
                        .toString();


        double price =
                ((Number)
                        medicineTable
                                .getValueAt(
                                        selectedRow,
                                        4
                                ))
                        .doubleValue();


        int stock =
                ((Number)
                        medicineTable
                                .getValueAt(
                                        selectedRow,
                                        5
                                ))
                        .intValue();


        // Check stock

        if (quantity > stock) {

            JOptionPane.showMessageDialog(
                    this,
                    "Not enough stock available.\n\n"
                            + "Available stock: "
                            + stock,
                    "Insufficient Stock",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // Check whether item already exists
        // in the cart

        for (
                int i = 0;
                i < cartTableModel.getRowCount();
                i++
        ) {

            int existingId =
                    ((Number)
                            cartTableModel
                                    .getValueAt(
                                            i,
                                            0
                                    ))
                            .intValue();


            if (existingId == medicineId) {

                int existingQuantity =
                        ((Number)
                                cartTableModel
                                        .getValueAt(
                                                i,
                                                3
                                        ))
                                .intValue();


                int newQuantity =
                        existingQuantity + quantity;


                if (newQuantity > stock) {

                    JOptionPane.showMessageDialog(
                            this,
                            "The cart quantity cannot "
                                    + "exceed available stock.\n\n"
                                    + "Available stock: "
                                    + stock,
                            "Insufficient Stock",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }


                double newTotal =
                        price * newQuantity;


                cartTableModel.setValueAt(
                        newQuantity,
                        i,
                        3
                );


                cartTableModel.setValueAt(
                        newTotal,
                        i,
                        4
                );


                updateTotals();

                quantityField.setText("1");

                return;
            }
        }


        // Add a NEW item

        double itemTotal =
                price * quantity;


        cartTableModel.addRow(
                new Object[]{
                        medicineId,
                        medicineName,
                        price,
                        quantity,
                        itemTotal
                }
        );


        // Format price columns

        cartTable.getColumnModel()
                .getColumn(2)
                .setCellRenderer(
                        new PriceRenderer()
                );

        cartTable.getColumnModel()
                .getColumn(4)
                .setCellRenderer(
                        new PriceRenderer()
                );


        updateTotals();


        // Reset quantity

        quantityField.setText("1");
    }


    // =====================================================
    // REMOVE FROM CART
    // =====================================================

    private void removeFromCart() {

        int selectedRow =
                cartTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an item to remove.",
                    "No Item Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        cartTableModel.removeRow(
                selectedRow
        );


        updateTotals();
    }


    // =====================================================
    // CLEAR CART
    // =====================================================

    private void clearCart() {

        if (
                cartTableModel.getRowCount()
                        == 0
        ) {

            return;
        }


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want "
                                + "to clear the cart?",
                        "Clear Cart",
                        JOptionPane.YES_NO_OPTION
                );


        if (
                result
                        == JOptionPane.YES_OPTION
        ) {

            cartTableModel.setRowCount(0);

            updateTotals();
        }
    }


    // =====================================================
    // UPDATE TOTALS
    // =====================================================

    private void updateTotals() {

        double subtotal = 0;


        for (
                int i = 0;
                i < cartTableModel.getRowCount();
                i++
        ) {

            double itemTotal =
                    ((Number)
                            cartTableModel
                                    .getValueAt(
                                            i,
                                            4
                                    ))
                            .doubleValue();


            subtotal += itemTotal;
        }


        double vat =
                subtotal * VAT_RATE;


        double total =
                subtotal + vat;


        subtotalLabel.setText(
                moneyFormat.format(subtotal)
        );


        vatLabel.setText(
                moneyFormat.format(vat)
        );


        totalLabel.setText(
                moneyFormat.format(total)
        );
    }


    // =====================================================
    // VIEW BILL
    // =====================================================

    private void showBill() {

        if (
                cartTableModel.getRowCount()
                        == 0
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "The cart is empty.",
                    "No Items",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        StringBuilder bill =
                new StringBuilder();


        bill.append(
                "================================\n"
        );

        bill.append(
                "       HEALTHFIRST PHARMACY\n"
        );

        bill.append(
                "              BILL\n"
        );

        bill.append(
                "================================\n\n"
        );


        for (
                int i = 0;
                i < cartTableModel.getRowCount();
                i++
        ) {

            String name =
                    cartTableModel
                            .getValueAt(
                                    i,
                                    1
                            )
                            .toString();


            double price =
                    ((Number)
                            cartTableModel
                                    .getValueAt(
                                            i,
                                            2
                                    ))
                            .doubleValue();


            int quantity =
                    ((Number)
                            cartTableModel
                                    .getValueAt(
                                            i,
                                            3
                                    ))
                            .intValue();


            double total =
                    ((Number)
                            cartTableModel
                                    .getValueAt(
                                            i,
                                            4
                                    ))
                            .doubleValue();


            bill.append(name)
                    .append("\n");


            bill.append("  ")
                    .append(quantity)
                    .append(" x ")
                    .append(
                            moneyFormat.format(
                                    price
                            )
                    )
                    .append(" = ")
                    .append(
                            moneyFormat.format(
                                    total
                            )
                    )
                    .append("\n\n");
        }


        bill.append(
                "--------------------------------\n"
        );


        bill.append(
                        "Subtotal: "
                )
                .append(
                        subtotalLabel.getText()
                )
                .append("\n");


        bill.append(
                        "VAT:      "
                )
                .append(
                        vatLabel.getText()
                )
                .append("\n");


        bill.append(
                        "TOTAL:    "
                )
                .append(
                        totalLabel.getText()
                )
                .append("\n");


        bill.append(
                "================================\n"
        );


        JTextArea billArea =
                new JTextArea(
                        bill.toString()
                );


        billArea.setEditable(false);

        billArea.setFont(
                new Font(
                        Font.MONOSPACED,
                        Font.PLAIN,
                        14
                )
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        billArea
                );

        scrollPane.setPreferredSize(
                new Dimension(
                        450,
                        400
                )
        );


        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Bill",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // =====================================================
    // CHECKOUT
    // =====================================================

    private void checkout() {

        // IMPORTANT:
        // Check the actual cart, not the medicine table.

        if (
                cartTableModel.getRowCount()
                        == 0
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "The cart is empty.",
                    "Cannot Checkout",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        double total =
                0;


        for (
                int i = 0;
                i < cartTableModel.getRowCount();
                i++
        ) {

            total +=
                    ((Number)
                            cartTableModel
                                    .getValueAt(
                                            i,
                                            4
                                    ))
                            .doubleValue();
        }


        double vat =
                total * VAT_RATE;


        double finalTotal =
                total + vat;


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Complete this sale?\n\n"
                                + "Subtotal: "
                                + moneyFormat.format(total)
                                + "\n"
                                + "VAT: "
                                + moneyFormat.format(vat)
                                + "\n"
                                + "TOTAL: "
                                + moneyFormat.format(finalTotal),

                        "Checkout",

                        JOptionPane.YES_NO_OPTION
                );


        if (
                result
                        == JOptionPane.YES_OPTION
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Sale completed successfully!\n\n"
                            + "Total: "
                            + moneyFormat.format(
                            finalTotal
                    )
                            + "\n\n"
                            + "Simulation only - "
                            + "no sale was saved.",
                    "Checkout Complete",
                    JOptionPane.INFORMATION_MESSAGE
            );


            // Clear cart

            cartTableModel.setRowCount(0);

            updateTotals();
        }
    }


    // =====================================================
    // PRICE RENDERER
    // =====================================================

    private static class PriceRenderer
            extends DefaultTableCellRenderer implements TableCellRenderer {

        public PriceRenderer() {

            setHorizontalAlignment(
                    SwingConstants.RIGHT
            );
        }


        @Override
        public void setValue(
                Object value
        ) {

            if (value instanceof Number) {

                setText(
                        new DecimalFormat(
                                "R #,##0.00"
                        ).format(value)
                );

            } else {

                setText(
                        value == null
                                ? ""
                                : value.toString()
                );
            }
        }
    }
}
