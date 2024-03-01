package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbService {
    private Connection con;

    public boolean saveUserDetails(String accountHolderName, long accountNumber, Integer accountPassword, Connection con) {

        boolean returnValue = false;
        try {
            PreparedStatement ps = con.prepareStatement("insert into user(name, account_number, account_password) values(?, ?, ?)");
            ps.setString(1, accountHolderName);
            ps.setLong(2, accountNumber);
            ps.setInt(3, accountPassword);
            int status = ps.executeUpdate();
            if(status != 0){
                returnValue = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return returnValue;
    }

    public boolean validateUser(Long userAccountNumber, Integer userPassword, Connection con) {
        boolean isValid = false;

        try{
            PreparedStatement ps = con.prepareStatement("select * from user where account_number = ? and account_password = ?");
            ps.setLong(1, userAccountNumber);
            ps.setInt(2, userPassword);
            ResultSet rs = ps.executeQuery();
            isValid = rs.next();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return isValid;
    }
}
