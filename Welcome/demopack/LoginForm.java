package demopack;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginForm {
    public JPanel loginPanel;
    private JButton loginButton;
    private JPasswordField passwordField1;
    private JTextField textField1;

    public String getPasswordField1() {
        return passwordField1.getText();
    }

    public String getTextField1() {
        return textField1.getText();
    }

    public LoginForm() {
        Connection conn = master.getConnection();
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cases for empty fields
                if (getTextField1().equals("") && getPasswordField1().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter username and password.");
                } else if (getTextField1().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter username.");
                } else if (getPasswordField1().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter password.");
                } else {
                    try {
                        // Create a statement object
                        Statement stmt = conn.createStatement();
                        // Create SQL statements
                        char[] password = passwordField1.getPassword();
                        String pass_str = new String(password);
                        String user_sqlStatement = "SELECT manager_id FROM manager WHERE username='" + textField1.getText() + "'";
                        String pass_sqlStatement = "SELECT manager_id FROM manager WHERE password='" + pass_str + "'";
                        // Send statements to DBMS
                        PreparedStatement user_pst = conn.prepareStatement(user_sqlStatement);
                        PreparedStatement pass_pst = conn.prepareStatement(pass_sqlStatement);
                        ResultSet user_rs = user_pst.executeQuery();
                        ResultSet pass_rs = pass_pst.executeQuery();
                        int i = 0; // i = 0 when login hasn't worked yet, i = 1 when login is successful
                        while (user_rs.next() && pass_rs.next()) {
                            // When login is successful
                            if (pass_rs.getInt("manager_id") == pass_rs.getInt("manager_id")) {
                                i = 1;
                                JOptionPane.showMessageDialog(null, "Login Successfully");
                                master.managerObj.setName(getTextField1());
                                master.managerFrame.setVisible(true);
                                master.loginFrame.setVisible(false);
                                for (Component C : master.loginFrame.getContentPane().getComponents()) // clear text fields
                                {
                                    if (C instanceof JTextField || C instanceof JTextArea) {
                                        ((JTextComponent) C).setText(""); //abstract superclass
                                    }
                                } //end for
                            }
                        }
                        if (i == 0) {   // if login does not work
                            JOptionPane.showMessageDialog(null, "Login Failed. Please try again.");
                        }
                    } catch (Exception q) {
                        JOptionPane.showMessageDialog(null, q);
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
    }
}

