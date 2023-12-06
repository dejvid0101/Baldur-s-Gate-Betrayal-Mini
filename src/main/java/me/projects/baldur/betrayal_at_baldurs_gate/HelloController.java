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
import me.projects.baldur.betrayal_at_baldurs_gate.classes.ClassInfoUtilz;
import me.projects.baldur.betrayal_at_baldurs_gate.classes.FileUtilz;
import me.projects.baldur.betrayal_at_baldurs_gate.classes.State;

import java.io.*;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class HelloController implements Serializable {

    private static State gameState;
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
    public Pane ouijaBoard;

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

    private boolean isPlayer1AllLeft;
    private boolean isPlayer1AllRight;
    private boolean isPlayer2AllLeft;
    private boolean isPlayer2AllRight;

    @FXML
    private MenuItem newGameBar;

    @FXML
    private MenuItem loadGameBar;
    @FXML

    private MenuItem saveGameBar;

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

            newGameBar.setOnAction(actionEvent -> {
                refreshState();
                setPlayersToLastPosition();
                startPlayerFlow();
                    }
            );

        }

        if (loadGameBar != null) {

            loadGameBar.setOnAction(actionEvent -> {
                loadGame();
                setPlayersToLastPosition();
                startPlayerFlow();
            });

        }

        if (saveGameBar != null) {

            saveGameBar.setOnAction(actionEvent -> {
                saveGame();
                HelloApplication.sendToPlayer1();
            });

        }

        //make sure game state object is not null before attempting to load game
        gameState=new State(player1Card.getLayoutX(),player2Card.getLayoutX(),1,1,1);

        setPlayersToLastPosition();
    }

    private void refreshState(){
        //make sure game state object is not null before attempting to load game
        gameState=new State(player1Card.getLayoutX(),player2Card.getLayoutX(),1,1,1);
    }


    //moves received player
    public void moveRight(Pane player) {

        //move player on screen
        player.setLayoutX((gameState.getAnyPlayerCardLayoutX(gameState.getCurrentPlayer())) + 250 * gameState.getSteps());

        //save new player position to state
        gameState.setAnyPlayerCardLayoutX(gameState.getCurrentPlayer(), player.getLayoutX());

        if (gameState.getCurrentPlayer() == 1) gameState.setCurrentPlayer(2);
        else gameState.setCurrentPlayer(1);
        startPlayerFlow();
    }

    public void moveLeft(Pane player, int playerNr) {
        //move player on screen
        player.setLayoutX((gameState.getAnyPlayerCardLayoutX(gameState.getCurrentPlayer())) - 250 * gameState.getSteps());

        //save new player position to state
        gameState.setAnyPlayerCardLayoutX(gameState.getCurrentPlayer(), player.getLayoutX());

        if (gameState.getCurrentPlayer() == 1) gameState.setCurrentPlayer(2);
        else gameState.setCurrentPlayer(1);
        startPlayerFlow();

    }

    public void startPlayerFlow() {
        isItHauntTime();
        this.game.setVisible(true);
        this.game.setTranslateY(-250);
        this.initialScreen.setVisible(false);

        this.leftArrow.setVisible(false);
        this.rightArrow.setVisible(false);

        System.out.println(Integer.toString(gameState.getCurrentPlayer()));

        if (gameState.getCurrentPlayer() == 2) {
            rightArrow.setOnMouseClicked(event -> moveRight(player2Card));
            leftArrow.setOnMouseClicked(event -> moveLeft(player2Card,2));

        } else {
            rightArrow.setOnMouseClicked(event -> moveRight(player1Card));
            leftArrow.setOnMouseClicked(event -> moveLeft(player1Card,1));
        }

        isPlayer1AllLeft = false;
        isPlayer1AllRight = false;
        isPlayer2AllLeft = false;
        isPlayer2AllRight = false;

        startTileCounter();

    }

    private void setPlayersToLastPosition() {
        this.player1Card.setLayoutX(gameState.getPlayer1CardLayoutX());
        this.player2Card.setLayoutX(gameState.getPlayer2CardLayoutX());
    }

    //if haunt time, start haunt animation and set flag, winner decided here
    private void isItHauntTime() {
        gameState.increaseMovesSinceStart();
        if (gameState.getMovesSinceStart() > 10) {
            hauntTransition.play();

            if (gameState.getPlayer1CardLayoutX() <= 750 && gameState.getPlayer1CardLayoutX() >= 500) {
                hauntLabel.setText("Player 1 wins");

                game.setVisible(false);
                initialScreen.setVisible(true);

            } else if (gameState.getPlayer2CardLayoutX() <= 750 && gameState.getPlayer2CardLayoutX() >= 500) {
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

    //starts tile counting animation, stops and generates random tile step, renders arrows to move depending on player position...
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
            gameState.setSteps(r.nextInt(2) + 1);

            System.out.println("Runnable task");
            tilesTransitionReverse.play();

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    tilesLabel.setText((gameState.getSteps() == 1) ? "Distance to move: " + Integer.toString(gameState.getSteps()) + " tile" : "Distance to move: " + Integer.toString(gameState.getSteps()) + " tiles");
                    System.out.println(gameState.getSteps());

                    switch (gameState.getSteps()) {
                        case 1:

                            isPlayer1AllLeft = gameState.getPlayer1CardLayoutX() < 250;
                            isPlayer1AllRight = gameState.getPlayer1CardLayoutX() >= 750;
                            isPlayer2AllLeft = gameState.getPlayer2CardLayoutX() < 250;
                            isPlayer2AllRight = gameState.getPlayer2CardLayoutX() >= 750;

                        case 2:

                            isPlayer1AllLeft = gameState.getPlayer1CardLayoutX() < 500;
                            System.out.println("layout player 1: " + gameState.getPlayer1CardLayoutX());
                            isPlayer1AllRight = gameState.getPlayer1CardLayoutX() > 500;
                            isPlayer2AllLeft = gameState.getPlayer2CardLayoutX() < 500;

                            isPlayer2AllRight = gameState.getPlayer2CardLayoutX() > 500;

                    }

                    System.out.println(isPlayer1AllLeft);
                    System.out.println(isPlayer1AllRight);
                    System.out.println(isPlayer2AllLeft);
                    System.out.println(isPlayer2AllRight);

                    //is any player close to window border? if so, prevent moving in direction of the border depending on the number of steps
                    switch (gameState.getCurrentPlayer()) {
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

    public void saveGame(){

FileUtilz.saveGameToFile(gameState);
    }

    public void loadGame(){

        gameState=FileUtilz.loadGameFromFile();

        // Now you can use the deserialized object
        if (gameState != null) {

            if(gameState.getMovesSinceStart()>=10) gameState.setMovesSinceStart(0);
            System.out.println(gameState.toString());
        }
    }

    public static void refreshGame(State changedState){

        gameState=changedState;

        System.out.println(gameState);
    }

}