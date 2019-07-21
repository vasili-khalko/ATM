package by.vasili.khalko;

public class Account {
    private String cardNumber;
    private int pin;
    private int availableBalance;

    public Account(String cardNumber, int pin, int availableBalance) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.availableBalance = availableBalance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getPin() {
        return pin;
    }

    public int getAvailableBalance() {
        return availableBalance;
    }

    public boolean validatePIN(int userPIN){
        return userPIN == pin;
    }

    public void credit(int amount){
        availableBalance += amount;
    }

    public void debit(int amount){
        availableBalance -= amount;
    }
}
