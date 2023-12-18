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
import me.projects.baldur.betrayal_at_baldurs_gate.InGameChat.InGameChatService;
import me.projects.baldur.betrayal_at_baldurs_gate.classes.*;

import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class HelloController implements Serializable {

    private static State gameState;
    public static InGameChatService msgService;
    private static int tilesCounter = 1;

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

    private static ScaleTransition hauntTransition;

    private static ScaleTransition tilesTransition;

    private static ScaleTransition tilesTransitionReverse;
    @FXML
    public Polygon leftArrow;
    @FXML
    public Polygon rightArrow;

    private static boolean isPlayer1AllLeft;
    private static boolean isPlayer1AllRight;
    private static boolean isPlayer2AllLeft;
    private static boolean isPlayer2AllRight;

    @FXML
    private MenuItem newGameBar;

    @FXML
    private MenuItem loadGameBar;
    @FXML

    private MenuItem saveGameBar;

    public static Pane player1CardStatic;

    public static Pane player2CardStatic;

    public static Label tilesLabelStatic;

    public static Label hauntLabelStatic;

    public static Polygon leftArrowStatic;
    public static Polygon rightArrowStatic;

    public static VBox gameStatic;

    public static VBox initialScreenStatic;

    public TextArea chatArea;
    public TextField msgField;

    public Button btnSend;

    public void initialize() throws RemoteException {

        connectToChatService();

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
                updateOtherPlayer();
                    }
            );

        }

        if (loadGameBar != null) {

            loadGameBar.setOnAction(actionEvent -> {
                loadGame();
                setPlayersToLastPosition();
                startPlayerFlow();
                updateOtherPlayer();
                try {
                    chatListener();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });

        }

        if (saveGameBar != null) {

            saveGameBar.setOnAction(actionEvent -> {
                saveGame();

            });

        }

        if (btnSend != null) {

            btnSend.setOnAction(actionEvent -> {
                try {
                    sendMsg();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });

        }

        player1CardStatic=player1Card;
        player2CardStatic=player2Card;
        leftArrowStatic=leftArrow;
        rightArrowStatic=rightArrow;
        tilesLabelStatic=tilesLabel;
        hauntLabelStatic=hauntLabel;
        initialScreenStatic=initialScreen;
        gameStatic=game;

        //make sure game state object is not null before attempting to load game
        gameState=new State(player1Card.getLayoutX(),player2Card.getLayoutX(),1,1,1);

        setPlayersToLastPosition();

        Platform.runLater(()-> {
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

            // Define the task to be executed periodically
            Runnable task = () -> {
                try {
                chatListener();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            };

            // Schedule the task to run every 1 second
            scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);

            }

        );
    }

    private static void updateOtherPlayer() {
        if (HelloApplication.activePlayer.trim().equals(NetworkConfig.PLAYER2))
        {
            HelloApplication.sendToPlayer1(gameState);
            System.out.println(gameState);
        }

        if (HelloApplication.activePlayer.trim().equals(NetworkConfig.PLAYER1))
        {
            HelloApplication.sendToPlayer2(gameState);
            System.out.println(gameState);
        }
    }

    private void refreshState(){
        //make sure game state object is not null before attempting to load game
        gameState=new State(0,0,1,1,1);
    }


    //moves received player
    public static void moveRight(Pane player) {

        //move player on screen
        player.setLayoutX((gameState.getAnyPlayerCardLayoutX(gameState.getCurrentPlayer())) + 250 * gameState.getSteps());

        //save new player position to state
        gameState.setAnyPlayerCardLayoutX(gameState.getCurrentPlayer(), player.getLayoutX());

        if (gameState.getCurrentPlayer() == 1) gameState.setCurrentPlayer(2);
        else gameState.setCurrentPlayer(1);

        updateOtherPlayer();

        startPlayerFlow();
    }

    public static void moveLeft(Pane player) {


        //move player on screen
        player.setLayoutX((gameState.getAnyPlayerCardLayoutX(gameState.getCurrentPlayer())) - 250 * gameState.getSteps());

        //save new player position to state
        gameState.setAnyPlayerCardLayoutX(gameState.getCurrentPlayer(), player.getLayoutX());


        if (gameState.getCurrentPlayer() == 1) gameState.setCurrentPlayer(2);
        else gameState.setCurrentPlayer(1);
        updateOtherPlayer();

        startPlayerFlow();

    }

    public static void startPlayerFlow() {
        if(isItHauntTime()) return;
if(HelloApplication.activePlayer.equals(NetworkConfig.PLAYER1)) if(gameState.getCurrentPlayer()==2) {
    leftArrowStatic.setVisible(false);
    rightArrowStatic.setVisible(false);
    return;
}

        if(HelloApplication.activePlayer.equals(NetworkConfig.PLAYER2)) if(gameState.getCurrentPlayer()==1) {
            leftArrowStatic.setVisible(false);
            rightArrowStatic.setVisible(false);
            return;
        }

        System.err.println("!isIt");

        gameStatic.setVisible(true);
        gameStatic.setTranslateY(-250);
        initialScreenStatic.setVisible(false);

        leftArrowStatic.setVisible(false);
        rightArrowStatic.setVisible(false);

        System.out.println(Integer.toString(gameState.getCurrentPlayer()));

        if (gameState.getCurrentPlayer() == 2) {
            rightArrowStatic.setOnMouseClicked(event -> moveRight(player2CardStatic));
            leftArrowStatic.setOnMouseClicked(event -> moveLeft(player2CardStatic));

        } else {
            rightArrowStatic.setOnMouseClicked(event -> moveRight(player1CardStatic));
            leftArrowStatic.setOnMouseClicked(event -> moveLeft(player1CardStatic));
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
    public static boolean isItHauntTime() {


        gameState.increaseMovesSinceStart();

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        Runnable task = () -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    // Code to be executed after the delay
                    gameStatic.setVisible(false);
                    initialScreenStatic.setVisible(true);

                }
            });
        };

        if (gameState.getMovesSinceStart() > 10) {
            hauntTransition.play();

            if (gameState.getPlayer1CardLayoutX() <= 750 && gameState.getPlayer1CardLayoutX() >= 500) {
                hauntLabelStatic.setText("Player 1 wins");

                int delayInSeconds = 5;
                executor.schedule(task, delayInSeconds, TimeUnit.SECONDS);
            }

            else if (gameState.getPlayer2CardLayoutX() <= 750 && gameState.getPlayer2CardLayoutX() >= 500) {
            hauntLabelStatic.setText("Player 2 wins");

                int delayInSeconds = 5;
                executor.schedule(task, delayInSeconds, TimeUnit.SECONDS);

        } else {
            hauntLabelStatic.setText("It's a draw");

                int delayInSeconds = 5;
                executor.schedule(task, delayInSeconds, TimeUnit.SECONDS);
        }
    return true;
        }
