package gui;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class TotalsPanel extends JPanel {

    private JLabel subtotalLabel;
    private JLabel vatLabel;
    private JLabel totalLabel;

    private DecimalFormat moneyFormat =
            new DecimalFormat("R #,##0.00");

    private static final double VAT_RATE = 0.15;

    // Build totals panel
    public TotalsPanel() {

        setLayout(new GridLayout(3, 2, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Subtotal
        add(new JLabel("Subtotal:"));

        subtotalLabel = new JLabel("R 0.00");
        subtotalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(subtotalLabel);

        // VAT
        add(new JLabel("VAT (15%):"));

        vatLabel = new JLabel("R 0.00");
        vatLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(vatLabel);

        // Total
        JLabel totalText = new JLabel("TOTAL:");
        totalText.setFont(new Font("Arial", Font.BOLD, 18));
        add(totalText);

        totalLabel = new JLabel("R 0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(totalLabel);
    }

    // Calculate and display totals
    public void updateTotals(double subtotal) {

        double vat = subtotal * VAT_RATE;
        double total = subtotal + vat;

        subtotalLabel.setText(moneyFormat.format(subtotal));
        vatLabel.setText(moneyFormat.format(vat));
        totalLabel.setText(moneyFormat.format(total));
    }

    // Get displayed subtotal
    public String getSubtotal() {
        return subtotalLabel.getText();
    }

    // Get displayed VAT
    public String getVat() {
        return vatLabel.getText();
    }

    // Get displayed total
    public String getTotal() {
        return totalLabel.getText();
    }
}