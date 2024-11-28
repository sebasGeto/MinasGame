package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Game;
import model.Graph;
import model.AdjacencyListGraph;

public class Main extends Application {
    private Game game;
    private static final int GRID_SIZE = 8;
    private static final int TILE_SIZE = 50;
    private boolean gameOver = false;

    // Cargar las imágenes una sola vez para optimizar
    private final Image bombImage = new Image(getClass().getResourceAsStream("/images/bomba.png"));
    private final Image diamondImage = new Image(getClass().getResourceAsStream("/images/diamante.png"));

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        showStartMenu(primaryStage);
    }

    private void showStartMenu(Stage primaryStage) {
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(20));
        menu.setStyle("-fx-background-color: #ffffff;");

        Label title = new Label("Bienvenido al Juego de Minas");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label instruction = new Label("Selecciona la cantidad de minas:");
        instruction.setStyle("-fx-font-size: 14px;");

        Button btn3Mines = new Button("3 Minas");
        Button btn4Mines = new Button("4 Minas");
        Button btn5Mines = new Button("5 Minas");
        Button btn6Mines = new Button("6 Minas");

        btn3Mines.setOnAction(e -> startGame(primaryStage, 3));
        btn4Mines.setOnAction(e -> startGame(primaryStage, 4));
        btn5Mines.setOnAction(e -> startGame(primaryStage, 5));
        btn6Mines.setOnAction(e -> startGame(primaryStage, 6));

        menu.getChildren().addAll(title, instruction, btn3Mines, btn4Mines, btn5Mines, btn6Mines);

        Scene menuScene = new Scene(menu, 300, 200);
        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Menú de Inicio");
        primaryStage.show();
    }

    private void startGame(Stage primaryStage, int numMines) {
        Graph<String> graph = new AdjacencyListGraph<>();

        // Crear los vértices del grafo
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                String nodeName = "Node" + i + "_" + j;
                graph.addVertex(nodeName);
            }
        }

        game = new Game(graph, GRID_SIZE, GRID_SIZE);
        game.placeMines(numMines);

        GridPane grid = new GridPane();

        // Crear la cuadrícula
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                final int row = i;
                final int col = j;

                Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE, Color.WHITE);
                tile.setStroke(Color.BLACK);

                tile.setOnMouseClicked(event -> {
                    if (gameOver) return;

                    String nodeName = game.getNodeName(row, col);

                    if (game.isMine(nodeName)) {
                        // Mostrar imagen de bomba
                        tile.setFill(null); // Eliminar color de relleno
                        grid.add(createImageView(bombImage), col, row);
                        endGame(false);
                    } else {
                        // Mostrar imagen de diamante en casilla segura
                        tile.setFill(null); // Eliminar color de relleno
                        grid.add(createImageView(diamondImage), col, row);

                        game.movePlayer(row, col);

                        if (game.hasReachedGoal()) {
                            endGame(true);
                        }
                    }
                });

                // Posición inicial del jugador
                if (game.getPlayerPosition().equals(game.getNodeName(i, j))) {
                    tile.setFill(Color.GREEN);
                }
                // Posición de la meta
                if (game.getGoalPosition().equals(game.getNodeName(i, j))) {
                    tile.setFill(Color.RED);
                }

                grid.add(tile, j, i);
            }
        }

        Scene gameScene = new Scene(grid, TILE_SIZE * GRID_SIZE, TILE_SIZE * GRID_SIZE);
        primaryStage.setScene(gameScene);
        primaryStage.setTitle("Juego de Minas - " + numMines + " Minas");
        primaryStage.show();
    }

    private ImageView createImageView(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(TILE_SIZE);
        imageView.setFitHeight(TILE_SIZE);
        return imageView;
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
