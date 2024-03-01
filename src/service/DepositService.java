package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DepositService {

    public static void depositService(Long accountNumber, Connection con) {
        try {
            PreparedStatement ps = con.prepareStatement("select balance from user where account_number = ?");
            ps.setLong(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            Long totalAmount = 0L;
            while (rs.next()) {
                totalAmount = rs.getLong("balance");
            }

            System.out.println("\nAvailable Balance : " + totalAmount + "\n\n");
            System.out.println("Enter the amount you want to deposit : ");
            Long amount = new java.util.Scanner(System.in).nextLong();

            Long newAmount = totalAmount + amount;
            PreparedStatement ps1 = con.prepareStatement("update user set balance = ? where account_number = ? ");
            ps1.setLong(1, newAmount);
            ps1.setLong(2, accountNumber);
            int status = ps1.executeUpdate();
            if (status > 0) {
                System.out.println("Amount credited successfully.....");
                TransactionService.recordTransaction(accountNumber, "CRE", "Credited "+amount+" rs.", con);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
