import com.badlogic.gdx.graphics.Color;

import java.util.Arrays;

public class PacMan {

    private Ghost Blinky;
    private Ghost Inky;
    private Ghost Pinky;
    private Ghost Clyde;

    private Player player;

    private Tile[][] board;
    private Color[][] testBoard;

    public PacMan() {
        player = new Player();
        testBoard = new Color[Global.BOARD_ROWS][Global.BOARD_COLS];
        board = new Tile[Global.BOARD_ROWS][Global.BOARD_COLS];
        for (Tile[] arr : board) {
            Arrays.fill(arr, Tile.STRAIGHT);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Tile[][] getBoard() {
        return board;
    }

    public Color[][] getTestBoard() {
        return testBoard;
    }

    public void update(){
        player.move(board);
    }
}
