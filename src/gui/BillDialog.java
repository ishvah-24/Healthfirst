package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;

public class BillDialog {

    private static DecimalFormat moneyFormat =
            new DecimalFormat("R #,##0.00");

    // Display the current bill
    public static void showBill(
            Component parent,
            DefaultTableModel cartModel,
            String subtotal,
            String vat,
            String total) {

        if (cartModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                    parent,
                    "The cart is empty.",
                    "No Items",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        StringBuilder bill = new StringBuilder();

        // Bill heading
        bill.append("================================\n")
                .append("       HEALTHFIRST PHARMACY\n")
                .append("              BILL\n")
                .append("================================\n\n");

        // Add cart items
        for (int i = 0; i < cartModel.getRowCount(); i++) {
            String name = cartModel.getValueAt(i, 1).toString();
            double price =
                    ((Number) cartModel.getValueAt(i, 2)).doubleValue();
            int quantity =
                    ((Number) cartModel.getValueAt(i, 3)).intValue();
            double itemTotal =
                    ((Number) cartModel.getValueAt(i, 4)).doubleValue();

            bill.append(name).append("\n")
                    .append("  ").append(quantity)
                    .append(" x ").append(moneyFormat.format(price))
                    .append(" = ").append(moneyFormat.format(itemTotal))
                    .append("\n\n");
        }

        // Add totals
        bill.append("--------------------------------\n")
                .append("Subtotal: ").append(subtotal).append("\n")
                .append("VAT:      ").append(vat).append("\n")
                .append("TOTAL:    ").append(total).append("\n")
                .append("================================\n");

        // Display bill
        JTextArea billArea = new JTextArea(bill.toString());
        billArea.setEditable(false);
        billArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(billArea);
        scrollPane.setPreferredSize(new Dimension(450, 400));

        JOptionPane.showMessageDialog(
                parent, scrollPane, "Bill",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}