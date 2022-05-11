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
        for (Color[] arr : testBoard) {
            Arrays.fill(arr, Color.WHITE);
        }
        testBoard[0][0] = Color.YELLOW;
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

    }
}
