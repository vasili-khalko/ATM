package by.vasili.khalko;

public class CasheDispenser {
    private static final int INITIAL_CASH = 50000;
    private int availableCashe;

    public CasheDispenser() {
        availableCashe = INITIAL_CASH;
    }

    public void dispenseCash(int amount) {
        availableCashe -= amount;
    }

    public boolean isSufficientCashAvailable(int amount) {
        return availableCashe >= amount;
    }

    public int getAvailableCashe() {
        return availableCashe;
    }
}
