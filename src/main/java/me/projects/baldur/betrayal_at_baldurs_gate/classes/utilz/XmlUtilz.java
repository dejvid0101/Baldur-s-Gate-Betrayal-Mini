package me.projects.baldur.betrayal_at_baldurs_gate.classes.utilz;

import me.projects.baldur.betrayal_at_baldurs_gate.classes.State;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class XmlUtilz {

    private static final String FILENAME = "gameMoves.xml";

    public static void saveGameMove(State gameMove) {

        List<State> gameMoveList = new ArrayList<>(); // sort this

        if(Files.exists(Path.of(FILENAME))) {
            gameMoveList.addAll(XmlUtilz.readAllGameMoves());
        }

        gameMoveList.add(gameMove);

        System.out.println(gameMoveList);

        try {
            Document document = createDocument("gameMoves");
            for(State gm : gameMoveList) {
                Element gameMoves = document.createElement("gameMove");
                document.getDocumentElement().appendChild(gameMoves);

                gameMoves.appendChild(createElement(document, "player1CardLayoutX", String.format("%.2f", gm.getPlayer1CardLayoutX())));
                gameMoves.appendChild(createElement(document, "player2CardLayoutX", String.format("%.2f", gm.getPlayer2CardLayoutX())));
                gameMoves.appendChild(createElement(document, "currentPlayer", String.format("%d", gm.getCurrentPlayer())));
                gameMoves.appendChild(createElement(document, "movesSinceStart", String.format("%d", gm.getMovesSinceStart())));

            }

            saveDocument(document, FILENAME);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public static void clearReplayFile() {
        // Load the existing XML file
        try (FileOutputStream outputStream = new FileOutputStream(FILENAME)) {
            outputStream.getChannel().truncate(0);
            System.out.println("File content set to empty.");
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Document createDocument(String element) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation domImplementation = builder.getDOMImplementation();
        return domImplementation.createDocument(null, element, null);
    }

    private static Node createElement(Document document, String tagName, String data) {
        Element element = document.createElement(tagName);
        Text text = document.createTextNode(data);
        element.appendChild(text);
        return element;
    }

    private static void saveDocument(Document document, String fileName) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(document), new StreamResult(new File(FILENAME)));
    }

    public static List<State> readAllGameMoves() {
        List<State> gameMoves = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(FILENAME));

            Element documentElement = document.getDocumentElement();
            System.out.println("Pročitao sam: " + documentElement.getTagName());

            NodeList gameMovesChildList = documentElement.getChildNodes();

            for(int i = 0; i < gameMovesChildList.getLength(); i++) {

                Node gameMoveNode = gameMovesChildList.item(i);

                if(gameMoveNode.getNodeType() == Node.ELEMENT_NODE) {

                    double player1CardLayoutX=0;
                    double player2CardLayoutX=0;
                    int currentPlayer=1;
                    int movesSinceStart=0;

                    Element gameMoveElement = (Element) gameMoveNode;
                    System.out.println("Pročitao sam: " + gameMoveElement.getTagName());

                    NodeList gameMoveChildList = gameMoveElement.getChildNodes();

                    for(int j = 0; j < gameMoveChildList.getLength(); j++) {

                        Node gameMoveChildNode = gameMoveChildList.item(j);

                        if(gameMoveChildNode.getNodeType() == Node.ELEMENT_NODE) {

                            Element gameMoveChildElement = (Element) gameMoveChildNode;

                            switch (gameMoveChildElement.getTagName()) {
                                case "player1CardLayoutX" -> player1CardLayoutX = Double.parseDouble(gameMoveChildElement.getTextContent());
                                case "player2CardLayoutX" -> player2CardLayoutX = Double.parseDouble(gameMoveChildElement.getTextContent());
                                case "currentPlayer" -> currentPlayer = Integer.parseInt(gameMoveChildElement.getTextContent());
                                case "movesSinceStart" -> movesSinceStart = Integer.parseInt(gameMoveChildElement.getTextContent());

                            }
                        }
                    }

                    State newGameMove = new State(player1CardLayoutX, player2CardLayoutX, currentPlayer, movesSinceStart, 0);
                    gameMoves.add(newGameMove);
                }
            }
        }
        catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace();
        }


        return gameMoves;
    }

}
