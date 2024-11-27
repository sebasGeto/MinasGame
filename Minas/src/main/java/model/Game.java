package model;

import java.util.*;

public class Game {
    private Graph<String> graph;
    private int rows;
    private int cols;
    private Set<String> mines;
    private String playerPosition;
    private String goalPosition;

    public Game(Graph<String> graph, int rows, int cols) {
        this.graph = graph;
        this.rows = rows;
        this.cols = cols;
        this.mines = new HashSet<>();
        this.playerPosition = getNodeName(0, 0);
        this.goalPosition = getNodeName(rows - 1, cols - 1);

        placeMines();
    }

    private void placeMines() {
        for (int i = 0; i < 5; i++) { // Colocamos 5 minas
            int row = (int) (Math.random() * rows);
            int col = (int) (Math.random() * cols);
            String nodeName = getNodeName(row, col);
            mines.add(nodeName);
        }
    }

    public String getNodeName(int row, int col) {
        return "Node" + row + "_" + col;
    }

    public boolean isMine(String nodeName) {
        return mines.contains(nodeName);
    }

    public boolean hasReachedGoal() {
        return playerPosition.equals(goalPosition);
    }

    public void movePlayer(int row, int col) {
        String newPosition = getNodeName(row, col);
        if (isMine(newPosition)) {
            System.out.println("¡Has pisado una mina! Fin del juego.");
        } else {
            playerPosition = newPosition;
            System.out.println("Jugador se movió a: " + newPosition);
        }
    }

    public String getPlayerPosition() {
        return playerPosition;
    }

    public String getGoalPosition() {
        return goalPosition;
    }
}




