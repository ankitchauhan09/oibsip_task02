
import service.*;

import java.sql.Connection;
import java.util.Random;
import java.util.Scanner;

public class Main {

    DbService dbService = new DbService();
    static Scanner scn = new Scanner(System.in);


    public static void main(String[] args) {

        Connection con = ConnectionService.getConnection();

        int optionChoice = 0;

        do {
            System.out.println("\n\n\t\tWelcome to Ankit Chauhan's ATM\t\t");
            System.out.println("\t1. Open New Account");
            System.out.println("\t2. Withdraw Money");
            System.out.println("\t3. Deposit Money");
            System.out.println("\t4. Transfer Money");
            System.out.println("\t5. Transactions History");
            System.out.println("\t6 or any other key to exit...");
            optionChoice = scn.nextInt();
            scn.nextLine();
            switch (optionChoice) {
                case 1:
                    new Main().openAccount(con);
                    break;
                case 2:
                    new Main().withdrawMoney(con);
                    break;
                case 3:
                    new Main().depositMoney(con);
                    break;
                case 4:
                    new Main().transferMoney(con);
                    break;
                case 5:
                    new Main().transactionsHistory(con);
                    break;
                default:
                    break;
            }


        }
        while (optionChoice >= 1 && optionChoice <= 5);

    }

    private void transactionsHistory(Connection con) {
        System.out.println("Enter account number : ");
        Long userAcountNumber = scn.nextLong();
        scn.nextLine();
        System.out.println("Enter your password : ");
        Integer userPassword = scn.nextInt();
        if (dbService.validateUser(userAcountNumber, userPassword, con)) {
            TransactionService.showTransactionHistory(userAcountNumber, con);
        } else {
            System.out.println("INVALID ACC NO OR PASSWORD");
        }
    }

    private void transferMoney(Connection con) {
        System.out.println("Enter account number : ");
        Long userAcountNumber = scn.nextLong();
        scn.nextLine();
        System.out.println("Enter your password : ");
        Integer userPassword = scn.nextInt();

        if (dbService.validateUser(userAcountNumber, userPassword, con)) {
            TransferService.transferService(userAcountNumber, con);
        } else {
            System.out.println("INVALID ACC NO OR PASSWORD");
        }
    }

    private void depositMoney(Connection con) {
        System.out.println("Enter account number : ");
        Long userAcountNumber = scn.nextLong();
        scn.nextLine();
        System.out.println("Enter your password : ");
        Integer userPassword = scn.nextInt();

        if (dbService.validateUser(userAcountNumber, userPassword, con)) {
            DepositService.depositService(userAcountNumber, con);
        } else {
            System.out.println("INVALID ACC NO OR PASSWORD");
        }
    }

    private void withdrawMoney(Connection con) {
        System.out.println("Enter account number : ");
        Long userAcountNumber = scn.nextLong();
        scn.nextLine();
        System.out.println("Enter your password : ");
        Integer userPassword = scn.nextInt();

        if (dbService.validateUser(userAcountNumber, userPassword, con)) {
            WithdrawService.withdrawMoney(userAcountNumber, con);
        } else {
            System.out.println("INVALID ACC NO OR PASSWORD");
        }
    }

    private void openAccount(Connection con) {
        Random random = new Random();
        String accountHolderName;
        System.out.println("Enter the account holders name : ");
        accountHolderName = scn.nextLine();
        System.out.println("Make a four digit pin : ");
        Integer accountPassword = scn.nextInt();
        long accountNumber = Math.abs(random.nextLong()) % 10000000000L;
        boolean created = this.dbService.saveUserDetails(accountHolderName, accountNumber, accountPassword, con);
        if (created) {
            System.out.println("Congratulations Mr." + accountHolderName + " your account has been opened....\nYour account number is : " + accountNumber);
        } else {
            System.out.println("Some error has occurred!!!");
        }
        main(new String[]{""});
    }
}