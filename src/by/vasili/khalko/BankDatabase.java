package by.vasili.khalko;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BankDatabase {
    private List<Account> accounts;

    public BankDatabase() {
        accounts = new ArrayList<>();
        readFromFile();
    }

    private Account getAccount(String cardNumber) {
        for (Account currentAccount: accounts) {
            if (currentAccount.getCardNumber().equals(cardNumber));
            return currentAccount;
        }

        return null;
    }

    public boolean AuthenticateUser(String userCardNumber, int userPIN) {
        Account userAccount = getAccount(userCardNumber);

        if(userAccount != null) {
            return  userAccount.validatePIN(userPIN);
        } else {
            return false;
        }
    }

    public int getAvailableBalance(String userCardNumber) {
        Account userAccount = getAccount(userCardNumber);
        return userAccount.getAvailableBalance();
    }

    public void credit(String userCardNumber, int amount) {
        Account userAccount = getAccount(userCardNumber);
        userAccount.credit(amount);
    }

    public void debit(String userCardNumber, int amount) {
        Account userAccount = getAccount(userCardNumber);
        userAccount.debit(amount);
    }

    public void saveToFile(){
        try (FileWriter writer = new FileWriter("src/by/vasili/khalko/resources/bankDatabase.txt")){
            for (Account account: accounts) {
                String cardNumber = account.getCardNumber();
                String pin = String.valueOf(account.getPin());
                String availableBalance = String.valueOf(account.getAvailableBalance());
                writer.write(String.format("%s $s %s", cardNumber, pin, availableBalance));
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void readFromFile(){
        try(FileReader fileReader = new FileReader("src/by/vasili/khalko/resources/bankDatabase.txt"); BufferedReader reader = new BufferedReader(fileReader)){
            String line = reader.readLine();
            while (line != null) {
                String[] arguments = line.split(" ");
                String cardNumber = arguments[0];
                int pin = Integer.parseInt(arguments[1]);
                int availableBalance = Integer.parseInt(arguments[2]);
                accounts.add(new Account(cardNumber, pin, availableBalance));
                line = reader.readLine();
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }


}
