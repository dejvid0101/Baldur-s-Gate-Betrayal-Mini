package me.projects.baldur.betrayal_at_baldurs_gate.classes;

public enum ConfigurationKey {
    PLAYER1("PLAYER1"),
    PLAYER2("PLAYER2"),
    PLAYER1_PORT("PLAYER1_PORT"),
    PLAYER2_PORT("PLAYER2_PORT"),
    RANDOM_PORT_HINT("RANDOM_PORT_HINT"),
    RMI_PORT("RMI_PORT"),
    HOST("HOST");

    private String keyName;

    private ConfigurationKey(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyName() {
        return keyName;
    }

}
