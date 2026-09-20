package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CartPanel extends JPanel {

    private JTable cartTable;
    private DefaultTableModel cartTableModel;

    private TotalsPanel totalsPanel;
    //cart panel
    public CartPanel(TotalsPanel totalsPanel) {
        this.totalsPanel = totalsPanel;

        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createTitledBorder("Current Sale"));

        // Cart buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton removeButton = new JButton("Remove Item");
        JButton clearButton = new JButton("Clear Cart");

        buttonPanel.add(removeButton);
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.NORTH);

        // Cart table
        String[] columns = {
                "ID", "Medicine", "Price", "Qty", "Total"
        };

        cartTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        cartTable = new JTable(cartTableModel);
        cartTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cartTable.setRowHeight(28);

        add(new JScrollPane(cartTable), BorderLayout.CENTER);

        // Button actions
        removeButton.addActionListener(e -> removeItem());
        clearButton.addActionListener(e -> clearCart());
        // Totals
        add(totalsPanel, BorderLayout.SOUTH);
    }

    // Add medicine to cart
    public void addMedicine(int id, String name, double price, int quantity) {
        // Check if medicine is already in cart
        for (int i = 0; i < cartTableModel.getRowCount(); i++) {
            int existingId = ((Number) cartTableModel.getValueAt(i, 0)).intValue();

            if (existingId == id) {
                int oldQuantity = ((Number) cartTableModel.getValueAt(i, 3)).intValue();
                int newQuantity = oldQuantity + quantity;

                cartTableModel.setValueAt(newQuantity, i, 3);
                cartTableModel.setValueAt(price * newQuantity, i, 4);

                updateTotals();
                return;
            }
        }

        // Add new item
        cartTableModel.addRow(new Object[]{
                id,
                name,
                price,
                quantity,
                price * quantity
        });
        updateTotals();
    }

    // Remove selected item
    private void removeItem() {
        int selectedRow = cartTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an item to remove.",
                    "No Item Selected",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        cartTableModel.removeRow(selectedRow);
        updateTotals();
    }

    // Clear cart
    private void clearCart() {
        if (cartTableModel.getRowCount() == 0)
            return;

        int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to clear the cart?",
                "Clear Cart",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {
            cartTableModel.setRowCount(0);
            updateTotals();
        }
    }

    // Calculate totals
    private void updateTotals() {

        double subtotal = 0;

        for (int i = 0; i < cartTableModel.getRowCount(); i++) {
            subtotal += ((Number)
                    cartTableModel.getValueAt(i, 4)).doubleValue();
        }
        totalsPanel.updateTotals(subtotal);
    }

    //give other classes access to the cart table
    public DefaultTableModel getCartTableModel() {
        return cartTableModel;
    }
}