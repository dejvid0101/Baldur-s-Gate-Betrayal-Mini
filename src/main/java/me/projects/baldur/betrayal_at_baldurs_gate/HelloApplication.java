package me.projects.baldur.betrayal_at_baldurs_gate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.projects.baldur.betrayal_at_baldurs_gate.classes.Adventurer;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("adventurer-card.fxml"));



        //loader must be loaded before passing values/data
        Parent root = fxmlLoader.load(); //initialize() controller method invoked

        //define controller for stage/scene
        //HelloController ctrlr=fxmlLoader.getController();
        //Adventurer adventurer = new Adventurer("Tasha");
        //ctrlr.setAdventurer(adventurer);

        //after data has been prepared, init scene
        Scene scene = new Scene(root);

        //set stage...
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}