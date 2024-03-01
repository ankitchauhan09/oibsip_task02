package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

public class TransactionService {

    public static void recordTransaction(Long accountNumber, String type, String description, Connection con){
        try{
            Long randomNumber = Math.abs(new Random().nextLong()%100000000L);
            String transactionID = "TXN"+randomNumber;
            PreparedStatement ps = con.prepareStatement("insert into transaction_history(type, account_number, description, transactionId) values(?, ?, ?, ?);");
            ps.setString(1, type);
            ps.setLong(2, accountNumber);
            ps.setString(3, description);
            ps.setString(4, transactionID);
            int status = ps.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void showTransactionHistory(Long acountNumber, Connection con){
        try{

            PreparedStatement ps = con.prepareStatement("select * from transaction_history where account_number = ?");
            ps.setLong(1, acountNumber);
            ResultSet rs = ps.executeQuery();
            System.out.println("\n\n\t\t-------------- Transaction History ---------------");
            while(rs.next()){
                System.out.println(rs.getString("transactionID") + " : " + rs.getString("type") + " " + rs.getString("description") + " at " + rs.getTimestamp("date"));
            }
            System.out.println("\n\n-------------------------------------------------------\n\n");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
