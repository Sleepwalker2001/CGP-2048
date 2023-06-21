package game.main;

public enum ID {

    Player2("player 2"),

    Player4("player 4"),
    Player8("player 8"),
    Player16("player 16"),
    Player32("player 32"),
    Player64("player 64"),
    Player128("player 128"),
    Player256("player 256"),
    Player512("player 512"),
    Player1024("player 1024"),
    Player2048("player 2048"),
    Player4096("player 4096");

    private String operationName;

    private ID(final String operationName) {
        setOperationName(operationName);
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(final String operationName) {
        this.operationName = operationName;
    }

    public static ID getOperationName(final String operationName) {

        for (ID oprname : ID.values()) {
            if (operationName.equals(oprname.toString())) {
                return oprname;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return operationName;
    }

}
