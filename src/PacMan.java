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

    public PacMan(){
        player = new Player();
        ghostBoard = Global.GHOST_MAP;
        playerBoard = Global.PLAYER_BOARD;
        blinky = new Blinky(14, 19, Direction.RIGHT, 0, 11, true, false);
        pinky = new Pinky(14, 16, Direction.UP, 0, 12, false, true);
        inky = new Inky(12, 16, Direction.UP, 0, 12, false, false);
        clyde = new Clyde(16, 16, Direction.UP, 0, 12, false, false);
        ghosts = new Ghost[]{blinky, pinky, inky, clyde};
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

    public void setStarted(Boolean b) {
        isStarted = b;
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
                    } else {
                        if (player.hit()) {
                            System.out.println("cool");
                        }
                        softReset();
                        break;
                    }
                }
                MovementMode m = null;
                switch (timer.getSeconds()) {
                    case 7, 34, 59, 84 -> m = MovementMode.CHASE;
                    case 0, 27, 54, 79 -> m = MovementMode.SCATTER;
                }
                if (m != null) {
                    for (Ghost j : ghosts) {
                        j.setMovementMode(m);
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

}
