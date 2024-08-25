
package model;

import java.util.Date;


public class ReservationModel {
    
    private String id;
    private String  customer_name;
    private String customer_nic;
    private String customer_address;
    private int customer_mobile;
    private Date checkin;
    private Date checkout;
    private String room_roomType;
    private String customer_email;
    private String room_bedType;
    private String room_roomID;
    private int amount;

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_nic() {
        return customer_nic;
    }

    public void setCustomer_nic(String customer_nic) {
        this.customer_nic = customer_nic;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public int getCustomer_mobile() {
        return customer_mobile;
    }

    public void setCustomer_mobile(int customer_mobile) {
        this.customer_mobile = customer_mobile;
    }

    public Date getCheckin() {
        return checkin;
    }

    public void setCheckin(Date checkin) {
        this.checkin = checkin;
    }

    public Date getCheckout() {
        return checkout;
    }

    public void setCheckout(Date checkout) {
        this.checkout = checkout;
    }

    public String getRoom_roomType() {
        return room_roomType;
    }

    public void setRoom_roomType(String room_roomType) {
        this.room_roomType = room_roomType;
    }

//    public String getReservation_roomNumber() {
//        return reservation_roomNumber;
//    }
//
//    public void setReservation_roomNumber(String reservation_roomNumber) {
//        this.reservation_roomNumber = reservation_roomNumber;
//    }

    public String getRoom_bedType() {
        return room_bedType;
    }

    public void setRoom_bedType(String room_bedType) {
        this.room_bedType = room_bedType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getRoom_roomID() {
        return room_roomID;
    }

    public void setRoom_roomID(String room_roomID) {
        this.room_roomID = room_roomID;
    }
    
    
    
}
