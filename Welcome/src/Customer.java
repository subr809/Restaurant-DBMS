import javax.swing.*;
import java.awt.event.*;

public class Customer {
    private JButton recommendedButton;
    private JButton dessertsButton;
    private JButton entreesButton;
    private JButton valueMenuButton;
    private JButton sidesButton;
    private JButton specialOffersButton;
    private JButton drinksButton;
    private JButton paymentOptionsButton;
    private JCheckBox dineInCheckBox;
    private JPanel customerPanel;
    private JCheckBox deliveryCheckBox;
    private JCheckBox toGoCheckBox;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;
    JCheckBox Check1 = new JCheckBox("Credit");
    JCheckBox Check2 = new JCheckBox("Debit");
    JCheckBox Check3 = new JCheckBox("Cash");
    JPanel al = new JPanel();

    public void printTextField(String text) {
        textArea1.setText(text);
    }

    public Customer() {
        textArea1.setEditable(false);
        textArea2.setEditable(false);
        textArea3.setEditable(false);
        paymentOptionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                al.add(Check1);
                al.add(Check2);
                al.add(Check3);
                int db = JOptionPane.showConfirmDialog(null, al, "Payment Option", JOptionPane.YES_NO_OPTION);
                if (db == 1) {
                    printTextField("");
                    Check1.setSelected(false);
                    Check2.setSelected(false);
                    Check3.setSelected(false);
                    Check1.setEnabled(true);
                    Check2.setEnabled(true);
                    Check3.setEnabled(true);
                }

            }
        });
        dineInCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dineInCheckBox.isSelected()) {
                    deliveryCheckBox.setEnabled(false);
                    toGoCheckBox.setEnabled(false);
                }
                if (!toGoCheckBox.isSelected() && !deliveryCheckBox.isSelected() && !dineInCheckBox.isSelected()){
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
                if (!toGoCheckBox.isSelected() && !deliveryCheckBox.isSelected() && !dineInCheckBox.isSelected()){
                    toGoCheckBox.setEnabled(true);
                    deliveryCheckBox.setEnabled(true);
                    dineInCheckBox.setEnabled(true);
                }
            }
        });
        deliveryCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (deliveryCheckBox.isSelected()){
                    toGoCheckBox.setEnabled(false);
                    dineInCheckBox.setEnabled(false);
                }
                if (!toGoCheckBox.isSelected() && !deliveryCheckBox.isSelected() && !dineInCheckBox.isSelected()){
                    toGoCheckBox.setEnabled(true);
                    deliveryCheckBox.setEnabled(true);
                    dineInCheckBox.setEnabled(true);
                }
            }
        });
        Check1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Check1.isSelected()){
                    Check2.setEnabled(false);
                    Check3.setEnabled(false);
                    printTextField("Credit");
                }
                if (!Check1.isSelected() && !Check2.isSelected() && !Check3.isSelected()) {
                    Check1.setEnabled(true);
                    Check2.setEnabled(true);
                    Check3.setEnabled(true);
                    printTextField("");
                }
            }
        });
        Check2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Check2.isSelected()){
                    Check1.setEnabled(false);
                    Check3.setEnabled(false);
                    printTextField("Debit");
                }
                if (!Check1.isSelected() && !Check2.isSelected() && !Check3.isSelected()){
                    Check1.setEnabled(true);
                    Check2.setEnabled(true);
                    Check3.setEnabled(true);
                    printTextField("");
                }
            }
        });
        Check3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Check3.isSelected()){
                    Check2.setEnabled(false);
                    Check1.setEnabled(false);
                    printTextField("Cash");
                }
                if (!Check1.isSelected() && !Check2.isSelected() && !Check3.isSelected()){
                    Check1.setEnabled(true);
                    Check2.setEnabled(true);
                    Check3.setEnabled(true);
                    printTextField("");
                }
            }
        });
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Customer");
        frame.setContentPane(new Customer().customerPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
