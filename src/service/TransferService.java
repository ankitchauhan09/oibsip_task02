package service;

import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TransferService {

    public static void transferService(Long accountNumber, Connection con){
        try{
            //getting account balance of sender
            PreparedStatement sender = con.prepareStatement("select * from user where account_number = ?");
            sender.setLong(1, accountNumber);
            ResultSet rsSender = sender.executeQuery();
            Long senderAccBalance = 0L;
            while(rsSender.next()){
                senderAccBalance = rsSender.getLong("balance");
            }

            //validating the receivers account number
            System.out.println("Enter the account number of the second person  : ");
            Long secondPersonAccNo = new java.util.Scanner(System.in).nextLong();

            PreparedStatement receiver = con.prepareStatement("select * from user where account_number = ?");
            receiver.setLong(1, secondPersonAccNo);
            ResultSet rs = receiver.executeQuery();
            if(rs.next()){
                Long balance = rs.getLong("balance");
                System.out.println("Enter the amount you want to send : ");
                Long amountToSend = new java.util.Scanner(System.in).nextLong();

                if(amountToSend <= senderAccBalance){
                    long finalAmnt = balance + amountToSend;
                    long amountAfterSending = senderAccBalance - amountToSend;
                    PreparedStatement sendingAmount = con.prepareStatement("update user set balance = ? where account_number = ?");
                    sendingAmount.setLong(1, finalAmnt);
                    sendingAmount.setLong(2, secondPersonAccNo);

                    PreparedStatement balanceUpdate = con.prepareStatement("update user set balance = ? where account_number = ?");
                    balanceUpdate.setLong(1, amountAfterSending);
                    balanceUpdate.setLong(2, accountNumber);

                    int status = sendingAmount.executeUpdate();
                    int status2 = balanceUpdate.executeUpdate();
                    if(status > 0 && status2 > 0){
                        System.out.println("Amount send succesfully to account number : " + secondPersonAccNo);
                        TransactionService.recordTransaction(accountNumber, "DEB", "Transferred "+amountToSend+" rs.", con);
                        TransactionService.recordTransaction(secondPersonAccNo, "CRE", "Received "+amountToSend+" rs.", con);
                    }
                    else{
                        System.out.println("SOME ERROR HAS OCCURRED");
                    }
                }
                else{
                    System.out.println("INSUFFICIENT BALANCE");
                }

            }
            else{
                System.out.println("No user with such account number....");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
