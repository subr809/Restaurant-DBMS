package demopack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ValueMenu {
    GridBagConstraints cons;
    private JLabel Intro;
    private JLabel Food_Item;
    private JLabel Price;
    private JLabel Quantity;
    private JPanel valueHolder;
    private JPanel priceHolder;
    private JPanel quantityHolder;
    public JPanel valuePanel;
    private JButton OKButton;
    ArrayList<JButton> food_buttons = new ArrayList<JButton>();
    ArrayList<String> food_names = new ArrayList<String>();
    ArrayList<Float> prices = new ArrayList<Float>();
    ArrayList<Counter> counters = new ArrayList<Counter>();
    ArrayList<String> value_ids = new ArrayList<String>();
    ArrayList<String> toppings = new ArrayList<String>();
    ArrayList<CharSequence> value_order = new ArrayList<CharSequence>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("ValueMenu");
        frame.setContentPane(new ValueMenu().valuePanel);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public class Counter {
        public int value = 0;
        public JButton incbtn = new JButton("+");
        public JButton decbtn = new JButton("-");
        private JLabel counterValueText;

        Counter() {
            counterValueText = new JLabel(String.valueOf(0));
            incbtn.addActionListener(new ActionListener() {
                int i;

                public void actionPerformed(ActionEvent e) {
                    i = value;
                    if (i <= 3) // max item count CHANGE TO CONSTANT
                        i++;
                    value = i;
                    counterValueText.setText(String.valueOf(i));
//                    i = 0;
                }
            });
            decbtn.addActionListener(new ActionListener() {
                int i;

                public void actionPerformed(ActionEvent e) {
                    i = value;
                    if (i > 0)
                        i--;
                    value = i;
                    counterValueText.setText(String.valueOf(i));
//                    i = 0;
                }
            });

            JPanel container = new JPanel(); // container for the counterValueText, incbtn, decbtn
            counterValueText.setBackground(Color.black);
            counterValueText.setAlignmentX(Component.CENTER_ALIGNMENT);
            decbtn.setAlignmentX(Component.LEFT_ALIGNMENT);
            incbtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
            container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
            container.setAlignmentX(Component.CENTER_ALIGNMENT);
            container.add(decbtn);
            container.add(counterValueText);
            container.add(incbtn);
            quantityHolder.add(container, cons);
        }
    }

    public ValueMenu() {
        Connection conn = master.getConnection();
        connect(conn, food_names, prices, value_ids, toppings);

        // format the container
        valueHolder.setLayout(new GridBagLayout()); // panel that holds buttons
        priceHolder.setLayout(new GridBagLayout());
        quantityHolder.setLayout(new GridBagLayout());
        cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.weightx = 1;
        cons.gridx = 0;
        valueHolder.setAlignmentX(Component.CENTER_ALIGNMENT);
        quantityHolder.setAlignmentX(Component.CENTER_ALIGNMENT);
        priceHolder.setAlignmentX(Component.CENTER_ALIGNMENT);

        for (String food_name : food_names) { // generate food buttons and counters
            // create button for food
            JButton foodbtn = new JButton(food_name);
            valueHolder.add(foodbtn, cons);
            food_buttons.add(foodbtn);

            // add counter
            counters.add(new ValueMenu.Counter());
        }
        for (Float price: prices) { // add each price to panel
            JButton priceLabel = new JButton(String.valueOf(price));
            priceLabel.setFocusPainted(false);
            priceLabel.setOpaque(false);
            priceLabel.setContentAreaFilled(false);
            priceLabel.setBorderPainted(false);
            priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            priceHolder.add(priceLabel, cons);
        }
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // master.customerObj.resetText();
                // Get food name, price, and quantity so that it can be output in order box of "Customer"
                int [] counterValues = new int[counters.size()];
                float totalPrice = 0;
                for (CharSequence s : value_order) {
                    master.customerObj.resetorderList(s);
                }
                value_order.clear();
                for (int i = 0; i < counters.size(); i++) {
                    counterValues[i] = counters.get(i).value;
                    if (counters.get(i).value > 0) {
                        String s = counters.get(i).value + " " + food_names.get(i) + " @ " + prices.get(i) + " each" + "\n";
                        value_order.add(s);
                        master.customerObj.printOrderField(s);
                        totalPrice += counters.get(i).value * prices.get(i);
                    }
                }
                totalPrice = (float) Math.round(totalPrice * 100) / 100;
                String t_price = "$" + totalPrice;
                master.customerObj.printTotalField(t_price);
                master.customerObj.ord.updateValueMenu(counterValues);
                master.valueFrame.setVisible(false);
                totalPrice = 0;
            }
        });
    }
    public void connect(Connection conn, ArrayList<String> food_names, ArrayList<Float> prices, ArrayList<String> value_ids,
                        ArrayList<String> toppings) {
        try {
            //create a statement object
            Statement stmt = conn.createStatement();
            //create an SQL statement
            String sqlStatement = "Select food_name, price, menu_id, special_note from menu where price < 4";
            //send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);

            //OUTPUT
            //System.out.println("______________________________________");
            while (result.next()) {
                food_names.add(result.getString("food_name"));
                prices.add(result.getFloat("price"));
                value_ids.add(result.getString("menu_id"));
                toppings.add(result.getString("special_note"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Value Menu Error accessing Database.");
        }

        try {
            conn.close();
            //         JOptionPane.showMessageDialog(null, "Connection Closed.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connection NOT Closed.");
        }//end try catch
    }
}
