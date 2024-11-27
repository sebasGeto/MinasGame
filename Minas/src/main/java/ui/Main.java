package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Game;
import model.Graph;
import model.AdjacencyListGraph;

public class Main extends Application {
    private Game game;
    private static final int GRID_SIZE = 5;
    private static final int TILE_SIZE = 80;

    private boolean gameOver = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Graph<String> graph = new AdjacencyListGraph<>();

        // Crear los vértices del grafo
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                String nodeName = "Node" + i + "_" + j;
                graph.addVertex(nodeName);
            }
        }

        game = new Game(graph, GRID_SIZE, GRID_SIZE);

        GridPane grid = new GridPane();

        // Cargar las imágenes
        Image bombImage = new Image(getClass().getResourceAsStream("/images/bomba.png"));
        Image diamondImage = new Image(getClass().getResourceAsStream("/images/diamante.png"));

        // Crear la cuadrícula
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                final int row = i;
                final int col = j;

                Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE, Color.BLUE);
                tile.setStroke(Color.BLACK);

                tile.setOnMouseClicked(event -> {
                    if (gameOver) return;

                    String nodeName = game.getNodeName(row, col);

                    if (game.isMine(nodeName)) {
                        // Mostrar imagen de bomba y terminar el juego
                        grid.add(new ImageView(bombImage), col, row);
                        tile.setFill(Color.BLACK);
                        endGame(false);
                    } else {
                        // Mostrar imagen de diamante
                        grid.add(new ImageView(diamondImage), col, row);
                        tile.setFill(Color.GREEN);
                        game.movePlayer(row, col);

                        if (game.hasReachedGoal()) {
                            endGame(true);
                        }
                    }
                });

                // Indicar posición inicial y meta
                if (game.getPlayerPosition().equals(game.getNodeName(i, j))) {
                    tile.setFill(Color.YELLOW);
                }
                if (game.getGoalPosition().equals(game.getNodeName(i, j))) {
                    tile.setFill(Color.RED);
                }

                grid.add(tile, j, i);
            }
        }

        Scene scene = new Scene(grid, TILE_SIZE * GRID_SIZE, TILE_SIZE * GRID_SIZE);
        primaryStage.setTitle("Juego de Minas");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void endGame(boolean hasWon) {
        gameOver = true;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (hasWon) {
            alert.setTitle("¡Victoria!");
            alert.setHeaderText("¡Felicitaciones!");
            alert.setContentText("Has alcanzado la meta con éxito. ¡Bien hecho!");
        } else {
            alert.setTitle("Derrota");
            alert.setHeaderText("¡Juego Terminado!");
            alert.setContentText("Has perdido porque pisaste una mina. Intenta nuevamente.");
        }
        alert.showAndWait();
    }
}