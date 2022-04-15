package demopack;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Checkout {
    public JPanel checkoutPanel;
    private JTextArea totalTextArea;
    private JTextArea ordersTextArea;
    private JButton confirmOrderButton;
    private JButton backButton;
    private JTextArea toppingsTextArea;


    public Checkout() {
        confirmOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                master.customerObj.ord.sendToDatabase();
                master.checkoutFrame.setVisible(false);
                master.customerObj.resetCustomer();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                master.checkoutFrame.setVisible(false);
            }
        });
    }

    public void setOrdersText(String text){
        ordersTextArea.setText(text);
    }
    public void setTotalText(String text){
        totalTextArea.setText(text);
    }
    public void setToppingsText(String text){
        toppingsTextArea.setText(text);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Checkout");
        frame.setContentPane(new Checkout().checkoutPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
