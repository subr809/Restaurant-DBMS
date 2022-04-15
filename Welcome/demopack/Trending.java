package demopack;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Trending {
    private JTable upTable;
    private JButton backButton;
    private JTable downTable;
    public JPanel trendingPanel;

    public Trending() {
        Connection conn = master.getConnection();
        connect(conn);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                master.managerFrame.setVisible(true);
                master.trendingFrame.setVisible(false);
            }
        });
    }
    public void connect(Connection conn) {
        try {
            //create a statement object
            Statement stmt = conn.createStatement();
            DefaultTableModel tb1Model = new DefaultTableModel(new String[]{"Recommended", "Total"}, 0);
            DefaultTableModel tb2Model = new DefaultTableModel(new String[]{"Recommended", "Total"}, 0);
            //create an SQL statement
            String TopE_sqlStatement = "SELECT recommended, count(recommended) as total " +
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
                    "   ORDER  BY count(*) desc Limit 2";  // Top 2

            String TopS_sqlStatement = "SELECT recommended, count(recommended) as total " +
                    "   FROM   order_history , unnest(order_entry) AS recommended " +
                    "   WHERE date between '2020-12-01' and '2020-12-31' " +
                    "   and (recommended like '%S1%' " +
                    "   or recommended like '%S2%' " +
                    "   or recommended like '%S3%' " +
                    "   or recommended like '%S4%') " +
                    "   Group by 1 " +
                    "   ORDER  BY count(*) desc Limit 2";

            String TopB_sqlStatement = "SELECT recommended, count(recommended) as total" +
                    "   FROM   order_history , unnest(order_entry) AS recommended" +
                    "   WHERE date between '2020-12-01' and '2020-12-31'" +
                    "   and (recommended like '%B1%'" +
                    "   or recommended like '%B2%'" +
                    "   or recommended like '%B3%'" +
                    "   or recommended like '%B4%'" +
                    "   or recommended like '%B5%')" +
                    "   Group by 1" +
                    "   ORDER  BY count(*) desc Limit 2;";
            String DownE_sqlStatement = "SELECT recommended, count(recommended) as total " +
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
                    "   ORDER  BY count(*) asc Limit 2";  // Top 2

            String DownS_sqlStatement = "SELECT recommended, count(recommended) as total " +
                    "   FROM   order_history , unnest(order_entry) AS recommended " +
                    "   WHERE date between '2020-12-01' and '2020-12-31' " +
                    "   and (recommended like '%S1%' " +
                    "   or recommended like '%S2%' " +
                    "   or recommended like '%S3%' " +
                    "   or recommended like '%S4%') " +
                    "   Group by 1 " +
                    "   ORDER  BY count(*) asc Limit 2";

            String DownB_sqlStatement = "SELECT recommended, count(recommended) as total" +
                    "   FROM   order_history , unnest(order_entry) AS recommended" +
                    "   WHERE date between '2020-12-01' and '2020-12-31'" +
                    "   and (recommended like '%B1%'" +
                    "   or recommended like '%B2%'" +
                    "   or recommended like '%B3%'" +
                    "   or recommended like '%B4%'" +
                    "   or recommended like '%B5%')" +
                    "   Group by 1" +
                    "   ORDER  BY count(*) asc Limit 2;";
            //send statement to DBMS
            PreparedStatement ps_topE = conn.prepareStatement(TopE_sqlStatement);
            PreparedStatement ps_topS = conn.prepareStatement(TopS_sqlStatement);
            PreparedStatement ps_topB = conn.prepareStatement(TopB_sqlStatement);
            PreparedStatement ps_downE = conn.prepareStatement(DownE_sqlStatement);
            PreparedStatement ps_downS = conn.prepareStatement(DownS_sqlStatement);
            PreparedStatement ps_downB = conn.prepareStatement(DownB_sqlStatement);
            ResultSet rs_topE = ps_topE.executeQuery();
            ResultSet rs_topS = ps_topS.executeQuery();
            ResultSet rs_topB = ps_topB.executeQuery();
            ResultSet rs_downE = ps_downE.executeQuery();
            ResultSet rs_downS = ps_downS.executeQuery();
            ResultSet rs_downB = ps_downB.executeQuery();
            // Creating trending up table
            while (rs_topB.next() && rs_topE.next() && rs_topS.next()) {
                String recommendedIDE = rs_topE.getString("recommended");
                String total_purchaseE = String.valueOf(rs_topE.getInt("total"));
                tb1Model.addRow(new Object[] {recommendedIDE, total_purchaseE});
                String recommendedIDS = rs_topS.getString("recommended");
                String total_purchaseS = String.valueOf(rs_topS.getInt("total"));
                tb1Model.addRow(new Object[] {recommendedIDS, total_purchaseS});
                String recommendedIDB = rs_topB.getString("recommended");
                String total_purchaseB = String.valueOf(rs_topB.getInt("total"));
                tb1Model.addRow(new Object[] {recommendedIDB, total_purchaseB});
            }
            upTable.setModel(tb1Model);
            upTable.setVisible(true);
            // Creating trending down table
            while (rs_downB.next() && rs_downE.next() && rs_downS.next()) {
                String downrecIDE = rs_downE.getString("recommended");
                String downpriceE = String.valueOf(rs_downE.getInt("total"));
                tb2Model.addRow(new Object[] {downrecIDE, downpriceE});
                String downrecIDS = rs_downS.getString("recommended");
                String downpriceS = String.valueOf(rs_downS.getInt("total"));
                tb2Model.addRow(new Object[] {downrecIDS, downpriceS});
                String downrecIDB = rs_downB.getString("recommended");
                String downB = String.valueOf(rs_downB.getInt("total"));
                tb2Model.addRow(new Object[] {downrecIDB, downB});
            }
            downTable.setModel(tb2Model);
            downTable.setVisible(true);
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


