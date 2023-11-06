package me.projects.baldur.betrayal_at_baldurs_gate.classes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtilz {

public static final String GAME_STATE_FILE="serializedState.dat";
public static final String HTML_DOCS_FILE="htmlDocs.html";

public static void saveGameToFile(State gameState){
    try (FileOutputStream fileOut = new FileOutputStream(GAME_STATE_FILE);
         ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

        System.out.println(gameState);

        // Write the object to the file
        objectOut.writeObject(gameState);


        //ovo radi
        System.out.println("Object has been serialized.");
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public static State loadGameFromFile(){
        try (FileInputStream fileIn = new FileInputStream(GAME_STATE_FILE);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

            // Read the object from the file
            return (State) objectIn.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new State(0,0,0,0,0);
        }
    }

    //concatenates and saves html to file
    public static void printToHtmlFile(List<String> items){
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<html><head><title>Generated HTML</title></head><body>");

        for (String item : items) {
            htmlContent.append("<p>").append(item).append("</p>");
        }

        htmlContent.append("</body></html>");

        try (FileWriter fileWriter = new FileWriter(HTML_DOCS_FILE)) {
            fileWriter.write(htmlContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
