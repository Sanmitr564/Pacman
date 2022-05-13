import com.badlogic.gdx.graphics.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class PacMan {

    private Ghost Blinky;
    private Ghost Inky;
    private Ghost Pinky;
    private Ghost Clyde;

    private Player player;

    private Tile[][] board;

    public PacMan() throws FileNotFoundException {
        player = new Player();
        board = new Tile[Global.BOARD_ROWS][Global.BOARD_COLS];
        initializeBoard();
    }

    public Player getPlayer() {
        return player;
    }

    public Tile[][] getBoard() {
        return board;
    }

    private void initializeBoard() throws FileNotFoundException {
        File file = new File("C:/Users/Sanmitr/Documents/IdeaProjects/Pacman/src/ghostMap.txt");
        Scanner scanner = new Scanner(file);
        int row = Global.BOARD_ROWS-1;
        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            for (int col = 0; col < str.length(); col++) {
                char value = str.charAt(col);
                int num = Integer.parseInt(String.valueOf(value));
                board[row][col] = Tile.values()[num];
            }
            row--;
        }
    }

    public void update() {
        player.move(board);
    }

}
