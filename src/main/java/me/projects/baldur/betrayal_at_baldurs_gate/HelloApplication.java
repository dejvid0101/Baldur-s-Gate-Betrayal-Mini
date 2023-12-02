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
import me.projects.baldur.betrayal_at_baldurs_gate.classes.*;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("board.fxml"));

        //loader must be loaded before passing values/data
        Parent root = fxmlLoader.load(); //initialize() controller method invoked

        //define controller for stage/scene
        HelloController gameController=fxmlLoader.getController();

        //after data has been prepared, init scene and set width/height
        Scene scene = new Scene(root, 1200.0, 600.0);

        //set stage...
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        //update docs
        ClassInfoUtilz.printClassInfo(gameController.getClass());

    }

    public static void main(String[] args) throws IOException {

        String player=args[0];

        if(player.equals(Player.PLAYER1.name())){
            System.out.println("CALIENTE");
            sendToServer();
        }
        if(player.equals(Player.PLAYER2.name())) {
            System.out.println("SERVERANGE");
            listenToRequests();
        }

        launch();
    }

    private static void sendToServer() {
        try {
            Socket player2socket=new Socket("localhost", NetworkConfig.PLAYER2_PORT);

            ObjectOutputStream player2output=new ObjectOutputStream(player2socket.getOutputStream());

            player2output.writeObject(new State(0,0,1,1,14));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void listenToRequests() throws IOException {
        ServerSocket socket=new ServerSocket(NetworkConfig.PLAYER2_PORT);

        System.out.println(socket.getLocalPort());

        Thread thread=new Thread(() -> { // separate listening and printing received messages

            while (true) {
                try (Socket clientSocket = socket.accept();
                     ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

                    State receivedData = (State)in.readObject();
                    System.out.println("Received state from player1: " + receivedData);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread.start();
    }
}