package gui;

import javax.swing.*;
import java.awt.*;

public class Cashier extends JPanel {

    private Main mainFrame;

    private MedicinePanel medicinePanel;
    private CartPanel cartPanel;
    private TotalsPanel totalsPanel;

    // Build cashier screen
    public Cashier(Main mainFrame) {

        this.mainFrame = mainFrame;

        setLayout(new BorderLayout(10, 10));
        setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );

        setBackground(Color.WHITE);

        // Create POS sections
        totalsPanel = new TotalsPanel();
        cartPanel = new CartPanel(totalsPanel);
        medicinePanel = new MedicinePanel(cartPanel);

        // Top section
        createTopPanel();

        // Center section
        JPanel centerPanel =
                new JPanel(new GridLayout(1, 2, 10, 0));

        centerPanel.add(medicinePanel);
        centerPanel.add(cartPanel);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom buttons
        createBottomPanel();
    }

    // Create title and logout button
    private void createTopPanel() {

        JPanel topPanel =
                new JPanel(new BorderLayout());

        JLabel title =
                new JLabel(
                        "HealthFirst Pharmacy - Point of Sale"
                );

        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        JButton logoutButton =
                new JButton("Logout");

        logoutButton.addActionListener(
                e -> mainFrame.showPanel("LOGIN")
        );

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
    }

    // Create bill and checkout buttons
    private void createBottomPanel() {

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

        billButton.addActionListener(e ->
                BillDialog.showBill(
                        this,
                        cartPanel.getCartTableModel(),
                        totalsPanel.getSubtotal(),
                        totalsPanel.getVat(),
                        totalsPanel.getTotal()
                )
        );

        checkoutButton.addActionListener(
                e -> checkout()
        );

        bottomPanel.add(billButton);
        bottomPanel.add(checkoutButton);

        add(
                bottomPanel,
                BorderLayout.SOUTH
        );
    }

    // Process checkout
    private void checkout() {

        if (cartPanel.getCartTableModel().getRowCount() == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "The cart is empty.",
                    "Cannot Checkout",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Complete this sale?\n\n"
                                + "Subtotal: "
                                + totalsPanel.getSubtotal()
                                + "\nVAT: "
                                + totalsPanel.getVat()
                                + "\nTOTAL: "
                                + totalsPanel.getTotal(),

                        "Checkout",
                        JOptionPane.YES_NO_OPTION
                );

        if (result == JOptionPane.YES_OPTION) {

            JOptionPane.showMessageDialog(
                    this,
                    "Sale completed successfully!\n\n"
                            + "Total: "
                            + totalsPanel.getTotal()
                            + "\n\nSimulation only - "
                            + "no sale was saved.",
                    "Checkout Complete",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Clear cart
            cartPanel.getCartTableModel().setRowCount(0);
            totalsPanel.updateTotals(0);
        }
    }
}