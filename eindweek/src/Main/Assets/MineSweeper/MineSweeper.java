package Main.Assets.MineSweeper;

import Main.Main;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class MineSweeper extends Application{

    private static final  int TILE_SIZE = 40;
    private static final int W = 800;
    private static final int H = 600;

    private static final int X_TILES = W / TILE_SIZE;
    private static final int Y_TILES = H /TILE_SIZE;

    private Tile[][] grid = new Tile[X_TILES][Y_TILES];
    private Scene mineS;


    public MineSweeper() {

    }

    private Parent createContent()
    {
        Pane root = new Pane();
        root.setPrefSize(W, H);
        root.setStyle("-fx-background-color: white");

        for (int y = 0; y < Y_TILES; y++)
        {
            for (int x = 0; x < X_TILES; x++)
            {
                Tile tile = new Tile(x, y, Math.random() < 0.2);

                grid[x][y] = tile;
                root.getChildren().add(tile);
            }
        }

        for (int y = 0; y < Y_TILES; y++)
        {
            for (int x = 0; x < X_TILES; x++)
            {
                Tile tile = grid[x][y];

                if (tile.hasBomb)
                    continue;

                long bombs = getNeighbors(tile).stream().filter(t -> t.hasBomb).count();

                if (bombs > 0)
                    tile.text1.setText(String.valueOf(bombs));
            }
        }

        return root;
    }

    private List<Tile> getNeighbors(Tile tile)
    {
        List<Tile> neighbors = new ArrayList<>();

        int[] points =  new int[] {
                -1, -1,
                -1, 0,
                -1, 1,
                0, -1,
                0, 1,
                1, -1,
                1, 0,
                1, 1
        };

        for (int i = 0; i < points.length; i++)
        {
            int dx = points[i];
            int dy = points[++i];

            int newX = tile.x + dx;
            int newY = tile.y + dy;

            if (newX >= 0 && newX < X_TILES
                    && newY >= 0 && newY < Y_TILES) {
            neighbors.add(grid[newX][newY]);
            }
        }

        return neighbors;
    }

    private class Tile extends StackPane
    {
        private int x, y;
        private boolean hasBomb;
        private boolean isOpen = false;

        private Rectangle border = new Rectangle(TILE_SIZE - 2, TILE_SIZE -2);
        private Text text1 = new Text();

        public Tile(int x, int y, boolean hasBomb)
        {
            this.x = x;
            this.y = y;
            this.hasBomb = hasBomb;

            border.setStroke(Color.LIGHTGRAY);

            text1.setFont(Font.font(18));
            text1.setText(hasBomb ? "X" : "");
            text1.setVisible(false);

            getChildren().addAll(border, text1);

            setTranslateX(x * TILE_SIZE);
            setTranslateY(y * TILE_SIZE);

            setOnMouseClicked(e -> open());
        }

        public void open()
        {
            if (isOpen)
                return;

            if (hasBomb)
            {
                System.out.println("Game Over");
                mineS.setRoot(createContent());
                return;
            }

            isOpen = true;
            text1.setVisible(true);
            border.setFill(null);

            if (text1.getText().isEmpty())
            {
                getNeighbors(this).forEach(Tile::open);
            }
        }

    }

    @Override
    public void start(Stage primaryStage)
    {
        mineS = new Scene(createContent());

        primaryStage.setResizable(false);

        primaryStage.setScene(mineS);
        primaryStage.show();
    }

}
