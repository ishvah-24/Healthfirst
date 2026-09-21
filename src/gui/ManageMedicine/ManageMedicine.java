package gui.ManageMedicine;

import model.Medicine;
import model.Supplier;
import service.MedicineService;
import service.SupplierService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.List;

public class ManageMedicine extends JPanel {
    // Stores the medicine table and its data
    private JTable medicineTable;
    private DefaultTableModel tableModel;

    // Stores the input fields for medicine details
    private JTextField nameField,companyField,typeField,priceField,quantityField,reorderLevelField,expiryDateField;

    // Stores the supplier dropdown
    private JComboBox<SupplierItem> supplierComboBox;

    // Services used to manage medicines and suppliers
    private MedicineService medicineService;
    private SupplierService supplierService;

    // Creates the medicine management panel
    public ManageMedicine() {
        medicineService=new MedicineService();
        supplierService=new SupplierService();

        // Sets the main layout and border
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        // Creates the page title
        JLabel title=new JLabel("Manage Medicines");
        title.setFont(new Font("Arial",Font.BOLD,24));
        add(title,BorderLayout.NORTH);

        // Creates the medicine table
        String[] columns={"ID","Name","Company","Type","Price","Stock","Reorder Level","Expiry Date","Supplier"};
        tableModel=new DefaultTableModel(columns,0){};
        medicineTable=new JTable(tableModel);
        medicineTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        medicineTable.setRowHeight(30);
        medicineTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        JScrollPane scrollPane=new JScrollPane(medicineTable);
        add(scrollPane,BorderLayout.CENTER);

        // Creates the form used to enter medicine information
        JPanel formPanel=new JPanel(new GridBagLayout());
        GridBagConstraints grid_bag_constraints=new GridBagConstraints();
        grid_bag_constraints.insets=new Insets(5,5,5,5);
        grid_bag_constraints.fill=GridBagConstraints.HORIZONTAL;

        // Adds the medicine name field
        grid_bag_constraints.gridx=0;
        grid_bag_constraints.gridy=0;
        formPanel.add(new JLabel("Medicine Name:"),grid_bag_constraints);
        nameField=new JTextField(18);
        grid_bag_constraints.gridx=1;
        formPanel.add(nameField,grid_bag_constraints);

        //adds the company field
        grid_bag_constraints.gridx=0;
        grid_bag_constraints.gridy=1;
        formPanel.add(new JLabel("Company:"),grid_bag_constraints);
        companyField=new JTextField(18);
        grid_bag_constraints.gridx=1;
        formPanel.add(companyField,grid_bag_constraints);

        //adds the medicine type field
        grid_bag_constraints.gridx=0;
        grid_bag_constraints.gridy=2;
        formPanel.add(new JLabel("Medicine Type:"),grid_bag_constraints);
        typeField=new JTextField(18);
        grid_bag_constraints.gridx=1;
        formPanel.add(typeField,grid_bag_constraints);

        //adds the price field
        grid_bag_constraints.gridx=0;
        grid_bag_constraints.gridy=3;
        formPanel.add(new JLabel("Price:"),grid_bag_constraints);
        priceField=new JTextField(18);
        grid_bag_constraints.gridx=1;
        formPanel.add(priceField,grid_bag_constraints);

        //adds the quantity field
        grid_bag_constraints.gridx=0;
        grid_bag_constraints.gridy=4;
        formPanel.add(new JLabel("Quantity in Stock:"),grid_bag_constraints);
        quantityField=new JTextField(18);
        grid_bag_constraints.gridx=1;
        formPanel.add(quantityField,grid_bag_constraints);

        //adds the reorder level field
        grid_bag_constraints.gridx=0;
        grid_bag_constraints.gridy=5;
        formPanel.add(new JLabel("Reorder Level:"),grid_bag_constraints);
        reorderLevelField=new JTextField(18);
        grid_bag_constraints.gridx=1;
        formPanel.add(reorderLevelField,grid_bag_constraints);

        //adds the expiry date field
        grid_bag_constraints.gridx=0;
        grid_bag_constraints.gridy=6;
        formPanel.add(new JLabel("Expiry Date:"),grid_bag_constraints);
        expiryDateField=new JTextField(18);
        expiryDateField.setToolTipText("Format: YYYY-MM-DD");
        grid_bag_constraints.gridx=1;
        formPanel.add(expiryDateField,grid_bag_constraints);

        // Adds the supplier dropdown
        grid_bag_constraints.gridx=0;
        grid_bag_constraints.gridy=7;
        formPanel.add(new JLabel("Supplier:"),grid_bag_constraints);
        supplierComboBox=new JComboBox<>();
        grid_bag_constraints.gridx=1;
        formPanel.add(supplierComboBox,grid_bag_constraints);

        // creates the buttons used to manage medicines
        // Creates the buttons used to manage medicines
        JPanel buttonPanel=new JPanel(new GridLayout(2,2,10,10));

        JButton addButton=new JButton("Add Medicine");
        JButton updateButton=new JButton("Update");
        JButton deleteButton=new JButton("Delete");
        JButton clearButton=new JButton("Clear");

        Dimension buttonSize=new Dimension(140,50);
        addButton.setPreferredSize(buttonSize);
        updateButton.setPreferredSize(buttonSize);
        deleteButton.setPreferredSize(buttonSize);
        clearButton.setPreferredSize(buttonSize);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

// Places the form and buttons next to each other
        JPanel bottomPanel=new JPanel(new FlowLayout(FlowLayout.LEFT,20,10));
        bottomPanel.add(formPanel);
        bottomPanel.add(buttonPanel);
        add(bottomPanel,BorderLayout.SOUTH);

        // connects each button to its method
        addButton.addActionListener(e->addMedicine());
        updateButton.addActionListener(e->updateMedicine());
        deleteButton.addActionListener(e->deleteMedicine());
        clearButton.addActionListener(e->clearForm());

        // Loads the selected medicine into the form
        medicineTable.getSelectionModel().addListSelectionListener(e->{
            if(!e.getValueIsAdjusting()) loadSelectedMedicine();
        });

        // Loads suppliers and medicines when the panel starts
        loadSuppliers();
        loadMedicines();
    }

