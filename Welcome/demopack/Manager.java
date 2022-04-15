package demopack;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Manager {
    public JPanel managerPanel;
    private JButton inventoryButton;
    private JButton menuButton;
    private JButton trendingButton;
    private JButton backButton;
    private JLabel user;
    private static JFrame frame;

    public void setName(String s) {
        user.setText("Manager Username: " + s);
    }

    public Manager() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showConfirmDialog(null, "welcome page");
                master.managerFrame.setVisible(false);
                master.welcomeFrame.setVisible(true);
            }
        });
        inventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                master.managerFrame.setVisible(false);
                master.inventoryFrame.setVisible(true);
            }
        });
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                master.managerFrame.setVisible(false);
                master.managerMenuFrame.setVisible(true);
            }
        });
        trendingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                master.managerFrame.setVisible(false);
                master.trendingFrame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        frame = new JFrame("Manager");
        frame.setContentPane(new Manager().managerPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
