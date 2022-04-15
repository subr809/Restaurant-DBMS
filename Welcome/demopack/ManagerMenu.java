package demopack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;
import java.util.ArrayList;

public class ManagerMenu {
    GridBagConstraints cons;
    private JTextField enterMenuIDOrTextField;
    private JButton confirmChangesButton;
    private JTextField enterPriceChangeTextField;
    private JPanel menuIDPanel;
    private JPanel foodItemPanel;
    private JPanel pricePanel;
    public JPanel managerMenuPanel;
    private JButton backButton;
    private JButton addButton;
    private JTextArea textArea1;
    private JButton addChangeButton;
    ArrayList<String> menu_id = new ArrayList<String>();
    ArrayList<String> food_name = new ArrayList<String>();
    ArrayList<Float> prices = new ArrayList<Float>();

    public void formatContainer() {
        pricePanel.setLayout(new GridBagLayout()); // panel that holds buttons
        foodItemPanel.setLayout(new GridBagLayout());
        menuIDPanel.setLayout(new GridBagLayout());
        menuIDPanel.setLayout(new GridBagLayout());
        cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.weightx = 1;
        cons.gridx = 0;
        pricePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        foodItemPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuIDPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public ManagerMenu() {
        Connection conn = master.getConnection();
        connect(conn, menu_id, food_name, prices);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showConfirmDialog(null, "welcome page");
                master.managerFrame.setVisible(true);
                master.managerMenuFrame.setVisible(false);
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

            JLabel price = new JLabel(String.valueOf(prices.get(i)));
            price.setFont(new Font("DialogInput", Font.PLAIN, 28));
            price.setHorizontalAlignment(SwingConstants.CENTER);
            price.setVerticalAlignment(SwingConstants.TOP);

            menuIDPanel.add(menuID, cons);
            foodItemPanel.add(foodName, cons);
            pricePanel.add(price, cons);
        }
        enterMenuIDOrTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
               // enterMenuIDOrTextField.setText("Enter Menu ID or Food Item");
            }

            @Override
            public void focusGained(FocusEvent e) {
                enterMenuIDOrTextField.setText("");
            }
        });
        enterPriceChangeTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                //enterPriceChangeTextField.setText("Enter Price Change");
            }
            @Override
            public void focusGained(FocusEvent e) {
                enterPriceChangeTextField.setText("");
            }
        });

        confirmChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String menu_ID = enterMenuIDOrTextField.getText();
                String price_change = enterPriceChangeTextField.getText();
                Float new_price = Float.parseFloat(price_change);
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String menu_ID = enterMenuIDOrTextField.getText();
                String price_change = enterPriceChangeTextField.getText();
                Float new_price = Float.parseFloat(price_change);
                textArea1.setText(menu_ID + " change to " + price_change);
            }
        });
        Connection conn2 = getConnection();
        confirmChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String menu_ID = enterMenuIDOrTextField.getText();

                System.out.println("Menu ID: " + menu_ID);
                String price_change = enterPriceChangeTextField.getText();

                System.out.println("Price change: " + price_change);
                //Float new_price = Float.parseFloat(price_change);

               // System.out.println("New price: " + new_price);
                price_adjustment_connection(conn2, price_change, menu_ID);
            }
        });
    }

    public void connect(Connection conn, ArrayList<String> menu, ArrayList<String> food, ArrayList<Float> prices) {
        try {
            //create a statement object
            Statement stmt = conn.createStatement();
            //create an SQL statement
            String sqlStatement = "Select menu_id, food_name, price FROM menu";
            //send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);

            //OUTPUT
            while (result.next()) {
                menu.add(result.getString("menu_id"));
                food.add(result.getString("food_name"));
                prices.add(result.getFloat("price"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Menu Connect Error accessing Database.");
        }
        try {
            conn.close();
            //         JOptionPane.showMessageDialog(null, "Connection Closed.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connection NOT Closed.");
        }//end try catch
    }

    public void price_adjustment_connection(Connection conn, String new_price, String select_menu_id) {
        try {
            //create a statement object

            Statement stmt = conn.createStatement();

            //create an SQL statement
            String sqlStatement = "UPDATE menu SET price='"+new_price+"' WHERE menu_id = '"+select_menu_id+"'";
            //PreparedStatement price_updateSQL = conn.prepareStatement(sqlStatement);
            //send statement to DBMS
            stmt.executeUpdate(sqlStatement);
            //ResultSet RS = price_updateSQL.executeQuery();
            //RS.next();
            //OUTPUT
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Menu Price Error accessing Database.");
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
        JFrame frame = new JFrame("ManagerMenu");
        frame.setContentPane(new ManagerMenu().managerMenuPanel);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
