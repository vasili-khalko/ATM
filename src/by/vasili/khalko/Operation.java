package by.vasili.khalko;

public enum Operation {
    INFO, WITHDRAW, DEPOSIT, EXIT_ATM;

    public static Operation getAllowableOperationByOrdinal(Integer i)throws IllegalArgumentException{
        switch (i) {
            case 1: return INFO;

            case 2: return DEPOSIT;

            case 3: return WITHDRAW;

            case 4: return EXIT_ATM;

            default: throw new IllegalArgumentException();
        }
    }
}
