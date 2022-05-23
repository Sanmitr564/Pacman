import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PacMan {

    private Blinky blinky;
    private Inky inky;
    private Pinky pinky;
    private Clyde clyde;

    private Player player;

    private Tile[][] playerBoard;

    public PacMan() throws FileNotFoundException {
        player = new Player();
        playerBoard = new Tile[Global.BOARD_ROWS][Global.BOARD_COLS];
        initializeBoard();
        blinky = new Blinky(1, 1, Direction.RIGHT, 25, 25);
        inky = new Inky(1, 27, Direction.DOWN, 25, 25);
        pinky = new Pinky(20, 1, Direction.LEFT, 25, 25);
        clyde = new Clyde(1, 1, Direction.RIGHT, 25, 25);
    }

    public Player getPlayer() {
        return player;
    }

    public Ghost getBlinky() {
        return blinky;
    }

    public Ghost getPinky() {
        return pinky;
    }

    public Ghost getInky() {
        return inky;
    }

    public Clyde getClyde() {
        return clyde;
    }

    public Tile[][] getPlayerBoard() {
        return playerBoard;
    }

    private void initializeBoard() throws FileNotFoundException {
        File file = new File("C:/Users/Sanmitr/Documents/IdeaProjects/Pacman/src/ghostMap.txt");
        Scanner scanner = new Scanner(file);
        int row = Global.BOARD_ROWS - 1;
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
        blinky.update(playerBoard, player);
        inky.update(playerBoard, player, blinky);
        pinky.update(playerBoard, player);
        clyde.update(playerBoard, player, blinky);
    }

}
