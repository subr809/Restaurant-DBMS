import javax.swing.*;
import java.awt.event.*;

public class LoginForm {
    private JPanel loginPanel;
    private JButton loginButton;
    private JPasswordField passwordField1;
    private JTextField textField1;

    public JPasswordField getPasswordField1() {
        return passwordField1;
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public LoginForm() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Login successful");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LoginForm");
        frame.setContentPane(new LoginForm().loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
