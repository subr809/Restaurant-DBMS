package demopack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Desserts {
    GridBagConstraints cons;
    private JTextArea textArea1;
    private JTextArea textArea2;
    public JPanel dessertsPanel;
    private JPanel dessertHolder;
    private JPanel priceHolder;
    private JPanel quantityHolder;
    private JLabel Food_Item;
    private JLabel Price;
    private JLabel Quantity;
    private JLabel Intro;
    private JPanel panel;
    private JButton OKButton;
    private JPanel availabilityHolder;
    ArrayList<String> food_names = new ArrayList<String>();
    ArrayList<Float> prices = new ArrayList<Float>();
    ArrayList<Counter> counters = new ArrayList<Counter>();
    ArrayList<JButton> food_buttons = new ArrayList<JButton>();
    ArrayList<String> dessert_ids = new ArrayList<String>();
    ArrayList<CharSequence> dessert_order = new ArrayList<CharSequence>();
    ArrayList<Integer> availability = new ArrayList<Integer>();

    public void printTextField(String text) {
        textArea1.setText(text);
    }

    public class Counter{
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
                    if(i > 0)
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

    public Desserts() {
        Connection conn = master.getConnection();
        connect(conn, food_names, prices, dessert_ids, availability);

        dessertHolder.setLayout(new GridBagLayout());
        priceHolder.setLayout(new GridBagLayout());
        quantityHolder.setLayout(new GridBagLayout());
        availabilityHolder.setLayout(new GridBagLayout());
        cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.weightx = 1;
        cons.gridx = 0;
        dessertHolder.setAlignmentX(Component.CENTER_ALIGNMENT);
        quantityHolder.setAlignmentX(Component.CENTER_ALIGNMENT);
        priceHolder.setAlignmentX(Component.CENTER_ALIGNMENT);
        availabilityHolder.setAlignmentX(Component.CENTER_ALIGNMENT);

        final int[] q_var = {0};

        for (String food_name : food_names) { // generate food buttons and counters
            // create button for food
            JButton foodbtn = new JButton(food_name);
            dessertHolder.add(foodbtn, cons);
            food_buttons.add(foodbtn);

            // add counter
            counters.add(new Desserts.Counter());
        }
        for (Integer a : availability) {
            JButton aLabel = new JButton(String.valueOf(a));
            aLabel.setFocusPainted(false);
            aLabel.setOpaque(false);
            aLabel.setContentAreaFilled(false);
            aLabel.setBorderPainted(false);
            aLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            availabilityHolder.add(aLabel, cons);
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
                double totalPrice = 0;
//                for (CharSequence s : dessert_order) {
//                    master.customerObj.resetorderList(s);
//                }
//                dessert_order.clear();
                for (int i = 0; i < counters.size(); i++) {
                    counterValues[i] = counters.get(i).value;
                    if (counters.get(i).value > 0) {
                        String s = counters.get(i).value + " " + food_names.get(i) + " @ " + prices.get(i) + " each" + "\n";
//                        dessert_order.add(s);
                        master.customerObj.printOrderField(s);
                        totalPrice += counters.get(i).value * prices.get(i);
                    }
                }
                totalPrice = (double) Math.round(totalPrice * 100) / 100;
//                String t_price = "$" + totalPrice;
//                master.customerObj.printTotalField(t_price);
                master.customerObj.ord.updateDesserts(counterValues, totalPrice);
                master.dessertsFrame.setVisible(false);
                totalPrice = 0;
            }
        });
    }

    public void connect(Connection conn, ArrayList<String> food_names, ArrayList<Float> prices, ArrayList<String> dessert_ids,
                        ArrayList<Integer> availability){
        try {
            //create a statement object
            Statement stmt = conn.createStatement();
            //create an SQL statement
            String sqlStatement = "SELECT food_name, price, menu_id FROM menu WHERE menu_id LIKE '%D%'";
            String sqlAvailability = "SELECT quantity FROM inventory WHERE menu_id LIKE '%D%'";
            PreparedStatement price_pst = conn.prepareStatement(sqlStatement);
            PreparedStatement ava_pst = conn.prepareStatement(sqlAvailability);
            ResultSet result = price_pst.executeQuery();
            ResultSet resultAvailability = ava_pst.executeQuery();

            //OUTPUT
            // JOptionPane.showMessageDialog(null, "Menu items from the Database.");
            //System.out.println("______________________________________");
            while (result.next() && resultAvailability.next()) {
                food_names.add(result.getString("food_name"));
                prices.add(result.getFloat("price"));
                dessert_ids.add(result.getString("menu_id"));
                availability.add(resultAvailability.getInt("quantity"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Desserts Error accessing Database.");
        }
        try {
            conn.close();
            //    JOptionPane.showMessageDialog(null, "Connection Closed.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connection NOT Closed.");
        }//end try catch
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Dessert");
        frame.setContentPane(new Desserts().dessertsPanel);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
