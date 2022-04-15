
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class welcome_page {
    private JPanel rootPanel;
    private JButton customerButton;
    private JButton managerButton;

    public welcome_page(){
        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(null,"Customer");
            }
        });
        managerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(null,"Manager");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Welcome Page");
        frame.setContentPane(new welcome_page().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
