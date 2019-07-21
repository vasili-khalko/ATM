package by.vasili.khalko.transaction;

import by.vasili.khalko.BankDatabase;
import by.vasili.khalko.CasheDispenser;
import by.vasili.khalko.ConsoleHelper;

public class WithdrawTransaction extends Transaction {

    private static final int CANCELED = 0;

    private int amount;
    private CasheDispenser casheDispenser;

    public WithdrawTransaction(String cardNumber, BankDatabase database, CasheDispenser casheDispenser) {
        super(cardNumber, database);
        this.casheDispenser = casheDispenser;
    }

    private int promptForWithdrawAmount() {
        ConsoleHelper.writeMessage("\nPlease enter the integer withdraw amount or 0 to cancel");
        int availableCashe = Math.min(casheDispenser.getAvailableCashe(), getDatabase()
                .getAvailableBalance(getCardNumber()));
        ConsoleHelper.writeMessage(String.format("\nMax amount: %d\n", availableCashe));
        int input = ConsoleHelper.readInt();
        return input;
    }

    @Override
    public void execute() {
        boolean cashDispensed = false;
        boolean transactionCanceled = false;
        do {
            int input = promptForWithdrawAmount();
            if (input != CANCELED) {
                amount = input;
                int availableBalance = getDatabase().getAvailableBalance(getCardNumber());
                if (amount <= availableBalance) {
                    if (casheDispenser.isSufficientCashAvailable(amount)) {
                        getDatabase().debit(getCardNumber(), amount);

                        casheDispenser.dispenseCash(amount);
                        cashDispensed = true;
                        ConsoleHelper.writeMessage("\nPlease take your cash from the cash dispenser.");
                    } else {
                        ConsoleHelper.writeMessage("\nInsufficient cash available in the ATM." +
                                 "\n\nPlease choose a smaller amount.");
                    }

                } else {
                    ConsoleHelper.writeMessage("\nInsufficient cash available in your account." +
                            "\n\nPlease choose a smaller amount.");
                }
            } else {
                ConsoleHelper.writeMessage("\nCanceling transaction...");
                transactionCanceled = true;
            }
        } while ((!cashDispensed) && (!transactionCanceled));
    }
}
