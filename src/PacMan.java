import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PacMan {

    private Ghost Blinky;
    private Ghost Inky;
    private Ghost Pinky;
    private Ghost Clyde;

    private Player player;

    private Tile[][] playerBoard;

    public PacMan() throws FileNotFoundException {
        player = new Player();
        playerBoard = new Tile[Global.BOARD_ROWS][Global.BOARD_COLS];
        initializeBoard();
        Blinky = new Ghost(1,1, Direction.RIGHT, 25, 25);
    }

    public Player getPlayer() {
        return player;
    }

    public Ghost getBlinky() {
        return Blinky;
    }

    public Tile[][] getPlayerBoard() {
        return playerBoard;
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
                playerBoard[row][col] = Tile.values()[num];
            }
            row--;
        }
    }

    public void update() {
        player.move(playerBoard);
        Blinky.move(playerBoard, player);
    }

}
