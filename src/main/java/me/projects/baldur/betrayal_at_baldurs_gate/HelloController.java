package me.projects.baldur.betrayal_at_baldurs_gate;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import me.projects.baldur.betrayal_at_baldurs_gate.classes.Adventurer;

public class HelloController {
    @FXML
    public Label nameLabel;

    public Adventurer adventurer;

    public void setAdventurer(Adventurer adventurer) {

        this.adventurer=adventurer;

    }

    public void initialize() {
        if (nameLabel != null) {
            Adventurer adventurer = new Adventurer("Tasha");
            nameLabel.setText(adventurer.getName());
        }
    }
}