    // Loads all suppliers into the dropdown
    private void loadSuppliers() {
        supplierComboBox.removeAllItems();
        List<Supplier> suppliers=supplierService.getAllSuppliers();
        for(Supplier supplier:suppliers) {
            supplierComboBox.addItem(
                    new SupplierItem(supplier.getSupplier_id(), supplier.getName()));
        }
    }

    //loads all medicines into the table
    private void loadMedicines() {
        tableModel.setRowCount(0);

        //the medicines are stored using a list
        List<Medicine> medicines=medicineService.getAllMedicines();

        //each medicine is displayed in a new row
        for(Medicine medicine:medicines) {
            String supplierName=getSupplierName(medicine.getSupplier_id());
            tableModel.addRow(new Object[]{
                    medicine.getMedicine_id(),
                    medicine.getName(),
                    medicine.getCompany(),
                    medicine.getMedicine_type(),
                    medicine.getPrice(),
                    medicine.getQuantity_in_stock(),
                    medicine.getReorder_level(),
                    medicine.getExpiry_date(),
                    supplierName});
        }
    }

    //finds the supplier name using its ID
    private String getSupplierName(int supplierId) {
        for(int i=0;i<supplierComboBox.getItemCount();i++) {
            SupplierItem supplier=supplierComboBox.getItemAt(i);
            if(supplier.getId()==supplierId) {
                return supplier.getName();
            };
        }
        return "Unknown";
    }