return false;
        }

    //starts tile counting animation, stops and generates random tile step, renders arrows to move depending on player position...
    public static void startTileCounter() {
        tilesLabelStatic.setText(Integer.toString(tilesCounter));

        // Create a Timeline to change the label's text every 100ms
        Duration duration = Duration.millis(100);
        KeyFrame keyFrame = new KeyFrame(duration, event -> {
            if (tilesCounter >= 3) {
                tilesCounter = 1;
            } else {
                tilesCounter++;
            }

            tilesTransition.play();
            tilesLabelStatic.setText("Distance to move: " + Integer.toString(tilesCounter) + " tiles");
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
                    tilesLabelStatic.setText((gameState.getSteps() == 1) ? "Distance to move: " + Integer.toString(gameState.getSteps()) + " tile" : "Distance to move: " + Integer.toString(gameState.getSteps()) + " tiles");
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
                                rightArrowStatic.setVisible(true);
                                leftArrowStatic.setVisible(true);

                            } else if (isPlayer1AllLeft && !isPlayer1AllRight) {
                                rightArrowStatic.setVisible(true);
                            } else if (!isPlayer1AllLeft && isPlayer1AllRight) {
                                leftArrowStatic.setVisible(true);
                            } else {
                                leftArrowStatic.setVisible(true);
                                rightArrowStatic.setVisible(true);
                            }
                        case 2:
                            if (isPlayer2AllLeft && isPlayer2AllRight) {
                                rightArrowStatic.setVisible(true);
                                leftArrowStatic.setVisible(true);
                            } else if (isPlayer2AllLeft && !isPlayer2AllRight) {
                                rightArrowStatic.setVisible(true);
                            } else if (!isPlayer2AllLeft && isPlayer2AllRight) {
                                leftArrowStatic.setVisible(true);
                            } else {
                                leftArrowStatic.setVisible(true);
                                rightArrowStatic.setVisible(true);
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

        player1CardStatic.setLayoutX(gameState.getPlayer1CardLayoutX());
        player2CardStatic.setLayoutX(gameState.getPlayer2CardLayoutX());

        startPlayerFlow();
    }

    public void sendMsg() throws RemoteException {
        String msg= msgField.getText();
        msgService.sendMsg(HelloApplication.activePlayer+": "+msg);

    }

    public void chatListener() throws RemoteException {
        StringBuilder stringBuilder = new StringBuilder();

        List<String> chatHistory=msgService.getAllMsgs();
        for (String messaj : chatHistory) {
            stringBuilder.append(messaj).append("\n");
        }

        this.chatArea.setText(stringBuilder.toString());
    }

    public void connectToChatService(){
        try {
            Registry registry = LocateRegistry.getRegistry(NetworkConfig.HOST, NetworkConfig.RMI_PORT);
            msgService = (InGameChatService) registry.lookup(InGameChatService.CHAT_OBJECT_NAME);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

}