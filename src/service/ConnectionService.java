package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.ExecutionException;

public class ConnectionService {

    private static Connection con = null;

    public static Connection getConnection() {
        if(con == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "ankit");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return con;
        }
        else {
            return con;
        }
    }

}
