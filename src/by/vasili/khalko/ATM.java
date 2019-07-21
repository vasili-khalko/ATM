package by.vasili.khalko;


import by.vasili.khalko.transaction.DepositTransaction;
import by.vasili.khalko.transaction.InfoTransaction;
import by.vasili.khalko.transaction.Transaction;
import by.vasili.khalko.transaction.WithdrawTransaction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ATM {
    public static void main(String[] args){
        ATM atm = new ATM();
        atm.run();
    }

    private static final String CARD_NUMBER_PATTERN = "^(\\d{4}\\-){3}\\d{4}$";
    private static final String PIN_PATTERN = "^\\d{4}$";


    private boolean userAuthenticated;
    private String currentCardNumber;
    private  CasheDispenser casheDispenser;
    private DepositSlot depositSlot;
    private BankDatabase bankDatabase;

    public ATM() {
        userAuthenticated = userAuthenticated = false;
        casheDispenser = casheDispenser = new CasheDispenser();
        depositSlot = depositSlot = new DepositSlot();
        bankDatabase = bankDatabase = new BankDatabase();
    }



    public void run() {
        String exit = "n";
        while (!exit.equalsIgnoreCase("y")) {
            while (!userAuthenticated) {
                ConsoleHelper.writeMessage("Welcome!");
                authenticateUser();
            }
            PerformTransaction();
            userAuthenticated = false;
            currentCardNumber = "";
            ConsoleHelper.writeMessage("\nThank you! Goodbye!");
            bankDatabase.saveToFile();
            ConsoleHelper.writeMessage("Exit ATM y/n");
            exit = ConsoleHelper.readString();

        }
    }

    private void authenticateUser() {
        Pattern cardPattern = Pattern.compile(CARD_NUMBER_PATTERN);
        Pattern pinPattern = Pattern.compile(PIN_PATTERN);
        Matcher cardMatcher;
        Matcher pinMatcher;
        ConsoleHelper.writeMessage(("Please enter the card number:\nXXXX-XXXX-XXXX-XXXX"));
        String userCardNumber = ConsoleHelper.readString();
        ConsoleHelper.writeMessage("enter the PIN:");
        String userPin = ConsoleHelper.readString();
        cardMatcher = cardPattern.matcher(userCardNumber);
        pinMatcher = pinPattern.matcher(userPin);
        if (cardMatcher.matches()) {
            if (pinMatcher.matches()) {
                userAuthenticated = bankDatabase.AuthenticateUser(userCardNumber, Integer.parseInt(userPin));
            } else {
                ConsoleHelper.writeMessage("PIN does not match format. Try again");
                return;
            }
        } else {
            ConsoleHelper.writeMessage("Card number does not match format. Try again");
            return;
        }
        if (userAuthenticated) {
            currentCardNumber = userCardNumber;
        } else {ConsoleHelper.writeMessage("Invalid account number or PIN. Please try again.");}
    }

    private void PerformTransaction() {
        Transaction currentTransaction;
        boolean userExited = false;

        while (!userExited) {
            int mainMenuSelection = displayMainMenu();

            switch (mainMenuSelection) {
                case 1:
                case 2:
                case 3:
                    currentTransaction = createTransaction(mainMenuSelection);
                    currentTransaction.execute();
                    break;
                case 4:
                    ConsoleHelper.writeMessage("\nExiting the system...");
                    userExited = true;
                    break;
                default:
                    ConsoleHelper.writeMessage("\nYou did not enter a valid selection. Try again.");
                    break;
            }
        }
    }

    private int displayMainMenu() {
        ConsoleHelper.writeMessage("\nMain menu:");
        ConsoleHelper.writeMessage("1 - View my balance");
        ConsoleHelper.writeMessage("2 - Withdraw cash");
        ConsoleHelper.writeMessage("3 - Deposit funds");
        ConsoleHelper.writeMessage("4 - Exit");
        ConsoleHelper.writeMessage("Enter a choice: ");
        int input = ConsoleHelper.readInt();
        if (input < 1 && input > 4) {
            ConsoleHelper.wrongDataMessage();
            return displayMainMenu();
        }
        return input;
    }

    private  Transaction createTransaction(int type) {
        Transaction temp = null;
        switch (type) {
            case  1: temp = new InfoTransaction(currentCardNumber, bankDatabase);
                    break;
            case  2: temp = new WithdrawTransaction(currentCardNumber, bankDatabase, casheDispenser);
                    break;
            case 3: temp = new DepositTransaction(currentCardNumber, bankDatabase, depositSlot) ;
                    break;
        }
        return temp;
    }

}
