import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PacMan {

    private Blinky blinky;
    private Inky inky;
    private Pinky pinky;
    private Clyde clyde;

    private Player player;

    private Tile[][] ghostBoard;
    private Tile[][] playerBoard;

    public PacMan() throws FileNotFoundException {
        player = new Player();
        ghostBoard = new Tile[Global.BOARD_ROWS][Global.BOARD_COLS];
        playerBoard = new Tile[Global.BOARD_ROWS][Global.BOARD_COLS];
        initializeGhostBoard();
        initializePlayerBoard();
        blinky = new Blinky(1, 1, Direction.RIGHT, 25, 25, true);
        inky = new Inky(1, 27, Direction.DOWN, 25, 25, false);
        pinky = new Pinky(20, 1, Direction.LEFT, 25, 25, false);
        clyde = new Clyde(1, 1, Direction.RIGHT, 25, 25, false);
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

    public Tile[][] getGhostBoard() {
        return ghostBoard;
    }

    public Tile[][] getPlayerBoard() {
        return playerBoard;
    }

    private void initializeGhostBoard() throws FileNotFoundException {
        File file = new File("C:/Users/Sanmitr/Documents/IdeaProjects/Pacman/src/ghostMap.txt");
        Scanner scanner = new Scanner(file);
        int row = Global.BOARD_ROWS - 1;
        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            for (int col = 0; col < str.length(); col++) {
                char value = str.charAt(col);
                int num = Integer.parseInt(String.valueOf(value));
                ghostBoard[row][col] = Tile.values()[num];
            }
            row--;
        }
    }

    private void initializePlayerBoard() throws FileNotFoundException {
        File file = new File("C:/Users/Sanmitr/Documents/IdeaProjects/Pacman/src/playerMap.txt");
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
        if(player.getConsumedSmallPellets() > 30 && !inky.isReleased()){
            inky.release();
        }
        blinky.update(ghostBoard, player);
        inky.update(ghostBoard, player, blinky);
        pinky.update(ghostBoard, player);
        clyde.update(ghostBoard, player, blinky);
    }

}
