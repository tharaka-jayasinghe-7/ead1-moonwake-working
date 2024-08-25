
package controller;

import com.toedter.calendar.JDateChooser;
import java.awt.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.ReservationModel;
import model.CustomerModel;
import model.RoomModel;



public class ReservationController {
    
    Connection con ;
    PreparedStatement pst;
    DefaultTableModel d;
    
        public void connect(ReservationModel reModel1){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_hotel", "root", "");
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public void autoID(ReservationModel reModel1 ,  JLabel lbl_id) {
        try {
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select MAX(reservation_id) from tbl_reservation");
            rs.next();
            rs.getString("MAX(reservation_id)");
            
            if(rs.getString("MAX(reservation_id)") == null){
                lbl_id.setText("RE0001");
            }
            else{
                long id = Long.parseLong(rs.getString("MAX(reservation_id)").substring(2,rs.getString("MAX(reservation_id)").length()));
                id++;
                lbl_id.setText("RE0" + String.format("%03d", id));
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
        
    public CustomerModel fetchData(String user_id){
        
        CustomerModel cModel1 = new CustomerModel();
        
        try {
            pst = con.prepareStatement("SELECT * FROM tbl_customer Where customer_nic = ?");
            pst.setString(1, user_id);
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()){
                cModel1.setCustomer_name(rs.getString("customer_name"));
                cModel1.setCustomer_adress(rs.getString("customer_address"));
                cModel1.setCustomer_mobile(rs.getInt("customer_mobile"));
                cModel1.setCustomer_email(rs.getString("customer_email"));

            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cModel1;
        
    }   
    
  
    public ReservationModel loadRoomType(JComboBox<String> rtype) {
    ReservationModel reModel1 = new ReservationModel();
    Set<String> uniqueItems = new HashSet<>(); // Use a Set to store unique items

    try {
        pst = con.prepareStatement("SELECT DISTINCT room_type FROM tbl_room"); // Use DISTINCT to retrieve unique room types
        ResultSet rs = pst.executeQuery();

        rtype.removeAllItems();

        while (rs.next()) {
            String roomType = rs.getString(1);

            // Check if the room type is not already in the set
            if (!uniqueItems.contains(roomType)) {
                rtype.addItem(roomType);
                uniqueItems.add(roomType);
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
    }

    return reModel1;
    }

    public ReservationModel loadBedType(JComboBox<String> btype) {
    ReservationModel reModel1 = new ReservationModel();
    Set<String> uniqueItems = new HashSet<>(); // Use a Set to store unique items

    try {
        pst = con.prepareStatement("SELECT DISTINCT bed_type FROM tbl_room"); // Use DISTINCT to retrieve unique room types
        ResultSet rs = pst.executeQuery();

        btype.removeAllItems();

        while (rs.next()) {
            String bedType = rs.getString("bed_type");

            // Check if the room type is not already in the set
            if (!uniqueItems.contains(bedType)) {
                btype.addItem(bedType);
                uniqueItems.add(bedType);
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
    }

    return reModel1;
    }
//
//    public ReservationModel loadRoomNO(JComboBox<String> rNO, String rType) {
//    ReservationModel reModel1 = new ReservationModel();    
//    
//    try {
//        pst = con.prepareStatement("SELECT * FROM tbl_room WHERE room_type = ?");
//        pst.setString(1, rType); // Set the value of the parameter to rType
//        ResultSet rs = pst.executeQuery();
//        rNO.removeAllItems();
//        while (rs.next()) {                
//            rNO.addItem(rs.getString("room_id"));
//        }
//        
//        return reModel1;
//    } catch (SQLException ex) {
//        Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
//    }
//    
//    return reModel1;
//}

    public RoomModel getRoomNO(String rtype, String btype, JComboBox rNO){
        RoomModel rmModel = new RoomModel();
        
        try {
            pst = con.prepareStatement("SELECT * FROM tbl_room WHERE room_type = ? AND bed_type =?");
            pst.setString(1, rtype);
            pst.setString(2, btype);
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()){
                rNO.addItem(rs.getString("room_id"));        
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rmModel;
    }

        public void addReservation(String resID, String resNIC, String resRoomID, String checkinDate, String checkoutDate, int resAmount){
        try {
            
            try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_hotel", "root", "");
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            pst = con.prepareStatement("INSERT INTO tbl_reservation VALUES (?,?,?,?,?,?)");
            pst.setString(1, resID);
            pst.setString(2, resNIC);
            pst.setString(3, resRoomID);
            pst.setString(4,checkinDate);
            pst.setString(5, checkoutDate);
            pst.setInt(6, resAmount);
            
            pst.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Reservation added.", "Successfull", JOptionPane.INFORMATION_MESSAGE);
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        public ArrayList<ReservationModel> getReservations(){
            
        ArrayList<ReservationModel> list = new ArrayList<ReservationModel>();
            try {
                pst = con.prepareStatement("SELECT * FROM tbl_reservation");

                ResultSet rs = pst.executeQuery();



                while (rs.next()) {
                     ReservationModel rModel = new ReservationModel();
                     rModel.setId(rs.getString("reservation_id"));
                    rModel.setCustomer_nic(rs.getString("customer_nic"));
                    rModel.setRoom_roomID(rs.getString("room_id"));
                    rModel.setCheckin(rs.getDate("checkin_date"));
                    rModel.setCheckout(rs.getDate("checkout_date"));
                     rModel.setAmount(rs.getInt("reservation_amount"));

                     list.add(rModel);
                }

            } catch (SQLException ex) {
                Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return list;
        }
        
        public void loadReservation(JTable table, ReservationModel resModel1) {
        try {
            pst = con.prepareStatement("SELECT * FROM tbl_reservation");
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

        public void viewReservation(ReservationModel resModel, JTable table,JLabel resId, JTextField nic,  JComboBox roomID, JTextField amount, JDateChooser dateCheckin, JDateChooser datecheckout){
        try {
            d = (DefaultTableModel)table.getModel();
            int selectIndex = table.getSelectedRow();
            resModel.setId(d.getValueAt(selectIndex, 0).toString());
            resModel.setCustomer_nic(d.getValueAt(selectIndex, 1).toString());
            resModel.setRoom_roomID(d.getValueAt(selectIndex, 2).toString());
            resModel.setCheckin(new SimpleDateFormat("yyyy-MM-dd").parse(d.getValueAt(selectIndex, 3).toString()));
            resModel.setCheckout(new SimpleDateFormat("yyyy-MM-dd").parse(d.getValueAt(selectIndex, 4).toString()));
            int amount1 = Integer.parseInt(d.getValueAt(selectIndex, 5).toString());
            resModel.setAmount(amount1);
            
            
            resId.setText(resModel.getId());
            nic.setText(resModel.getCustomer_nic());
            //        roomType.setSelectedItem(resModel.getReservation_roomType());
            roomID.setSelectedItem(resModel.getRoom_roomID());  
            //        bedType.setSelectedItem(resModel.getReservation_bedType());
            amount.setText(String.valueOf(resModel.getAmount()));
            dateCheckin.setDate(resModel.getCheckin());
            datecheckout.setDate(resModel.getCheckout());
        } catch (ParseException ex) {
            Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        public ReservationModel getReservationDataAll(String reID){
//            SELECT
//            cus.customer_name,
//            cus.customer_nic AS cusId,
//            cus.customer_address,
//            cus.customer_mobile,
//            cus.customer_email,
//            res.checkin_date,
//            res.checkout_date,
//            r.room_type,
//            r.room_id AS rId,
//            r.bed_type
//            FROM
//            tbl_reservation AS res
//            JOIN
//            tbl_customer AS cus ON res.customer_nic = cus.customer_nic
//            JOIN
//            tbl_room AS r ON res.room_id = r.room_id
//            WHERE
//            res.reservation_id = reID;
            ReservationModel reModel1 = new ReservationModel();
            try {
                
            String qString = "SELECT\n" +
"            res.reservation_id,\n" +
"            cus.customer_name,\n" +
"            cus.customer_nic AS cusId,\n" +
"            cus.customer_address,\n" +
"            cus.customer_mobile,\n" +
"            cus.customer_email,\n" +
"            res.checkin_date,\n" +
"            res.checkout_date,\n" +
"            res.reservation_amount,\n" +
"            r.room_type,\n" +
"            r.room_id AS rId,\n" +
"            r.bed_type\n" +
"            FROM\n" +
"            tbl_reservation AS res\n" +
"            JOIN\n" +
"            tbl_customer AS cus ON res.customer_nic = cus.customer_nic\n" +
"            JOIN\n" +
"            tbl_room AS r ON res.room_id = r.room_id\n" +
"            WHERE\n" +
"            res.reservation_id = ?";
            
            pst = con.prepareStatement(qString);
            pst.setString(1, reID);
            
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()){
                var x = rs.getString("res.reservation_id");
                reModel1.setId(rs.getString("res.reservation_id"));
                reModel1.setCustomer_name(rs.getString("cus.customer_name"));
                reModel1.setCustomer_nic(rs.getString("cusId"));
                reModel1.setCustomer_address(rs.getString("cus.customer_address"));
                reModel1.setCustomer_mobile(rs.getInt("cus.customer_mobile"));
                reModel1.setCheckin(rs.getDate("res.checkin_date"));
                reModel1.setCheckout(rs.getDate("res.checkout_date"));
                reModel1.setRoom_roomType(rs.getString("r.room_type"));
                reModel1.setAmount(rs.getInt("res.reservation_amount"));
                reModel1.setRoom_bedType(rs.getString("r.bed_type"));
                reModel1.setRoom_roomID(rs.getString("rId"));
                reModel1.setCustomer_email(rs.getString("cus.customer_email"));

            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            return reModel1;
            
        }
        
        public void editReservation(ReservationModel resModel) {
        
        try {
            
            try {
                pst = con.prepareStatement("UPDATE tbl_reservation SET customer_nic=?, room_id=?, checkin_date=?, checkout_date= ?, reservation_amount =? WHERE reservation_id = ?");
            } catch (SQLException ex) {
                Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            pst.setString(1, resModel.getCustomer_nic());
            pst.setString(2, resModel.getRoom_roomID());
            pst.setString(3, resModel.getCheckin().toString());
            pst.setString(4, resModel.getCheckout().toString());
            pst.setInt(5, resModel.getAmount());
            pst.setString(6, resModel.getId());
            
            int rowsAffected = pst.executeUpdate();
            
            if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Reservation Edited.", "Successful", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Reservation not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
        }  
        
        
        
        public void deleteReservation(ReservationModel reModel, String id) {
        try {
       
        
            pst = con.prepareStatement("DELETE FROM tbl_reservation WHERE reservation_id = ?");
            
            pst.setString(1, id); // Specify the room_id to identify the room to edit

            int rowsAffected = pst.executeUpdate();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Reservation Deleted.", "Successful", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Reservation not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        } catch (SQLException ex) {
            Logger.getLogger(RoomController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        
}

    

