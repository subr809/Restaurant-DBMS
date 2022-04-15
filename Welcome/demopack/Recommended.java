package demopack;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Recommended {
    GridBagConstraints cons;
    public JPanel recommendedPanel;
    private JLabel Intro;
    private JPanel recommendedHolder;
    private JPanel sideHolder;
    private JPanel quantityHolder;
    private JButton backButton;
    private JTable JTable1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Recommended");
        frame.setContentPane(new Recommended().recommendedPanel);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Recommended() {
        Connection conn = master.getConnection();
       // createTable();
        connect(conn);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                master.customerFrame.setVisible(true);
                master.recommendedFrame.setVisible(false);
            }
        });
    }

    public void connect(Connection conn) {
            try {
                //create a statement object
                Statement stmt = conn.createStatement();
                DefaultTableModel tb1Model = new DefaultTableModel(new String[]{"food_name", "total"}, 0);
                //create an SQL statement
                String TopE_sqlStatement = "SELECT menu.food_name, total FROM (SELECT recommended, count(recommended) as total " +
                        "   FROM order_history , unnest(order_entry) AS recommended " +
                        "   WHERE date between '2020-12-01' and '2020-12-31' " +
                        "   and (recommended like '%E1%' " +
                        "   or recommended like '%E2%' " +
                        "   or recommended like '%E3%' " +
                        "   or recommended like '%E4%' " +
                        "   or recommended like '%E5%' " +
                        "   or recommended like '%E6%' " +
                        "   or recommended like '%E7%') " +
                        "   Group by 1 " +
                        "   ORDER  BY count(*) desc Limit 2 ) as foo inner join menu on recommended = menu.menu_id";  // Top 2

                String TopS_sqlStatement = "SELECT menu.food_name, total FROM (SELECT recommended, count(recommended) as total " +
                        "   FROM   order_history , unnest(order_entry) AS recommended " +
                        "   WHERE date between '2020-12-01' and '2020-12-31' " +
                        "   and (recommended like '%S1%' " +
                        "   or recommended like '%S2%' " +
                        "   or recommended like '%S3%' " +
                        "   or recommended like '%S4%') " +
                        "   Group by 1 " +
                        "   ORDER  BY count(*) desc Limit 2 ) as foo inner join menu on recommended = menu.menu_id";

                String TopB_sqlStatement = "SELECT menu.food_name, total FROM (SELECT recommended, count(recommended) as total " +
                        "   FROM   order_history , unnest(order_entry) AS recommended" +
                        "   WHERE date between '2020-12-01' and '2020-12-31'" +
                        "   and (recommended like '%B1%'" +
                        "   or recommended like '%B2%'" +
                        "   or recommended like '%B3%'" +
                        "   or recommended like '%B4%'" +
                        "   or recommended like '%B5%')" +
                        "   Group by 1" +
                        "   ORDER  BY count(*) desc Limit 2 ) as foo inner join menu on recommended = menu.menu_id";
                //send statement to DBMS
                PreparedStatement ps_topE = conn.prepareStatement(TopE_sqlStatement);
                PreparedStatement ps_topS = conn.prepareStatement(TopS_sqlStatement);
                PreparedStatement ps_topB = conn.prepareStatement(TopB_sqlStatement);
                ResultSet rs_topE = ps_topE.executeQuery();
                ResultSet rs_topS = ps_topS.executeQuery();
                ResultSet rs_topB = ps_topB.executeQuery();

                while (rs_topB.next() && rs_topE.next() && rs_topS.next()) {
                    String recommendedIDE = rs_topE.getString("food_name");
                    String total_purchaseE = String.valueOf(rs_topE.getInt("total"));
                    tb1Model.addRow(new Object[] {recommendedIDE, total_purchaseE});
                    String recommendedIDS = rs_topS.getString("food_name");
                    String total_purchaseS = String.valueOf(rs_topS.getInt("total"));
                    tb1Model.addRow(new Object[] {recommendedIDS, total_purchaseS});
                    String recommendedIDB = rs_topB.getString("food_name");
                    String total_purchaseB = String.valueOf(rs_topB.getInt("total"));
                    tb1Model.addRow(new Object[] {recommendedIDB, total_purchaseB});
                }
                JTable1.setModel(tb1Model);
                JTable1.setVisible(true);
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Recommended Error accessing Database.");
        }
        try {
            conn.close();
            //JOptionPane.showMessageDialog(null, "Connection Closed.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connection NOT Closed.");
        }//end try catch
    }
}
