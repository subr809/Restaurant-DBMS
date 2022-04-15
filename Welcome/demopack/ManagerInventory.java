package demopack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class ManagerInventory {
    GridBagConstraints cons;
    private JPanel quantityPanel;
    private JPanel unitPricePanel;
    private JPanel foodItemPanel;
    private JPanel menuIDPanel;
    public JPanel inventoryPanel;
    private JButton backButton;
    private JTextArea menuIDField;
    private JTextArea quantityField;
    private JButton confirmChangesButton;
    ArrayList<String> menu_id = new ArrayList<String>();
    ArrayList<String> food_name = new ArrayList<String>();
    ArrayList<Float> unit_price = new ArrayList<Float>();
    ArrayList<Integer> quantity = new ArrayList<Integer>();

    public void formatContainer() {
        unitPricePanel.setLayout(new GridBagLayout()); // panel that holds buttons
        foodItemPanel.setLayout(new GridBagLayout());
        quantityPanel.setLayout(new GridBagLayout());
        menuIDPanel.setLayout(new GridBagLayout());
        cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.weightx = 1;
        cons.gridx = 0;
        unitPricePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        foodItemPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        quantityPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuIDPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public ManagerInventory() {
        Connection conn = master.getConnection();
        connect(conn, menu_id, food_name, unit_price, quantity);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showConfirmDialog(null, "welcome page");
                master.managerFrame.setVisible(true);
                master.inventoryFrame.setVisible(false);
            }
        });
        formatContainer();
        for (int i = 0; i < menu_id.size(); ++i) {
            JLabel menuID = new JLabel(menu_id.get(i));
            menuID.setFont(new Font("DialogInput", Font.PLAIN, 28));
            menuID.setHorizontalAlignment(SwingConstants.CENTER);
            menuID.setVerticalAlignment(SwingConstants.TOP);

            JLabel foodName = new JLabel(food_name.get(i));
            foodName.setFont(new Font("DialogInput", Font.PLAIN, 28));
            foodName.setHorizontalAlignment(SwingConstants.CENTER);
            foodName.setVerticalAlignment(SwingConstants.TOP);

            JLabel unitPrice = new JLabel(String.valueOf(unit_price.get(i)));
            unitPrice.setFont(new Font("DialogInput", Font.PLAIN, 28));
            unitPrice.setHorizontalAlignment(SwingConstants.CENTER);
            unitPrice.setVerticalAlignment(SwingConstants.TOP);

            JLabel quantityNum = new JLabel(String.valueOf(quantity.get(i)));
            quantityNum.setFont(new Font("DialogInput", Font.PLAIN, 28));
            quantityNum.setHorizontalAlignment(SwingConstants.CENTER);
            quantityNum.setVerticalAlignment(SwingConstants.TOP);

            menuIDPanel.add(menuID, cons);
            foodItemPanel.add(foodName, cons);
            unitPricePanel.add(unitPrice, cons);
            quantityPanel.add(quantityNum, cons);
        }
        menuIDField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                // enterMenuIDOrTextField.setText("Enter Menu ID or Food Item");
            }

            @Override
            public void focusGained(FocusEvent e) {
                menuIDField.setText("");
            }
        });
        quantityField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                // enterMenuIDOrTextField.setText("Enter Menu ID or Food Item");
            }

            @Override
            public void focusGained(FocusEvent e) {
                quantityField.setText("");
            }
        });
        Connection conn2 = getConnection();
        confirmChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String menu_ID = menuIDField.getText();

                String quantity = quantityField.getText();

                int quantity_i = Integer.parseInt(quantity);

                price_adjustment_connection(conn2, quantity_i, menu_ID);
            }
        });
    }

    public void connect(Connection conn, ArrayList<String> menu_id, ArrayList<String> food_name, ArrayList<Float> unit_price,
                        ArrayList<Integer> quantity) {
        try {
            //create a statement object
            Statement stmt = conn.createStatement();
            //create an SQL statement
            String sqlStatement = "Select menu_id, food_name, unit_price, quantity FROM inventory";
            //send statement to DBMS
            PreparedStatement ps = conn.prepareStatement(sqlStatement);
            ResultSet result = ps.executeQuery();

            //OUTPUT
            while (result.next()) {
                menu_id.add(result.getString("menu_id"));
                food_name.add(result.getString("food_name"));
                unit_price.add(result.getFloat("unit_price"));
                quantity.add(result.getInt("quantity"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Inventory Error accessing Database. ");
        }
        try {
            conn.close();
            //         JOptionPane.showMessageDialog(null, "Connection Closed.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connection NOT Closed.");
        }//end try catch
    }

    public void price_adjustment_connection(Connection conn, int quantity, String select_menu_id) {
        try {
            //create a statement object

            Statement stmt = conn.createStatement();

            //create an SQL statement
            String sqlStatement = "UPDATE inventory SET quantity='"+quantity+"' WHERE menu_id = '"+select_menu_id+"'";
            //send statement to DBMS
            //stmt.executeQuery(sqlStatement);
            stmt.executeUpdate(sqlStatement);
            //OUTPUT
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Inventory Change Accessing Database.");
        }
        try {
            conn.close();
            //         JOptionPane.showMessageDialog(null, "Connection Closed.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connection NOT Closed.");
        }//end try catch
    }

    public static Connection getConnection() {
        dbSetup my = new dbSetup();
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/db901_group11_project2",
                    my.user, my.pswd);
        }   catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }//end try catch
        return conn;
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Manager Inventory");
        frame.setContentPane(new ManagerInventory().inventoryPanel);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
