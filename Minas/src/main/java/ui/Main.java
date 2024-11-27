package ui;

import javafx.application.Application;
import javafx.scene.Scene;
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
    private static final int TILE_SIZE = 50;





    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Graph<String> graph = new AdjacencyListGraph<>();

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                String nodeName = "Node" + i + "_" + j;
                graph.addVertex(nodeName);
            }
        }

        game = new Game(graph, GRID_SIZE, GRID_SIZE);

        GridPane grid = new GridPane();


        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                final int row = i;
                final int col = j;
                Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE, Color.BLUE);
                tile.setStroke(Color.BLACK);

                tile.setOnMouseClicked(event -> {
                    String nodeName = game.getNodeName(row, col);

                    if (game.isMine(nodeName)) {
                        tile.setFill(Color.BLACK);
                    } else {
                        tile.setFill(Color.GREEN);
                    }

                    game.movePlayer(row, col);

                    if (game.hasReachedGoal()) {
                        System.out.println("¡Has alcanzado la meta!");
                    }
                });


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
}


