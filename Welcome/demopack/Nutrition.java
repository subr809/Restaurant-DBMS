package demopack;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Nutrition {
    GridBagConstraints cons;
    public JPanel nutritionPanel;
    private JTable nutritionTable;
    private JButton backButton;
    private JScrollPane nutritionScroll;

    public Nutrition() {
        Connection conn = master.getConnection();
        connect(conn);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                master.customerFrame.setVisible(true);
                master.nutritionFrame.setVisible(false);
            }
        });
    }


    /*public void setLabels(int ) {

    }*/
    public void connect(Connection conn){
        //Map<String, JButton> buttonsMap = new HashMap<String, JButton>();
        try {
            //create a statement object
            Statement stmt = conn.createStatement();
            DefaultTableModel tb1Model = new DefaultTableModel(new String[]{"Food Name", "Allergens", "Calories", "Fat"
            , "Carbs", "Protein", "Sodium", "Sugars", "Cholesterol"}, 0);
            //create an SQL statement
            String sqlStatement = "SELECT food_name, allergens, calories, fat, carbs, protein, sodium, sugars, cholesterol FROM food_info";
            PreparedStatement nutrition_pst = conn.prepareStatement(sqlStatement);
            //send statement to DBMS
            ResultSet result = nutrition_pst.executeQuery();
            //OUTP3T
            while (result.next()) {
                String food_name = result.getString("food_name");
                String allergens = result.getString("allergens");
                int calories = result.getInt("calories");
                String fats = result.getString("fat");
                String carbs = result.getString("carbs");
                String protein = result.getString("protein");
                String sodium = result.getString("sodium");
                String sugars = result.getString("sugars");
                String cholesterol = result.getString("cholesterol");
                tb1Model.addRow(new Object[] {food_name, allergens, calories, fats, carbs, protein, sodium, sugars, cholesterol});
            }
            nutritionTable.setModel(tb1Model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Nutrition Error accessing Database.");
        }
        try {
            conn.close();
            //    JOptionPane.showMessageDialog(null, "Connection Closed.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connection NOT Closed.");
        }//end try catch (end initial connection)
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Food Info");
        frame.setContentPane(new Nutrition().nutritionPanel);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
