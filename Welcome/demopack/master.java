package demopack;
import java.sql.*;
import javax.swing.*;
import java.awt.Toolkit;
import java.awt.Dimension;

class master {
    public static JFrame welcomeFrame;
    public static JFrame managerFrame;
    public static JFrame inventoryFrame;
    public static JFrame managerMenuFrame;
    public static JFrame trendingFrame;
    public static JFrame loginFrame;
    public static JFrame customerFrame;
    public static JFrame entreeFrame;
    public static JFrame sidesFrame;
    public static JFrame dessertsFrame;
    public static JFrame nutritionFrame;
    public static JFrame valueFrame;
    public static JFrame recommendedFrame;
    public static JFrame drinksFrame;
    public static JFrame foodInfoFrame;
    public static JFrame checkoutFrame;
    public static Customer customerObj;
    public static Nutrition foodInfoObj;
    public static Manager managerObj;
    public static Checkout checkoutObj;
    public static void main(String[] args){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        loginFrame = new JFrame("LoginForm");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setContentPane(new LoginForm().loginPanel);
        loginFrame.pack();
        loginFrame.setVisible(false);

        managerObj = new Manager();

        managerFrame = new JFrame("Manager");
        managerFrame.setContentPane(managerObj.managerPanel);
        managerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        managerFrame.setSize(screenSize);
        managerFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        managerFrame.setUndecorated(true);
        managerFrame.setVisible(false);

        inventoryFrame = new JFrame("Manager Inventory");
        inventoryFrame.setContentPane(new ManagerInventory().inventoryPanel);
        inventoryFrame.setSize(screenSize);
        inventoryFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        inventoryFrame.setUndecorated(true);
        //entreeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //inventoryFrame.pack();
        inventoryFrame.setVisible(false);

        managerMenuFrame = new JFrame("Manager Menu");
        managerMenuFrame.setContentPane(new ManagerMenu().managerMenuPanel);
        //managerMenuFrame.setSize(screenSize);
        //managerMenuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //managerMenuFrame.setUndecorated(true);
        //entreeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        managerMenuFrame.pack();
        managerMenuFrame.setVisible(false);

        trendingFrame = new JFrame("Trending");
        trendingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        trendingFrame.setContentPane(new Trending().trendingPanel);
        trendingFrame.pack();
        trendingFrame.setVisible(false);

        entreeFrame = new JFrame("Entrees");
        entreeFrame.setContentPane(new Entrees().entreesHolder);
        //entreeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        entreeFrame.pack();
        entreeFrame.setVisible(false);

        foodInfoObj = new Nutrition();

        foodInfoFrame = new JFrame("Food Info");
        foodInfoFrame.setContentPane(foodInfoObj.nutritionPanel);
        //entreeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        foodInfoFrame.pack();
        entreeFrame.setVisible(false);

        sidesFrame = new JFrame("Sides");
        sidesFrame.setContentPane(new Sides().sidesPanel);
        //entreeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sidesFrame.pack();
        sidesFrame.setVisible(false);

        dessertsFrame = new JFrame("Desserts");
        dessertsFrame.setContentPane(new Desserts().dessertsPanel);
        //entreeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dessertsFrame.pack();
        dessertsFrame.setVisible(false);

        drinksFrame = new JFrame("Drinks");
        drinksFrame.setContentPane(new Drinks().drinksPanel);
        //entreeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drinksFrame.pack();
        drinksFrame.setVisible(false);

        valueFrame = new JFrame("Value Menu");
        valueFrame.setContentPane(new ValueMenu().valuePanel);
        //entreeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        valueFrame.pack();
        valueFrame.setVisible(false);

        recommendedFrame = new JFrame("Recommended");
        recommendedFrame.setContentPane(new Recommended().recommendedPanel);
        //entreeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        recommendedFrame.pack();
        recommendedFrame.setVisible(false);

        nutritionFrame = new JFrame("Nutrition");
        nutritionFrame.setContentPane(new Nutrition().nutritionPanel);
        //entreeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        nutritionFrame.pack();
        nutritionFrame.setVisible(false);

        customerObj = new Customer();

        customerFrame = new JFrame("Customer");
        customerFrame.setContentPane(customerObj.customerPanel);
        customerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        customerFrame.setSize(screenSize);
        customerFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        customerFrame.setUndecorated(true);
        customerFrame.setVisible(false);

        checkoutObj = new Checkout();

        checkoutFrame = new JFrame("Checkout");
        checkoutFrame.setContentPane(checkoutObj.checkoutPanel);
        checkoutFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        checkoutFrame.pack();
        checkoutFrame.setVisible(false);

        welcomeFrame = new JFrame("Welcome Page");
        welcomeFrame.setContentPane(new welcome_page().rootPanel);
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.pack();
        welcomeFrame.setVisible(true);
    }

    public static Connection getConnection() {
        dbSetup my = new dbSetup();
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/db901_group11_project2",
                    my.user, my.pswd);
        }   catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }//end try catch
        return conn;
    }

}