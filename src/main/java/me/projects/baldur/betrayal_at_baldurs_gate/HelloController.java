package me.projects.baldur.betrayal_at_baldurs_gate;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;
import me.projects.baldur.betrayal_at_baldurs_gate.classes.Adventurer;

public class HelloController {
    @FXML
    public Label hauntLabel;

    @FXML
    public Pane ouijaBoard;

    @FXML
    public Pane player1Card;

    @FXML
    public Pane player2Card;

    public Adventurer adventurer;

    private ScaleTransition hauntTransition;

    public void setAdventurer(Adventurer adventurer) {

        this.adventurer=adventurer;

    }

    public void initialize() {

        if (hauntLabel != null) {
            // Create animation for haunt labellange
            hauntTransition = new ScaleTransition(Duration.seconds(0.5), hauntLabel);
            hauntTransition.setToX(5.0); // Scale width to 2 times its original size
            hauntTransition.setToY(5.0); // Scale height to half of its original size
            hauntTransition.setAutoReverse(true);
            hauntTransition.setCycleCount(ScaleTransition.INDEFINITE);
        }

        if (player1Card != null) {
            player1Card.setStyle("-fx-background-image: url('file:///C:/Users/David/OneDrive%20-%20Visoko%20uciliste%20Algebra/Desktop/Betrayal_at_Baldurs_Gate/src/main/resources/textures/Torskar%20Stonecleaver.png');" +
                    " -fx-background-size: 100px");

        }

        if (player2Card != null) {
            player2Card.setStyle("-fx-background-image: url('file:///C:/Users/David/OneDrive%20-%20Visoko%20uciliste%20Algebra/Desktop/Betrayal_at_Baldurs_Gate/src/main/resources/textures/Lia_Faen_Tlabar.png');" +
                    " -fx-background-size: 100px");

        }

        if (ouijaBoard != null) {
            ouijaBoard.setStyle("-fx-background-image: url('file:///C://Users//David//OneDrive%20-%20Visoko%20uciliste%20Algebra//Desktop//Betrayal_at_Baldurs_Gate//src//main//resources//textures//Baldur_Tiles.png');" +
                    "-fx-background-repeat:no-repeat;");

        }
    }

    public void changeLayout() {
        hauntTransition.play();
    }
}