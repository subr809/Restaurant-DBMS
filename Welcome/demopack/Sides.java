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

public class Sides {
    GridBagConstraints cons;
    public JPanel sidesPanel;
    private JPanel sidesHolder;
    private JPanel quantityHolder;
    private JPanel priceHolder;
    private JLabel Food_Item;
    private JLabel Price;
    private JLabel Quantity;
    private JLabel Intro;
    private JButton OKButton;
    private JPanel availabilityHolder;
    ArrayList<String> food_names = new ArrayList<String>();
    ArrayList<Float> prices = new ArrayList<Float>();
    ArrayList<Counter> counters = new ArrayList<Counter>();
    ArrayList<JButton> food_buttons = new ArrayList<JButton>();
    ArrayList<String> side_order = new ArrayList<String>();
    ArrayList<String> side_ids = new ArrayList<String>();
    ArrayList<String> toppings = new ArrayList<String>();
    ArrayList<Integer> availability = new ArrayList<Integer>();

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

    public Sides() {
        Connection conn = master.getConnection();

        connect(conn, food_names, prices, toppings, side_ids, availability);

        // format the container
        sidesHolder.setLayout(new GridBagLayout());
        priceHolder.setLayout(new GridBagLayout());
        quantityHolder.setLayout(new GridBagLayout());
        availabilityHolder.setLayout(new GridBagLayout());
        cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.weightx = 1;
        cons.gridx = 0;
        sidesHolder.setAlignmentX(Component.CENTER_ALIGNMENT);
        quantityHolder.setAlignmentX(Component.CENTER_ALIGNMENT);
        priceHolder.setAlignmentX(Component.CENTER_ALIGNMENT);
        availabilityHolder.setAlignmentX(Component.CENTER_ALIGNMENT);

        for (String food_name : food_names) {
            // create button for food
            JButton foodbtn = new JButton(food_name);
            foodbtn.setName(food_name);
            sidesHolder.add(foodbtn, cons);
            food_buttons.add(foodbtn);
            // add counter
            counters.add(new Counter());
            foodbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent f) {
                    Panel al = new Panel();
                    for (int i = 0; i < toppings.size(); ++i) {
                        String top = toppings.get(i);
                        al.removeAll();
                        if (foodbtn.getName().equals(food_names.get(i))) {
                            String[] parsed = top.split("[,]", 0);
                            for (String s : parsed) {
                                JCheckBox toppingOp = new JCheckBox(s);
                                toppingOp.setName(s);
                                al.add(toppingOp);
                                toppingOp.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if (toppingOp.isSelected()) {
                                            master.customerObj.printToppingOptions(foodbtn.getName(), toppingOp.getName());
                                        } else {
                                            String seq = foodbtn.getName() + ": " + toppingOp.getName() + '\n';
                                            master.customerObj.resetToppingOptions(seq);
                                        }
                                    }
                                });
                            }
                            if (foodbtn.getName().equals(food_name)) {
                                int db = JOptionPane.showConfirmDialog(null, al, "Select Topping(s)", JOptionPane.YES_NO_OPTION);
                            }
                        }
                    }
                }
            });
        }

        for (Float price : prices) {
            JButton priceLabel = new JButton(String.valueOf(price));
            priceLabel.setFocusPainted(false);
            priceLabel.setOpaque(false);
            priceLabel.setContentAreaFilled(false);
            priceLabel.setBorderPainted(false);
            priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            priceHolder.add(priceLabel, cons);
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

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get food name, price, and quantity so that it can be output in order box of "Customer"
                int [] counterValues = new int[counters.size()];
                double totalPrice = 0;
                for (int i = 0; i < counters.size(); i++) {
                    counterValues[i] = counters.get(i).value;
                    if (counters.get(i).value > 0) {
                        String s = counters.get(i).value + " " + food_names.get(i) + " @ " + prices.get(i) + " each" + "\n";
//                        side_order.add(s);
                        master.customerObj.printOrderField(s);
                        totalPrice += counters.get(i).value * prices.get(i);
                    }
                }
                totalPrice = (double) Math.round(totalPrice * 100) / 100;
//                String t_price = "$" + totalPrice;
//                master.customerObj.printTotalField(t_price);
                master.customerObj.ord.updateSides(counterValues, totalPrice);
                master.sidesFrame.setVisible(false);
                totalPrice = 0;
            }
        });
    }

    public void connect(Connection conn, ArrayList<String> food_names, ArrayList<Float> prices, ArrayList<String>
            toppings, ArrayList<String> side_ids, ArrayList<Integer> availability) {
        try {
            //create a statement object
            Statement stmt = conn.createStatement();
            //create an SQL statement
            String sqlStatement = "SELECT food_name, price, menu_id, special_note FROM menu WHERE menu_id LIKE '%S%'";
            String sqlAvailability = "SELECT quantity FROM inventory WHERE menu_id LIKE '%S%'";
            PreparedStatement price_pst = conn.prepareStatement(sqlStatement);
            PreparedStatement ava_pst = conn.prepareStatement(sqlAvailability);
            //send statement to DBMS
            ResultSet result = price_pst.executeQuery();
            ResultSet resultAvailability = ava_pst.executeQuery();

            //OUTPUT
            //JOptionPane.showMessageDialog(null, "Menu items from the Database.");
            //System.out.println("______________________________________");
            while (result.next() && resultAvailability.next()) {
                //System.out.println(result.getString("cus_lname"));
                food_names.add(result.getString("food_name"));
                prices.add(result.getFloat("price"));
                side_ids.add(result.getString("menu_id"));
                toppings.add(result.getString("special_note"));
                availability.add(resultAvailability.getInt("quantity"));
                //food_name += result.getString("food_name") + "\n";
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Sides Error accessing Database.");
        }
        //JOptionPane.showMessageDialog(null, food_name);
        try {
            conn.close();
        //    JOptionPane.showMessageDialog(null, "Connection Closed.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connection NOT Closed.");
        }//end try catch
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sides");
        frame.setContentPane(new Sides().sidesPanel);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
