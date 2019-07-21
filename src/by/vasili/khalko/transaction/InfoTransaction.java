package by.vasili.khalko.transaction;

import by.vasili.khalko.BankDatabase;
import by.vasili.khalko.ConsoleHelper;

public class InfoTransaction extends Transaction {

    public InfoTransaction(String cardNumber, BankDatabase database) {
        super(cardNumber, database);
    }

    @Override
    public void execute() {
        int availableBalance = getDatabase().getAvailableBalance(getCardNumber());

        ConsoleHelper.writeMessage(String
                .format("\nBalance Information:\nAvailable balance: %d", availableBalance));
    }
}
