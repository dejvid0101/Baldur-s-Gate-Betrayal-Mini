package me.projects.baldur.betrayal_at_baldurs_gate.classes;


import javafx.scene.control.skin.TextInputControlSkin;

public class Adventurer {
    private String name;
    private int health;
    private int sanity;
    private int might;
    private int speed;
    private int knowledge;

    public Adventurer(String name) {
        this.name = name;
        // Initialize player's attributes and starting values
    }

    public void move(TextInputControlSkin.Direction direction) {
        // Implement player movement logic
        // Update player's position on the game board
    }

    public void performAction() {
        // Define actions that a player can take during their turn
    }

    // Getters and setters for player attributes

    public String getName() {
        return name;
    }
}
