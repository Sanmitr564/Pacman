import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PacMan {

    private Ghost blinky;
    private Ghost inky;
    private Ghost pinky;
    private Ghost clyde;

    private Player player;

    private final Tile[][] ghostBoard;
    private final Tile[][] playerBoard;
    private Timer timer;
    private Ghost[] ghosts;
    private boolean isStarted;

    public PacMan() throws FileNotFoundException {
        player = new Player();
        ghostBoard = new Tile[Global.BOARD_ROWS][Global.BOARD_COLS];
        playerBoard = new Tile[Global.BOARD_ROWS][Global.BOARD_COLS];
        initializeGhostBoard();
        initializePlayerBoard();
        blinky = new Blinky(14, 19, Direction.RIGHT, 15, 15, true, false);
        pinky = new Pinky(14, 16, Direction.UP, 0, 15, false, true);
        inky = new Inky(12, 16, Direction.UP, 0, 15, false, false);
        clyde = new Clyde(16, 16, Direction.UP, 0, 15, false, false);
        ghosts = new Ghost[] {blinky, pinky, inky, clyde};
        timer = new Timer();
        timer.start();
        isStarted = false;
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

    public Ghost getClyde() {
        return clyde;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(Boolean b){
        isStarted = b;
    }

    public void start(){
        isStarted = true;
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
        if (isStarted) {
            player.move(playerBoard);
            if(player.getConsumedSmallPellets() > 30){
                inky.release();
            }
            if(player.getConsumedSmallPellets() > 60){
                clyde.release();
            }
            blinky.update(ghostBoard, player, (Blinky)blinky);
            inky.update(ghostBoard, player, (Blinky)blinky);
            pinky.update(ghostBoard, player, (Blinky)blinky);
            clyde.update(ghostBoard, player, (Blinky)blinky);
            for(Ghost g : ghosts){
                if(g.getX() == player.getX() && g.getY() == player.getY()){
                    if(player.hit()){
                        System.out.println("cool");
                    }
                    for(Ghost j : ghosts){
                        //j.setMovementMode(MovementMode.Scatter);
                    }
                    break;
                }
                MovementMode m = null;
                switch (timer.getSeconds()){
                    case 7, 34, 59, 84 -> m = MovementMode.Chase;
                    case 0, 27, 54, 79 -> m = MovementMode.Scatter;
                }
                if(m != null){
                    for(Ghost j : ghosts){
                        j.setMovementMode(m);
                    }
                }
            }
            timer.iterate();
        }

    }

}
