public enum TransactionType {
    TRANSFER_COINS(0),
    CREATE_QUESTION(1),
    CREATE_ANSWER(2),
    CLOSE_QUESTION(3);


    private final int value;

    TransactionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}