package controller;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.CustomerModel;

public class CustomerController {
    
    Connection con ;
    PreparedStatement pst;
    DefaultTableModel d;
    
    public void connect(CustomerModel customerModel1){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_hotel", "root", "");
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public void addCustomer(CustomerModel customerModel1){
        try {
            
            try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_hotel", "root", "");
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            pst = con.prepareStatement("INSERT INTO tbl_customer VALUES (?,?,?,?,?,?)");
            pst.setString(1, customerModel1.getCustomer_nic() );
            pst.setString(2, customerModel1.getCustomer_name());
            pst.setString(3, customerModel1.getCustomer_adress());
            pst.setInt(4, customerModel1.getCustomer_mobile());
            pst.setString(5, customerModel1.getCustomer_gender());
            pst.setString(6, customerModel1.getCustomer_email());
            
            pst.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Customer added.", "Successfull", JOptionPane.INFORMATION_MESSAGE);
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadCustomer(JTable table, CustomerModel customerModel1) {
        try {
            pst = con.prepareStatement("SELECT * FROM tbl_customer");
            ResultSet rs = pst.executeQuery();

            ResultSetMetaData rsd = rs.getMetaData();
            int c = rsd.getColumnCount();

            DefaultTableModel d = (DefaultTableModel) table.getModel(); // Get the JTable's model

            while (rs.next()) {
                Vector<Object> rowData = new Vector<>();
                for (int i = 1; i <= c; i++) {
                    rowData.add(rs.getString(i)); // Retrieve data from the ResultSet
                }
                d.addRow(rowData); // Add the row to the table model
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public void viewCustomer(CustomerModel cusModel, JTable table, JTextField nic, JTextField name, JTextField address, JTextField mobile, JComboBox gender, JTextField email){
        d = (DefaultTableModel)table.getModel();
        int selectIndex = table.getSelectedRow();
        cusModel.setCustomer_nic(d.getValueAt(selectIndex, 0).toString());
        cusModel.setCustomer_name(d.getValueAt(selectIndex, 1).toString());
        cusModel.setCustomer_adress(d.getValueAt(selectIndex, 2).toString());
        
        int mobileNum = Integer.parseInt(d.getValueAt(selectIndex, 3).toString());
        cusModel.setCustomer_mobile(mobileNum);
        
        cusModel.setCustomer_gender(d.getValueAt(selectIndex, 4).toString());
        cusModel.setCustomer_email(d.getValueAt(selectIndex, 5).toString());
        
        nic.setText(cusModel.getCustomer_nic());
        name.setText(cusModel.getCustomer_name());
        address.setText(cusModel.getCustomer_adress());
        mobile.setText(String.valueOf(cusModel.getCustomer_mobile()));
        gender.setSelectedItem(cusModel.getCustomer_gender());
        email.setText(cusModel.getCustomer_email());
                    
    }
    
    public void editCustomer(CustomerModel cusModel, String id ,JTextField name, JTextField address, JTextField mobile, JComboBox gender, JTextField email) {
        try {
       
        
            pst = con.prepareStatement("UPDATE tbl_customer SET customer_name =?, customer_address=?, customer_mobile=?, customer_gender=?, customer_email= ? WHERE customer_nic = ?");
            pst.setString(1, name.getText());
            pst.setString(2, address.getText());
            pst.setInt(3, Integer.parseInt(mobile.getText()));
            pst.setString(4, gender.getSelectedItem().toString());
            pst.setString(5, email.getText());
            pst.setString(6, id);

            int rowsAffected = pst.executeUpdate();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Customer Edited.", "Successful", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Customer not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        } catch (SQLException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteCustomer(CustomerModel cusModel, String id) {
        try {
             
            pst = con.prepareStatement("DELETE FROM tbl_customer WHERE customer_nic = ?");
            
            pst.setString(1, id); // Specify the room_id to identify the room to edit

            int rowsAffected = pst.executeUpdate();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Customer Deleted.", "Successful", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Customer not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        } catch (SQLException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
