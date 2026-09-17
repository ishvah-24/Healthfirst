import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class Admin extends JPanel{
    private Main mainFrame;


    public Admin(Main mainFrame){
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        JLabel admin = new JLabel("Admin dashboard");

        this.add(admin);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

        tabbedPane.setUI(new BasicTabbedPaneUI() {
             @Override
             protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
                 return 75;
             }

             @Override
             protected int calculateTabHeight(int tabPlacement,
                                              int tabIndex,
                                              int fontHeight) {
                 return 113; // height of each tab
             }
         });


        JPanel manageMedicines = new JPanel();
        JLabel manage_medicine = new JLabel("Manage Medicine");
        manageMedicines.add(manage_medicine);
        manageMedicines.setPreferredSize(new Dimension(1000, 700));

        JPanel manageSuppliers = new JPanel();
        JLabel manage_suppliers = new JLabel("Manage Suppliers");
        manageSuppliers.add(manage_suppliers);

        JPanel manageUsers = new JPanel();
        JLabel manage_users = new JLabel("Manage Users");
        manageUsers.add(manage_users);

        tabbedPane.addTab("Medicines" ,manageMedicines);
        tabbedPane.addTab("Suppliers" ,manageSuppliers);
        tabbedPane.addTab("Users" ,manageUsers);

        tabbedPane.setPreferredSize(new Dimension(1000, 700));


        //JLabel welcomeMessage = new JLabel("Welcome Back " + user.getFull_name());
        //add(welcomeMessage);
        this.add(tabbedPane);
    }
}
