package demopack;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class welcome_page {
    public JPanel rootPanel;
    private JButton customerButton;
    private JButton managerButton;
    private JLabel imageLabel;

    public welcome_page() {
        customerButton.setFocusPainted(false);
        managerButton.setFocusPainted(false);
        customerButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
//            int i = JOptionPane.showConfirmDialog(null, "Customer");
//            if (i == 0) {
                master.customerFrame.setVisible(true);
                master.welcomeFrame.setVisible(false);
//            }
            }
        });
        managerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "Manager");
                if (i == 0) {
                    master.loginFrame.setVisible(true);
                    master.welcomeFrame.setVisible(false);
                }
            }
        });
    }


    public static void main(String[] args) {
    }


}
