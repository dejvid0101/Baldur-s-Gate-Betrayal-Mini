package me.projects.baldur.betrayal_at_baldurs_gate;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;
import me.projects.baldur.betrayal_at_baldurs_gate.classes.Adventurer;

import java.io.Console;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class HelloController {

    private int tilesCounter = 1;

    @FXML
    public VBox game;

    @FXML
    public VBox initialScreen;

    @FXML
    public Label hauntLabel;

    @FXML
    public Label tilesLabel;

    @FXML
    public HBox arrowWrapper;

    @FXML
    public Pane ouijaBoard;

    @FXML
    public GridPane ouijaBoardHolder;

    @FXML
    public Pane player1Card;

    @FXML
    public Pane player1CardInitial;

    @FXML
    public Pane player2Card;

    public Pane player2CardInitial;


    public Adventurer adventurer;

    private ScaleTransition hauntTransition;

    private ScaleTransition tilesTransition;

    private ScaleTransition tilesTransitionReverse;
    @FXML
    public Polygon leftArrow;
    @FXML
    public Polygon rightArrow;

    private int steps = 1;

    private int currentPlayer = 1;

    private boolean isPlayer1AllLeft;
    private boolean isPlayer1AllRight;
    private boolean isPlayer2AllLeft;
    private boolean isPlayer2AllRight;

    @FXML
    private MenuItem newGameBar;

    private int movesSinceStart;
    @FXML
    public DialogPane winnerScreen;

    public void setAdventurer(Adventurer adventurer) {

        this.adventurer = adventurer;

    }

    public void initialize() {

        if (hauntLabel != null) {
            // Create animation for haunt labellange
            hauntTransition = new ScaleTransition(Duration.seconds(0.5), hauntLabel);

            hauntTransition.setToX(5.0); // Scale width to 2 times its original size
            hauntTransition.setToY(5.0); // Scale height to half of its original size
            hauntTransition.setAutoReverse(true);
            hauntTransition.setCycleCount(4);
        }

        if (player1Card != null) {
            player1Card.setStyle("-fx-background-image: url('file:///C:/Users/David/OneDrive%20-%20Visoko%20uciliste%20Algebra/Desktop/Betrayal_at_Baldurs_Gate/src/main/resources/textures/Torskar%20Stonecleaver.png');" +
                    " -fx-background-size: 100px");

        }

        if (player1CardInitial != null) {
            player1CardInitial.setStyle("-fx-background-image: url('file:///C:/Users/David/OneDrive%20-%20Visoko%20uciliste%20Algebra/Desktop/Betrayal_at_Baldurs_Gate/src/main/resources/textures/Torskar%20Stonecleaver.png');" +
                    " -fx-background-size: 100px");

        }

        if (player2Card != null) {
            player2Card.setStyle("-fx-background-image: url('file:///C:/Users/David/OneDrive%20-%20Visoko%20uciliste%20Algebra/Desktop/Betrayal_at_Baldurs_Gate/src/main/resources/textures/Lia_Faen_Tlabar.png');" +
                    " -fx-background-size: 100px");

        }

        if (player2CardInitial != null) {
            player2CardInitial.setStyle("-fx-background-image: url('file:///C:/Users/David/OneDrive%20-%20Visoko%20uciliste%20Algebra/Desktop/Betrayal_at_Baldurs_Gate/src/main/resources/textures/Lia_Faen_Tlabar.png');" +
                    " -fx-background-size: 100px");

        }

        if (ouijaBoard != null) {
            ouijaBoard.setStyle("-fx-background-image: url('file:///C://Users//David//OneDrive%20-%20Visoko%20uciliste%20Algebra//Desktop//Betrayal_at_Baldurs_Gate//src//main//resources//textures//Baldur_Tiles.png');" +
                    "-fx-background-repeat:no-repeat; -fx-alignment:center;");

        }

        if (tilesLabel != null) {
            tilesTransition = new ScaleTransition(Duration.seconds(0.2), tilesLabel);
            tilesTransition.setToX(3.0); // Scale width to 3 times its original size
            tilesTransition.setToY(3.0); // Scale height to 3 times its original size
            tilesTransition.setAutoReverse(false);

            tilesTransitionReverse = new ScaleTransition(Duration.seconds(0.2), tilesLabel);
            tilesTransitionReverse.setToX(1.5); // Scale width to 3 times its original size
            tilesTransitionReverse.setToY(1.5); // Scale height to 3 times its original size
            tilesTransitionReverse.setAutoReverse(false);

        }

        if (newGameBar != null) {

            newGameBar.setOnAction(actionEvent -> startPlayerFlow());

        }
    }


    //moves received player
    public void moveRight(Pane player) {
        player.setLayoutX((player.getLayoutX()) + 250 * steps);

        if (currentPlayer == 1) currentPlayer = 2;
        else currentPlayer = 1;
        startPlayerFlow();
    }

    public void moveLeft(Pane player) {
        player.setLayoutX((player.getLayoutX()) - 250 * steps);

        if (currentPlayer == 1) currentPlayer = 2;
        else currentPlayer = 1;
        startPlayerFlow();

    }

    public void startPlayerFlow() {
        isItHauntTime();
        this.game.setVisible(true);
        this.game.setTranslateY(-250);
        this.initialScreen.setVisible(false);

        this.leftArrow.setVisible(false);
        this.rightArrow.setVisible(false);

        System.out.println(Double.toString(player1Card.getLayoutX()));

        if (currentPlayer == 2) {
            rightArrow.setOnMouseClicked(event -> moveRight((player2Card)));
            leftArrow.setOnMouseClicked(event -> moveLeft((player2Card)));

        } else {
            rightArrow.setOnMouseClicked(event -> moveRight(player1Card));
            leftArrow.setOnMouseClicked(event -> moveLeft(player1Card));
        }

        isPlayer1AllLeft = false;
        isPlayer1AllRight = false;
        isPlayer2AllLeft = false;
        isPlayer2AllRight = false;

        startTileCounter();

    }

    private void isItHauntTime() {
        movesSinceStart++;
        if (movesSinceStart > 10) {

            hauntTransition.play();

            if (player1Card.getLayoutX() <= 750 && player1Card.getLayoutX() >= 500) {
                hauntLabel.setText("Player 1 wins");

                game.setVisible(false);
                initialScreen.setVisible(true);

            } else if (player2Card.getLayoutX() <= 750 && player2Card.getLayoutX() >= 500) {
                hauntLabel.setText("Player 2 wins");

                game.setVisible(false);
                initialScreen.setVisible(true);

            } else {
                hauntLabel.setText("It's a draw");

                game.setVisible(false);
                initialScreen.setVisible(true);

            }

        }
    }

    //starts tile counting animation, stops and generates random tile step...
    public void startTileCounter() {
        tilesLabel.setText(Integer.toString(tilesCounter));

        // Create a Timeline to change the label's text every 100ms
        Duration duration = Duration.millis(100);
        KeyFrame keyFrame = new KeyFrame(duration, event -> {
            if (tilesCounter >= 3) {
                tilesCounter = 1;
            } else {
                tilesCounter++;
            }

            tilesTransition.play();
            tilesLabel.setText("Distance to move: " + Integer.toString(tilesCounter) + " tiles");
        });
        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Random r = new Random();

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            timeline.stop();
            steps = r.nextInt(2) + 1;

            System.out.println("Runnable task");
            tilesTransitionReverse.play();

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    tilesLabel.setText((steps == 1) ? "Distance to move: " + Integer.toString(steps) + " tile" : "Distance to move: " + Integer.toString(steps) + " tiles");
                    System.out.println(steps);

                    switch (steps) {
                        case 1:

                            isPlayer1AllLeft = player1Card.getLayoutX() < 250;
                            isPlayer1AllRight = player1Card.getLayoutX() >= 750;
                            isPlayer2AllLeft = player2Card.getLayoutX() < 250;
                            isPlayer2AllRight = player2Card.getLayoutX() >= 750;

                        case 2:

                            isPlayer1AllLeft = player1Card.getLayoutX() < 500;
                            System.out.println("layout player 1: " + player1Card.getLayoutX());
                            isPlayer1AllRight = player1Card.getLayoutX() > 500;
                            isPlayer2AllLeft = player2Card.getLayoutX() < 500;
                            System.out.println("layout player 2: " + player2Card.getLayoutX());

                            isPlayer2AllRight = player2Card.getLayoutX() > 500;

                    }

                    System.out.println(isPlayer1AllLeft);
                    System.out.println(isPlayer1AllRight);
                    System.out.println(isPlayer2AllLeft);
                    System.out.println(isPlayer2AllRight);

                    //is any player close to window border? if so, prevent moving in direction of the border depending on the number of steps
                    switch (currentPlayer) {
                        case 1:
                            if (isPlayer1AllLeft && isPlayer1AllRight) {
                                rightArrow.setVisible(true);
                                leftArrow.setVisible(true);

                            } else if (isPlayer1AllLeft && !isPlayer1AllRight) {
                                rightArrow.setVisible(true);
                            } else if (!isPlayer1AllLeft && isPlayer1AllRight) {
                                leftArrow.setVisible(true);
                            } else {
                                leftArrow.setVisible(true);
                                rightArrow.setVisible(true);
                            }
                        case 2:
                            if (isPlayer2AllLeft && isPlayer2AllRight) {
                                rightArrow.setVisible(true);
                                leftArrow.setVisible(true);
                            } else if (isPlayer2AllLeft && !isPlayer2AllRight) {
                                rightArrow.setVisible(true);
                            } else if (!isPlayer2AllLeft && isPlayer2AllRight) {
                                leftArrow.setVisible(true);
                            } else {
                                leftArrow.setVisible(true);
                                rightArrow.setVisible(true);
                            }
                    }

                }
            });
        };


        long delay = 2000; // Delay in milliseconds (1 second in this example)
        executor.schedule(task, delay, TimeUnit.MILLISECONDS);


        // Shutdown the executor when you're done
        executor.shutdown();


    }

}