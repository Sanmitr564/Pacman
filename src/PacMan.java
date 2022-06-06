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
    private MovementMode queuedMovementMode;
    private boolean isGameOver;

    public PacMan(){
        player = new Player();
        ghostBoard = new Tile[Global.GHOST_MAP.length][Global.GHOST_MAP[0].length];
        playerBoard = new Tile[Global.PLAYER_BOARD.length][Global.PLAYER_BOARD[0].length];
        clone2DArray(ghostBoard, Global.GHOST_MAP);
        clone2DArray(playerBoard, Global.PLAYER_BOARD);
        blinky = new Blinky(14, 19, Direction.RIGHT, 0, 11, true, false);
        pinky = new Pinky(14, 16, Direction.UP, 0, 12, false, true);
        inky = new Inky(12, 16, Direction.UP, 0, 12, false, false);
        clyde = new Clyde(16, 16, Direction.UP, 0, 12, false, false);
        ghosts = new Ghost[]{blinky, pinky, inky, clyde};
        timer = new Timer();
        timer.start();
        isStarted = false;
        isGameOver = false;
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

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(Boolean b) {
        isStarted = b;
    }

    public Ghost[] getGhosts() {
        return ghosts;
    }

    public void start() {
        isStarted = true;
    }

    public Tile[][] getGhostBoard() {
        return ghostBoard;
    }

    public Tile[][] getPlayerBoard() {
        return playerBoard;
    }

    public void update() {
        if (isStarted) {
            player.move(playerBoard);
            if (player.getPelletsConsumedThisCycle() >= 7) {
                pinky.release();
            }
            if (player.getConsumedSmallPellets() >= 30 && player.getNumLives() == 3) {
                inky.release();
            }
            if (player.getPelletsConsumedThisCycle() >= 17 && player.getNumLives() < 3) {
                inky.release();
            }
            if (player.getConsumedSmallPellets() >= 60 && player.getNumLives() == 3) {
                clyde.release();
            }
            if (player.getPelletsConsumedThisCycle() >= 32 && player.getNumLives() < 3) {
                clyde.release();
            }
            blinky.update(ghostBoard, player, (Blinky) blinky);
            inky.update(ghostBoard, player, (Blinky) blinky);
            pinky.update(ghostBoard, player, (Blinky) blinky);
            clyde.update(ghostBoard, player, (Blinky) blinky);
            for (Ghost g : ghosts) {
                if (g.getX() == player.getX() && g.getY() == player.getY()) {
                    if (player.isEnergized()) {
                        g.eat();
                        player.eatGhost();
                    } else {
                        if (player.hit()) {
                            isGameOver = true;
                            return;
                        }
                        softReset();
                        break;
                    }
                }
                switch (timer.getSeconds()) {
                    case 7, 34, 59, 84 -> queuedMovementMode = MovementMode.CHASE;
                    case 0, 27, 54, 79 -> queuedMovementMode = MovementMode.SCATTER;
                }
                if (!player.isEnergized()) {
                    for (Ghost j : ghosts) {
                        j.setMovementMode(queuedMovementMode);
                    }
                }else{
                    for (Ghost j : ghosts) {
                        j.setMovementMode(MovementMode.FRIGHTENED);
                    }
                }
            }
            timer.iterate();
        }

    }

    private void softReset() {
        player.softReset();
        blinky = new Blinky(14, 19, Direction.RIGHT, 0, 11, true, false);
        pinky = new Pinky(14, 16, Direction.UP, 0, 12, false, false);
        inky = new Inky(12, 16, Direction.UP, 0, 12, false, false);
        clyde = new Clyde(16, 16, Direction.UP, 0, 12, false, false);
        ghosts = new Ghost[]{blinky, pinky, inky, clyde};
        timer = new Timer();
        timer.start();
        isStarted = false;
    }

    private <T> void clone2DArray(T[][] copyArray, T[][] source){
        for(int row = 0; row < source.length; row++){
            System.arraycopy(source[row], 0, copyArray[row], 0, source[row].length);
        }
    }
}
