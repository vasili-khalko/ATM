package by.vasili.khalko.transaction;

import by.vasili.khalko.BankDatabase;

public abstract class Transaction {
    private String cardNumber;
    private BankDatabase database;

    public Transaction(String cardNumber, BankDatabase database) {
        this.cardNumber = cardNumber;
        this.database = database;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public BankDatabase getDatabase() {
        return database;
    }

    public  abstract void execute();
}
