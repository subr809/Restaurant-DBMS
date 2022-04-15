package demopack;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;

import static java.time.LocalDate.now;


public class Order {
    // index 0-6 = e1-e7
    // index 7-10 = s1-s4
    // index 11-15 = b1-b5
    // index 16-17 = d1-d2
    String[] itemnames = {"E1", "E2", "E3", "E4", "E5", "E6", "E7", "S1", "S2", "S3", "S4", "B1", "B2", "B3", "B4", "B5", "D1", "D2"};
    int[] itemcounts = new int[18];
    int entreesidx = 0;
    int sidesidx = 7;
    int drinksidx = 11;
    int dessertsidx = 16;
//    int valuemenuidx = 0;
    double entreestotalprice = 0;
    double sidestotalprice = 0;
    double drinkstotalprice = 0;
    double dessertstotalprice = 0;


    double calculateTotal(){
        double total = entreestotalprice + sidestotalprice + drinkstotalprice + dessertstotalprice;
        master.customerObj.printTotalField("$" + String.valueOf(total));
        return total;
    }

    void updateEntrees(int[] counterValues, double price){
        for (int i = 0; i < counterValues.length; i++){
            itemcounts[i+entreesidx] = counterValues[i];
        }
        entreestotalprice = price;
        calculateTotal();
    }
    void updateSides(int[] counterValues, double price){
        for (int i = 0; i < counterValues.length; i++){
            itemcounts[i+sidesidx] = counterValues[i];
        }
        sidestotalprice = price;
        calculateTotal();
    }
    void updateDrinks(int[] counterValues, double price){
        for (int i = 0; i < counterValues.length; i++){
            itemcounts[i+drinksidx] = counterValues[i];
        }
        drinkstotalprice = price;
        calculateTotal();
    }
    void updateDesserts(int[] counterValues, double price){
        for (int i = 0; i < counterValues.length; i++){
            itemcounts[i+dessertsidx] = counterValues[i];
        }
        dessertstotalprice = price;
        calculateTotal();
    }
    void updateValueMenu(int[] counterValues){
//        for (int i = 0; i < counterValues.length; i++){
//            itemcounts[i+valuemenuidx] = counterValues[i];
//        }
    }
    void sendToDatabase(){
        // implement
        Connection conn = master.getConnection();
        try {
            // Create a statement object
            Statement stmt = conn.createStatement();
            // get customer details
            String customer_id = "";
            String sqlStatement = "SELECT customer_id FROM customer WHERE last_name = '" + master.customerObj.getLastName().toUpperCase() + "' AND first_name = '" + master.customerObj.getFirstName().toUpperCase() + "'";
            PreparedStatement cid_pst = conn.prepareStatement(sqlStatement);
            ResultSet result = cid_pst.executeQuery();
            boolean isFound = false;
            while (result.next()){
                customer_id = String.valueOf(result.getInt("customer_id"));
                isFound = true;
//                System.out.println("found");
            }
            if (isFound == false){
//                System.out.println("not found");
                String sqlStatement2 = "SELECT MAX(customer_id) FROM customer";
                PreparedStatement lastcid_pst = conn.prepareStatement(sqlStatement2);
                ResultSet result2 = lastcid_pst.executeQuery();
                while(result2.next()){
                    int newmax = result2.getInt("max") + 1;
//                    System.out.println(newmax);
                    customer_id = String.valueOf(newmax);
                }
            }
            String first_name = master.customerObj.getFirstName().toUpperCase();
            String last_name = master.customerObj.getLastName().toUpperCase();
            String date = now().toString();
            String payment_method = master.customerObj.getPaymentMethod();
            String car_description = master.customerObj.getCarDescription();
            String to_go = (master.customerObj.toGo()) ? "True" : "False";
            String order_complete = "True";
            String order_entry = "{";
            for (int i = 0; i < itemnames.length; i++) {
                if (itemcounts[i] != 0) {
                    for (int j = 0; j < itemcounts[i]; j++) {
                        order_entry += itemnames[i] + ",";
                    }
                }
            }
            if(order_entry.endsWith(","))
            {
                order_entry = order_entry.substring(0,order_entry.length() - 1);
                order_entry += "}";
            }
//            char[] c = order_entry.toCharArray();
//            c[c.length-1] = '}';
//            order_entry = c.toString();
            String total_price = String.valueOf(calculateTotal());
            // Create SQL
            // generate statements to DBMS
            String orderEntry_sqlStatement = "INSERT INTO order_history (customer_id, last_name, first_name, date, " +
                    "payment_method, car_description, to_go, order_completion, order_entry, total_price)" +
                    " VALUES ('"+customer_id+"', '"+last_name+"', '"+first_name+"', '"+date+"', '"+payment_method+"', " +
                    "'"+car_description+"', " + "'"+to_go+"', '"+order_complete+"', '"+order_entry+"', '"+total_price+"')";
            System.out.println(orderEntry_sqlStatement);
            // Send statements to DBMS
            PreparedStatement orderEntry_pst = conn.prepareStatement(orderEntry_sqlStatement);
            int orderEntry_ResultSet = orderEntry_pst.executeUpdate();
            // send to order_history table

        } catch (Exception q) {
            JOptionPane.showMessageDialog(null, q);
        }
    }
}
