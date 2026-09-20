package gui;

import service.ReportService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Reports extends JPanel {

    private ReportService reportService;

    private JTable salesTable;
    private JTable expiryTable;

    private DefaultTableModel salesModel;
    private DefaultTableModel expiryModel;

    public Reports() {
        reportService = new ReportService();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Reports");
        title.setFont(new Font("Arial", Font.BOLD, 24));

        add(title, BorderLayout.NORTH);

        // TABS
        JTabbedPane tabs = new JTabbedPane();

        // SALES REPORT
        JPanel salesPanel = new JPanel(new BorderLayout(10, 10));
        JButton loadSalesButton = new JButton("Load Sales Report");

        String[] salesColumns = {
                "Sale ID",
                "Sale Date",
                "Total Amount"
        };

        salesModel = new DefaultTableModel(salesColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        salesTable = new JTable(salesModel);
        salesTable.setRowHeight(30);

        salesPanel.add(new JScrollPane(salesTable), BorderLayout.CENTER);
        salesPanel.add(loadSalesButton, BorderLayout.SOUTH);

        loadSalesButton.addActionListener(e -> loadSalesReport());

        // EXPIRY REPORT
        JPanel expiryPanel = new JPanel(new BorderLayout(10, 10));
        JButton loadExpiryButton = new JButton("Load Expiry Report");

        String[] expiryColumns = {
                "Medicine ID",
                "Name",
                "Company",
                "Type",
                "Expiry Date",
                "Stock"
        };

        expiryModel = new DefaultTableModel(expiryColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        expiryTable = new JTable(expiryModel);
        expiryTable.setRowHeight(30);

        expiryPanel.add(new JScrollPane(expiryTable), BorderLayout.CENTER);
        expiryPanel.add(loadExpiryButton, BorderLayout.SOUTH);

        loadExpiryButton.addActionListener(e -> loadExpiryReport());

        // Add tabs
        tabs.addTab("Sales Report", salesPanel);
        tabs.addTab("Expiry Report", expiryPanel);

        add(tabs, BorderLayout.CENTER);
    }

    // LOAD SALES REPORT
    private void loadSalesReport() {
        salesModel.setRowCount(0);

        List<Object[]> sales = reportService.getSalesReport();

        for (Object[] sale : sales) {
            salesModel.addRow(sale);
        }
    }


// load the expiry report
    private void loadExpiryReport() {
        expiryModel.setRowCount(0);
        List<Object[]> medicines = reportService.getExpiringMedicines();

        for (Object[] medicine : medicines) {
            expiryModel.addRow(medicine);
        }
    }
}