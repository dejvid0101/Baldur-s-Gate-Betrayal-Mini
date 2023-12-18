package me.projects.baldur.betrayal_at_baldurs_gate;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Screen;
import javafx.stage.Stage;
import me.projects.baldur.betrayal_at_baldurs_gate.InGameChat.InGameChatImpl;
import me.projects.baldur.betrayal_at_baldurs_gate.InGameChat.InGameChatService;
import me.projects.baldur.betrayal_at_baldurs_gate.classes.*;
import me.projects.baldur.betrayal_at_baldurs_gate.rmi.RemoteService;
import me.projects.baldur.betrayal_at_baldurs_gate.rmi.RemoteServiceImpl;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.NotBoundException;
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
        Scene scene = new Scene(root, 1200.0, 750.0);

        //set stage...
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        //update docs
        ClassInfoUtilz.printClassInfo(gameController.getClass());

    }

    public static void main(String[] args) throws IOException {
        new Thread(Application::launch).start();

        activePlayer = args[0];


        if (activePlayer.equals(Player.PLAYER1.name())) {
            startChatService();
            System.out.println("CALIENTE");
            listenToRequests(NetworkConfig.PLAYER1_PORT);

        }
        if (activePlayer.equals(Player.PLAYER2.name())) {
            System.out.println("SERVERANGE");
            listenToRequests(NetworkConfig.PLAYER2_PORT);

        }
    }

    public static void sendToPlayer1(State player2state) {
        try {
            Socket player1socket = new Socket(NetworkConfig.HOST, NetworkConfig.PLAYER1_PORT);

            ObjectOutputStream player1output = new ObjectOutputStream(player1socket.getOutputStream());

            player1output.writeObject(player2state);

        } catch (IOException e) {
            System.out.println("Unable to connect to player1");
        }
    }


    public static void sendToPlayer2(State player1state) {
        try {
            Socket player2socket = new Socket(NetworkConfig.HOST, NetworkConfig.PLAYER2_PORT);

            ObjectOutputStream player2output = new ObjectOutputStream(player2socket.getOutputStream());

            player2output.writeObject(player1state);

        } catch (IOException e) {
            System.out.println("Unable to connect to player2");
        }
    }

    private static void listenToRequests(int port) throws IOException {
        ServerSocket socket = new ServerSocket(port);

        System.out.println(socket.getLocalPort());

        while (true) {
            try (Socket clientSocket = socket.accept();
                 ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

                State receivedData = (State) in.readObject();
                System.out.println("Received state from " + clientSocket.getLocalPort() + receivedData);
                Platform.runLater(() -> {
                    HelloController.refreshGame(receivedData);
                    HelloController.isItHauntTime();
                });

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void startChatService() throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(NetworkConfig.RMI_PORT);
        msgService = new InGameChatImpl();
        InGameChatService skeleton = (InGameChatService) UnicastRemoteObject.exportObject(msgService, NetworkConfig.RANDOM_PORT_HINT);
        registry.rebind(InGameChatService.CHAT_OBJECT_NAME, skeleton);
        System.out.println("Chat object registered?");
    }

}