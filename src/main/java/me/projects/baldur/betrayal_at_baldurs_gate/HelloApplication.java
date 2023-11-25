package me.projects.baldur.betrayal_at_baldurs_gate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Screen;
import javafx.stage.Stage;
import me.projects.baldur.betrayal_at_baldurs_gate.classes.Adventurer;
import me.projects.baldur.betrayal_at_baldurs_gate.classes.ClassInfoUtilz;
import me.projects.baldur.betrayal_at_baldurs_gate.classes.NetworkConfig;
import me.projects.baldur.betrayal_at_baldurs_gate.classes.Player;

import java.io.IOException;
import java.net.ServerSocket;

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

        if(player.equals(Player.PLAYER1.name())) System.out.println("CALIENTE");
        if(player.equals(Player.PLAYER2.name())) {
            System.out.println("SERVERANGE");  listenToRequests();
        }

        launch();
    }

    private static void listenToRequests() throws IOException {
        ServerSocket socket=new ServerSocket(NetworkConfig.PLAYER2_PORT);

        System.out.println(socket.getLocalPort());
    }
}