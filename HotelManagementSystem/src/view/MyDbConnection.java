
package view;

import java.sql.Connection;
import java.sql.DriverManager;

public class MyDbConnection {
        private static Connection con;
    public static Connection getMyDBConnection(){
        try{
            String dbpath = "jdbc:mysql://localhost/db_hotel";
            con = DriverManager.getConnection(dbpath, "root", "");
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        return con;
        
    }
}
