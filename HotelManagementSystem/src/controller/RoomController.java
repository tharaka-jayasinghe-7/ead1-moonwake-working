package controller;
import java.sql.*;
import java.util.ArrayList;
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
import model.RoomModel;




public class RoomController {
    
    Connection con ;
    PreparedStatement pst;
    DefaultTableModel d;
    
    public void connect(RoomModel rooModel1){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_hotel", "root", "");
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public void autoID(RoomModel roomModel1 ,  JLabel lbl_id) {
        try {
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select MAX(room_id) from tbl_room");
            rs.next();
            rs.getString("MAX(room_id)");
            
            if(rs.getString("MAX(room_id)") == null){
                lbl_id.setText("R0001");
            }
            else{
                long id = Long.parseLong(rs.getString("MAX(room_id)").substring(2,rs.getString("MAX(room_id)").length()));
                id++;
                lbl_id.setText("R0" + String.format("%03d", id));
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addRoom(RoomModel roomModel1 , String id){
        try {
            
            try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_hotel", "root", "");
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            pst = con.prepareStatement("INSERT INTO tbl_room VALUES (?,?,?,?)");
            pst.setString(1, id );
            pst.setString(2, roomModel1.getRoom_type());
            pst.setString(3, roomModel1.getBed_type());
            pst.setInt(4, roomModel1.getRoom_amount());
            
            pst.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Room added.", "Successfull", JOptionPane.INFORMATION_MESSAGE);
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadRoom(JTable table, RoomModel roomModel1) {
        try {
            pst = con.prepareStatement("SELECT * FROM tbl_room");
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
    
    public void viewRoom(RoomModel rModel, JTable table, JLabel id, JComboBox rType, JComboBox bType, JTextField amount){
        d = (DefaultTableModel)table.getModel();
        int selectIndex = table.getSelectedRow();
        rModel.setRoom_id(d.getValueAt(selectIndex, 0).toString());
        rModel.setRoom_type(d.getValueAt(selectIndex, 1).toString());
        rModel.setBed_type(d.getValueAt(selectIndex, 2).toString());

        int roomAmount = Integer.parseInt(d.getValueAt(selectIndex, 3).toString());
        rModel.setRoom_amount(roomAmount);
        
        
        id.setText(rModel.getRoom_id());
        rType.setSelectedItem(rModel.getRoom_type());
        bType.setSelectedItem(rModel.getBed_type());
        amount.setText(String.valueOf(rModel.getRoom_amount()));
        
        
 
    }
    
    public void editRoom(RoomModel roomModel1, String id , JComboBox rType, JComboBox bType, JTextField amount) {
        try {
       
        
            pst = con.prepareStatement("UPDATE tbl_room SET room_type = ?, bed_type = ?, room_amount = ? WHERE room_id = ?");
            pst.setString(1, rType.getSelectedItem().toString());
            pst.setString(2, bType.getSelectedItem().toString());
            pst.setInt(3, Integer.parseInt(amount.getText()));
            pst.setString(4, id); // Specify the room_id to identify the room to edit

            int rowsAffected = pst.executeUpdate();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Room Edited.", "Successful", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Room not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        } catch (SQLException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteRoom(RoomModel roomModel1, String id) {
        try {
       
        
            pst = con.prepareStatement("DELETE FROM tbl_room WHERE room_id = ?");
            
            pst.setString(1, id); // Specify the room_id to identify the room to edit

            int rowsAffected = pst.executeUpdate();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Room Deleted.", "Successful", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Room not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        } catch (SQLException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<RoomModel> getRoomsByType(String rType, String bedType){
        ArrayList<RoomModel> rooms = new ArrayList<RoomModel>();
        
        try {
            pst = con.prepareStatement("SELECT * FROM tbl_room WHERE room_type = ? AND bed_type =?");
            pst.setString(1, rType);
            pst.setString(2, bedType);
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()){
                RoomModel rModel = new RoomModel();
                rModel.setRoom_id(rs.getString("room_id"));
                rModel.setRoom_amount(rs.getInt("room_amount"));
                
                rooms.add(rModel);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rooms;
    }


}