    //adds a new medicine to the database
    private void addMedicine() {
        try {
            String name=nameField.getText().trim();
            String company=companyField.getText().trim();
            String type=typeField.getText().trim();
            double price=Double.parseDouble(priceField.getText().trim());
            int quantity=Integer.parseInt(quantityField.getText().trim());
            int reorderLevel=Integer.parseInt(reorderLevelField.getText().trim());
            Date expiryDate=Date.valueOf(expiryDateField.getText().trim());
            SupplierItem supplier=(SupplierItem)supplierComboBox.getSelectedItem();

            // Checks that required fields contain information
            if(name.isEmpty()||company.isEmpty()||type.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please complete all fields.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Checks that a supplier was selected
            if(supplier==null) {
                JOptionPane.showMessageDialog(this,
                        "Please select a supplier.",
                        "Missing Supplier",JOptionPane.WARNING_MESSAGE);
                return;
            }

            //prevents negative values
            if(price<0||quantity<0||reorderLevel<0) {
                JOptionPane.showMessageDialog(this,
                        "Price, quantity and reorder level cannot be negative.",
                        "Invalid Information",JOptionPane.WARNING_MESSAGE);
                return;
            }

            //sends the medicine information to the service
            boolean success=medicineService.addMedicine(name,company,type,price,quantity,reorderLevel,expiryDate,supplier.getId());

            //updates the table after adding the medicine
            if(success) {
                JOptionPane.showMessageDialog(this,"Medicine added successfully.");
                clearForm();
                loadMedicines();
            } else {
                JOptionPane.showMessageDialog(this,"Could not add medicine.","Error",JOptionPane.ERROR_MESSAGE);
            }
        } catch(NumberFormatException e) {
            // Handles invalid number input
            JOptionPane.showMessageDialog(this,
                    "Price, quantity and reorder level must contain valid numbers.",
                    "Invalid Number",JOptionPane.WARNING_MESSAGE);
        } catch(IllegalArgumentException e) {
            // Handles invalid date input
            JOptionPane.showMessageDialog(this,
                    "Expiry date must use the format YYYY-MM-DD.",
                    "Invalid Date",JOptionPane.WARNING_MESSAGE);
        }
    }

    // Updates the selected medicine
    private void updateMedicine() {
        int selectedRow=medicineTable.getSelectedRow();

        // Makes sure a medicine has been selected
        if(selectedRow==-1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a medicine first.",
                    "No Selection",JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int medicineId=(int)tableModel.getValueAt(selectedRow,0);
            String name=nameField.getText().trim();
            String company=companyField.getText().trim();
            String type=typeField.getText().trim();
            double price=Double.parseDouble(priceField.getText().trim());
            int quantity=Integer.parseInt(quantityField.getText().trim());
            int reorderLevel=Integer.parseInt(reorderLevelField.getText().trim());
            Date expiryDate=Date.valueOf(expiryDateField.getText().trim());
            SupplierItem supplier=(SupplierItem)supplierComboBox.getSelectedItem();

            // Checks that required fields contain information
            if(name.isEmpty()||company.isEmpty()||type.isEmpty()) {
                JOptionPane.showMessageDialog(this,"Please complete all fields.","Missing Information",JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Checks that a supplier was selected
            if(supplier==null) {
                JOptionPane.showMessageDialog(this,"Please select a supplier.",
                        "Missing Supplier",JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Prevents negative values
            if(price<0||quantity<0||reorderLevel<0) {
                JOptionPane.showMessageDialog(this,
                        "Price, quantity and reorder level cannot be negative.",
                        "Invalid Information",JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Sends the updated information to the service
            boolean success=medicineService.updateMedicine(
                    medicineId,name, company,
                    type, price,quantity,
                    reorderLevel, expiryDate,supplier.getId());

            // Refreshes the table after updating
            if(success) {
                JOptionPane.showMessageDialog(this,
                        "Medicine updated successfully.");
                clearForm();
                loadMedicines();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Could not update medicine.","Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch(NumberFormatException e) {
            // Handles invalid number input
            JOptionPane.showMessageDialog(this,
                    "Price, quantity and reorder level must contain valid numbers.","Invalid Number",
                    JOptionPane.WARNING_MESSAGE);
        } catch(IllegalArgumentException e) {
            // Handles invalid date input
            JOptionPane.showMessageDialog(this,
                    "Expiry date must use the format YYYY-MM-DD.",
                    "Invalid Date",JOptionPane.WARNING_MESSAGE);
        }
    }

    // Deletes the selected medicine
    private void deleteMedicine() {
        int selectedRow=medicineTable.getSelectedRow();

        // Makes sure a medicine has been selected
        if(selectedRow==-1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a medicine first.",
                    "No Selection",JOptionPane.WARNING_MESSAGE);
            return;
        }

        int medicineId=(int)tableModel.getValueAt(selectedRow,0);
        String medicineName=tableModel.getValueAt(selectedRow,1).toString();

        // Asks the user to confirm the deletion
        int confirmation=JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete "+medicineName+"?"
                ,"Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if(confirmation!=JOptionPane.YES_OPTION) return;

        boolean success=medicineService.deleteMedicine(medicineId);

        // Refreshes the table after deletion
        if(success) {
            JOptionPane.showMessageDialog(this,"Medicine deleted successfully.");
            clearForm();
            loadMedicines();
        } else {
            JOptionPane.showMessageDialog(this,"Could not delete medicine.","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    // Loads the selected table row into the form
    private void loadSelectedMedicine() {
        int selectedRow=medicineTable.getSelectedRow();
        if(selectedRow==-1) return;

        nameField.setText(tableModel.getValueAt(selectedRow,1).toString());
        companyField.setText(tableModel.getValueAt(selectedRow,2).toString());
        typeField.setText(tableModel.getValueAt(selectedRow,3).toString());
        priceField.setText(tableModel.getValueAt(selectedRow,4).toString());
        quantityField.setText(tableModel.getValueAt(selectedRow,5).toString());
        reorderLevelField.setText(tableModel.getValueAt(selectedRow,6).toString());
        expiryDateField.setText(tableModel.getValueAt(selectedRow,7).toString());

        // Finds the selected medicine and sets its supplier
        int medicineId=(int)tableModel.getValueAt(selectedRow,0);
        Medicine medicine=medicineService.getMedicineById(medicineId);
        if(medicine!=null) selectSupplier(medicine.getSupplier_id());
    }

    // Selects the supplier that belongs to the medicine
    private void selectSupplier(int supplierId) {
        for(int i=0;i<supplierComboBox.getItemCount();i++) {
            SupplierItem supplier=supplierComboBox.getItemAt(i);
            if(supplier.getId()==supplierId) {
                supplierComboBox.setSelectedIndex(i);
                return;
            }
        }
    }

    // Clears all form fields
    private void clearForm() {
        nameField.setText("");
        companyField.setText("");
        typeField.setText("");
        priceField.setText("");
        quantityField.setText("");
        reorderLevelField.setText("");
        expiryDateField.setText("");

        // Resets the supplier dropdown
        if(supplierComboBox.getItemCount()>0) supplierComboBox.setSelectedIndex(0);
        medicineTable.clearSelection();
    }

    // Stores the supplier ID and name for the dropdown
    private static class SupplierItem {
        private int id;
        private String name;

        public SupplierItem(int id,String name) {
            this.id=id;
            this.name=name;
        }

        // Returns the supplier ID
        public int getId() { return id; }

        // Returns the supplier name
        public String getName() { return name; }

        // Displays the supplier name in the dropdown
        @Override
        public String toString() { return name; }
    }
}