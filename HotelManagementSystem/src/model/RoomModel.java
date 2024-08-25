
package model;

public class RoomModel {
    private String room_id ;
    private String room_type;
    private String bed_type;
    private int room_amount;

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getBed_type() {
        return bed_type;
    }

    public void setBed_type(String bed_type) {
        this.bed_type = bed_type;
    }

    public int getRoom_amount() {
        return room_amount;
    }

    public void setRoom_amount(int room_amount) {
        this.room_amount = room_amount;
    }
    
    
}


