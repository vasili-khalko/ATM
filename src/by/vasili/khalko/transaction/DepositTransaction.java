package by.vasili.khalko.transaction;

import by.vasili.khalko.BankDatabase;
import by.vasili.khalko.ConsoleHelper;
import by.vasili.khalko.DepositSlot;

public class DepositTransaction extends Transaction{
    private static final int MAX_AMOUNT = 1000000;
    private  int amount;
    private DepositSlot depositSlot;

    private static final int CANCELED = 0;

    public DepositTransaction(String cardNumber, BankDatabase database, DepositSlot depositSlot) {
        super(cardNumber, database);
        this.depositSlot = depositSlot;
    }

    private int promptForDepositAmount() {
        ConsoleHelper.writeMessage("\nPlease enter the integer deposit amount or 0 to cancel (max amount = 1 000 000): ");
        int input = ConsoleHelper.readInt();
        if (input == CANCELED){
            return CANCELED;
        } else {
            if (input > MAX_AMOUNT) {
                ConsoleHelper.wrongDataMessage();
                return promptForDepositAmount();
            }
            return input;
        }
    }

    @Override
    public void execute() {
        amount = promptForDepositAmount();
        if (amount != CANCELED) {
            getDatabase().credit(getCardNumber(), amount);
            ConsoleHelper.writeMessage("\nThe transaction was successful");
        } else {
            ConsoleHelper.writeMessage("\nCanceling transaction...");
        }
    }
}
