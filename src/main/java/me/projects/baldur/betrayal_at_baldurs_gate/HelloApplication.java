package me.projects.baldur.betrayal_at_baldurs_gate;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.projects.baldur.betrayal_at_baldurs_gate.InGameChat.InGameChatImpl;
import me.projects.baldur.betrayal_at_baldurs_gate.InGameChat.InGameChatService;
import me.projects.baldur.betrayal_at_baldurs_gate.classes.*;
import me.projects.baldur.betrayal_at_baldurs_gate.classes.utilz.ClassInfoUtilz;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class HelloApplication extends Application {

    public static String activePlayer;
    public static InGameChatService msgService;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("board.fxml"));

        //loader must be loaded before passing values/data
        Parent root = fxmlLoader.load(); //initialize() controller method invoked

        //define controller for stage/scene
        HelloController gameController = fxmlLoader.getController();

        //after data has been prepared, init scene and set width/height
        Scene scene = new Scene(root, 1200.0, 775.0);

        //set stage...
        stage.setTitle("Betrayal at Baldur's Gate");
        stage.setScene(scene);
        stage.show();

        //update docs
        ClassInfoUtilz.printClassInfo(gameController.getClass());

    }

    public static void main(String[] args) throws IOException {
        new Thread(Application::launch).start();

        activePlayer = args[0];

        if (activePlayer.equals(Player.PLAYER1.name())) {
            startChatServer();
            listenToRequests(ConfigurationReader.getInstance().readIntegerValueForKey(ConfigurationKey.PLAYER1_PORT));

        }
        if (activePlayer.equals(Player.PLAYER2.name())) {
            listenToRequests(ConfigurationReader.getInstance().readIntegerValueForKey(ConfigurationKey.PLAYER2_PORT));

        }
    }

    public static void sendToPlayer1(State player2state) {
        try {
            Socket player1socket = new Socket(ConfigurationReader.getInstance().readStringValueForKey(ConfigurationKey.HOST), ConfigurationReader.getInstance().readIntegerValueForKey(ConfigurationKey.PLAYER1_PORT));

            ObjectOutputStream player1output = new ObjectOutputStream(player1socket.getOutputStream());

            player1output.writeObject(player2state);

        } catch (IOException e) {
            System.out.println("Unable to connect to player1");
        }
    }


    public static void sendToPlayer2(State player1state) {
        try {
            Socket player2socket = new Socket(ConfigurationReader.getInstance().readStringValueForKey(ConfigurationKey.HOST), ConfigurationReader.getInstance().readIntegerValueForKey(ConfigurationKey.PLAYER2_PORT));

            ObjectOutputStream player2output = new ObjectOutputStream(player2socket.getOutputStream());

            player2output.writeObject(player1state);

        } catch (IOException e) {
            System.out.println("Unable to connect to player2");
        }
    }

    private static void listenToRequests(int port) throws IOException {
        ServerSocket socket = new ServerSocket(port);

        while (true) {
            try (Socket clientSocket = socket.accept();
                 ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

                State receivedState = (State) in.readObject();
                Platform.runLater(() -> {
                    HelloController.refreshGame(receivedState);
                    HelloController.isItHauntTime();
                });

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void startChatServer() throws RemoteException {
        Registry registry =
                LocateRegistry.createRegistry(ConfigurationReader.getInstance()
                        .readIntegerValueForKey(ConfigurationKey.RMI_PORT));
        msgService = new InGameChatImpl();
        InGameChatService skeleton =
                (InGameChatService) UnicastRemoteObject.exportObject(msgService,ConfigurationReader.getInstance()
                        .readIntegerValueForKey(ConfigurationKey.RANDOM_PORT_HINT));
        registry.rebind(InGameChatService.CHAT_OBJECT_NAME, skeleton);
        System.out.println("Chat object registered?");
    }

}