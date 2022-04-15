import javax.swing.*;

public class Manager {
    private JPanel loginPanel;
    private JButton favoritesButton;
    private JButton valueMenuButton;
    private JButton sidesButton;
    private JButton dessertsButton;
    private JButton beveragesButton;
    private JButton ordersButton;
    private JButton inventoryButton;
    private JButton pricesButton;
    private JButton customerInformationButton;


    public static void main(String[] args) {
        JFrame frame = new JFrame("Customer");
        frame.setContentPane(new Manager().loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
