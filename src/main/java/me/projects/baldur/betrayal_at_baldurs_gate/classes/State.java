package me.projects.baldur.betrayal_at_baldurs_gate.classes;

import java.io.Serializable;

public class State implements Serializable {
    private double player1CardLayoutX;
    private double player2CardLayoutX;

    private int currentPlayer;
    private int movesSinceStart;
    private int steps;

    public State(double player1CardLayoutX, double player2CardLayoutX, int currentPlayer, int movesSinceStart, int steps) {
        this.player1CardLayoutX = player1CardLayoutX;
        this.player2CardLayoutX = player2CardLayoutX;
        this.currentPlayer=currentPlayer;
        this.movesSinceStart = movesSinceStart;
        this.steps = steps;
    }

    public double getPlayer1CardLayoutX() {
        return player1CardLayoutX;
    }

    public void setPlayer1CardLayoutX(double player1CardLayoutX) {
        this.player1CardLayoutX = player1CardLayoutX;
    }

    public double getPlayer2CardLayoutX() {
        return player2CardLayoutX;
    }

    public double getAnyPlayerCardLayoutX(int playerNr) {
        if (playerNr==1){
            return player1CardLayoutX;
        } else if (playerNr==2) {
            return player2CardLayoutX;
        } else return 0.0;
    }

    public void setAnyPlayerCardLayoutX(int playerNr, double playerCardLayoutX) {
        if (playerNr==1){
            player1CardLayoutX=playerCardLayoutX;
        } else if (playerNr==2) {
            player2CardLayoutX=playerCardLayoutX;
        }
    }

    public void setPlayer2CardLayoutX(double player2CardLayoutX) {
        this.player2CardLayoutX = player2CardLayoutX;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getMovesSinceStart() {
        return movesSinceStart;
    }

    public void setMovesSinceStart(int movesSinceStart) {
        this.movesSinceStart = movesSinceStart;
    }

    public void increaseMovesSinceStart() {
        this.movesSinceStart++;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "State{" +
                "player1CardLayoutX=" + player1CardLayoutX +
                ", player2CardLayoutX=" + player2CardLayoutX +
                ", movesSinceStart=" + movesSinceStart +
                ", steps=" + steps +
                '}';
    }
}
