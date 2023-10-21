package me.projects.baldur.betrayal_at_baldurs_gate;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import me.projects.baldur.betrayal_at_baldurs_gate.classes.Adventurer;

public class HelloController {
    @FXML
    public Label nameLabel;

    @FXML
    public Pane cardContainer;

    @FXML
    public ImageView cardImage;

    public Adventurer adventurer;

    public void setAdventurer(Adventurer adventurer) {

        this.adventurer=adventurer;

    }

    public void initialize() {

        if (nameLabel != null) {
            Adventurer adventurer = new Adventurer("Tasha");
            nameLabel.setText(adventurer.getName());
        }

        if (cardImage != null) {
            cardImage.setImage(new Image("C:\\Users\\David\\OneDrive - Visoko uciliste Algebra\\Desktop\\Betrayal_at_Baldurs_Gate\\src\\main\\resources\\textures\\Lia_Faen_Tlabar.png"));
        }
    }
}