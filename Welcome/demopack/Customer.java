package demopack;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Customer {
    public JPanel customerPanel;
    private JButton recommendedButton;
    private JButton dessertsButton;
    private JButton entreesButton;
    private JButton valueMenuButton;
    private JButton sidesButton;
    private JButton drinksButton;
    private JButton paymentOptionsButton;
    private JCheckBox dineInCheckBox;
    private JCheckBox deliveryCheckBox;
    private JCheckBox toGoCheckBox;
    private JTextArea paymentSelected;
    public JTextArea totalPrice;
    public JTextArea orderList;
    private JButton backButton;
    private JButton checkoutButton;
    private JTextArea pleaseEnterFirstNameTextArea;
    private JTextArea enterCarDescriptionColorTextArea;
    private JTextArea pleaseEnterLastNameTextArea;
    private JButton nutritionButton;
    private JTextArea toppingArea;
    ArrayList<String> entree_orders = new ArrayList<String>();
    ArrayList<String> sides_orders = new ArrayList<String>();
    ArrayList<String> drinks_orders = new ArrayList<String>();
    JCheckBox Credit = new JCheckBox("Credit");
    JCheckBox Debit = new JCheckBox("Debit");
    JCheckBox Cash = new JCheckBox("Cash");
    JPanel al = new JPanel();  //

    Order ord = new Order();

    // Returns first name
    public String getFirstName() {
        return pleaseEnterFirstNameTextArea.getText();
    }
    // Returns last name
    public String getLastName() {
        return pleaseEnterLastNameTextArea.getText();
    }
    // Returns if payment method is credit
    public String getPaymentMethod () {
        return paymentSelected.getText();
    }
    // Returns true if order is to-go
    public boolean toGo() {
        return toGoCheckBox.isSelected();
    }
    // Returns car description if order is for delivery
    public String getCarDescription() {
        return enterCarDescriptionColorTextArea.getText();
    }
    // Prints topping options into "Toppings" text area
    public void printToppingOptions(String food_item, String text) {
        String newText = food_item + ": " + text + '\n';
        toppingArea.append(newText);
    }
    // Removes a topping from text area if it is deselected
    public void resetToppingOptions(CharSequence s) {
        toppingArea.setText(toppingArea.getText().replace(s,""));
    }
    // Prints payment method into "paymentSelected" text area
    public void printPaymentField(String text) {
        paymentSelected.setText(text);
    }
    // Removes an order from order list text area if the customer chooses to delete it
    public void resetorderList(CharSequence s) {
        orderList.setText(orderList.getText().replace(s,""));
        //System.out.println(orderList.getText());
    }
    // Resets the total price text area to empty
    public void resettotalPrice(CharSequence s) {
        totalPrice.setText(totalPrice.getText().replace(s,""));
        //System.out.println(orderList.getText());
    }
    // Adds order to the order text area
    public void printOrderField(String text) {
        orderList.append(text);
    }
    // Adds total price to total price text area
    public void printTotalField(String text) {
        totalPrice.setText(text);
    }

    // Resets all customer information when the user backs out of page or when a customer finishes their order
    void resetCustomer() {
        ord = new Order();
        for (Component C : master.loginFrame.getContentPane().getComponents()) // clear text fields
        {
            if (C instanceof JTextField || C instanceof JTextArea) {

                ((JTextComponent) C).setText(""); //abstract superclass
            }
        }  //end for
        pleaseEnterFirstNameTextArea.setText("");
        pleaseEnterLastNameTextArea.setText("");
        enterCarDescriptionColorTextArea.setText("");
        paymentSelected.setText("");
        totalPrice.setText("");
        orderList.setText("");
        toppingArea.setText("");
        Credit.setSelected(false);
        Debit.setSelected(false);
        Cash.setSelected(false);
        Credit.setEnabled(true);
        Debit.setEnabled(true);
        Cash.setEnabled(true);
        dineInCheckBox.setSelected(false);
        deliveryCheckBox.setSelected(false);
        toGoCheckBox.setSelected(false);
        dineInCheckBox.setEnabled(true);
        deliveryCheckBox.setEnabled(true);
        toGoCheckBox.setEnabled(true);
    }

    // Class for Customer
    public Customer() {
        // Text Areas set to not editable
        paymentSelected.setEditable(false);
        totalPrice.setEditable(false);
        orderList.setEditable(false);
        toppingArea.setEditable(false);
        // Focus boxes on buttons removed
        recommendedButton.setFocusPainted(false);
        entreesButton.setFocusPainted(false);
        sidesButton.setFocusPainted(false);
        dessertsButton.setFocusPainted(false);
        drinksButton.setFocusPainted(false);
        paymentOptionsButton.setFocusPainted(false);
        valueMenuButton.setFocusPainted(false);
        dineInCheckBox.setFocusPainted(false);
        toGoCheckBox.setFocusPainted(false);
        deliveryCheckBox.setFocusPainted(false);
        backButton.setFocusPainted(false);
        checkoutButton.setFocusPainted(false);

        // When user clicks back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                master.welcomeFrame.setVisible(true);
                master.customerFrame.setVisible(false);
                resetCustomer();
            }
        });
        // When user clicks payment options button
        paymentOptionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                al.add(Credit);
                al.add(Debit);
                al.add(Cash);
                int db = JOptionPane.showConfirmDialog(null, al, "Payment Option", JOptionPane.YES_NO_OPTION);
                if (db == 1) {
                    printPaymentField("");
                    Credit.setSelected(false);
                    Debit.setSelected(false);
                    Cash.setSelected(false);
                    Credit.setEnabled(true);
                    Debit.setEnabled(true);
                    Cash.setEnabled(true);
                }
            }
        });
        // When user clicks order style check boxes
        dineInCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dineInCheckBox.isSelected()) {
                    deliveryCheckBox.setEnabled(false);
                    toGoCheckBox.setEnabled(false);
                }
                if (!toGoCheckBox.isSelected() && !deliveryCheckBox.isSelected() && !dineInCheckBox.isSelected()) {
                    toGoCheckBox.setEnabled(true);
                    deliveryCheckBox.setEnabled(true);
                    dineInCheckBox.setEnabled(true);
                }
            }
        });
        toGoCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (toGoCheckBox.isSelected()) {
                    deliveryCheckBox.setEnabled(false);
                    dineInCheckBox.setEnabled(false);
                }
                if (!toGoCheckBox.isSelected() && !deliveryCheckBox.isSelected() && !dineInCheckBox.isSelected()) {
                    toGoCheckBox.setEnabled(true);
                    deliveryCheckBox.setEnabled(true);
                    dineInCheckBox.setEnabled(true);
                }
            }
        });
        deliveryCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (deliveryCheckBox.isSelected()) {
                    toGoCheckBox.setEnabled(false);
                    dineInCheckBox.setEnabled(false);
                }
                if (!toGoCheckBox.isSelected() && !deliveryCheckBox.isSelected() && !dineInCheckBox.isSelected()) {
                    toGoCheckBox.setEnabled(true);
                    deliveryCheckBox.setEnabled(true);
                    dineInCheckBox.setEnabled(true);
                }
            }
        });
        // When user clicks on payment methods
        Credit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Credit.isSelected()) {
                    Debit.setEnabled(false);
                    Cash.setEnabled(false);
                    printPaymentField("Credit");
                }
                if (!Credit.isSelected() && !Debit.isSelected() && !Cash.isSelected()) {
                    Credit.setEnabled(true);
                    Debit.setEnabled(true);
                    Cash.setEnabled(true);
                    printPaymentField("");
                }
            }
        });
        Debit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Debit.isSelected()) {
                    Credit.setEnabled(false);
                    Cash.setEnabled(false);
                    printPaymentField("Debit");
                }
                if (!Credit.isSelected() && !Debit.isSelected() && !Cash.isSelected()) {
                    Credit.setEnabled(true);
                    Debit.setEnabled(true);
                    Cash.setEnabled(true);
                    printPaymentField("");
                }
            }
        });
        Cash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Cash.isSelected()) {
                    Debit.setEnabled(false);
                    Credit.setEnabled(false);
                    printPaymentField("Cash");
                }
                if (!Credit.isSelected() && !Debit.isSelected() && !Cash.isSelected()) {
                    Credit.setEnabled(true);
                    Debit.setEnabled(true);
                    Cash.setEnabled(true);
                    printPaymentField("");
                }
            }
        });
        // When user clicks food options on Customer page
        recommendedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                master.recommendedFrame.setVisible(true);
            }
        });
        valueMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                master.valueFrame.setVisible(true);
            }
        });
        entreesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent f) {
                master.entreeFrame.setVisible(true);
                //Entrees.main(new String[]{});
            }
        });
        sidesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                master.sidesFrame.setVisible(true);
//                Sides.main(new String[]{});
            }
        });
        drinksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                master.drinksFrame.setVisible(true);
//                Drink.main(new String[]{});
            }
        });
        dessertsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                master.dessertsFrame.setVisible(true);
//                Dessert.main(new String[]{});
            }
        });
        nutritionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                master.nutritionFrame.setVisible(true);
//                Dessert.main(new String[]{});
            }
        });
        // When user heads to checkout page (final page) to confirm order
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "Are you sure you want to checkout?");
                //if
//                Checkout.main(new String[]{});
                master.checkoutObj.setOrdersText(orderList.getText());
                master.checkoutObj.setTotalText(totalPrice.getText());
                master.checkoutObj.setToppingsText(toppingArea.getText());
                master.checkoutFrame.setVisible(true);
            }
        });
    }
    // Main for Customer
    public static void main(String[] args) {
        JFrame frame = new JFrame("demopack.master.Customer");
        frame.setContentPane(new Customer().customerPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}