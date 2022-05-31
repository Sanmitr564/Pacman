public class Player {

    private int x;
    private int y;
    private Direction direction;
    private Direction queuedDirection;
    private int section;

    private int consumedSmallPellets;
    private int numLives;
    private boolean isEnergized;
    private Timer energizeTimer;

    public Player() {
        x = 14;
        y = 7;
        direction = Direction.LEFT;
        queuedDirection = Direction.LEFT;
        section = Global.PLAYER_TILE_SECTIONS / 2;
        consumedSmallPellets = 0;
        numLives = 3;
        isEnergized = false;
        energizeTimer = new Timer();
    }

    //<editor-fold desc="setters & getters">
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setQueuedDirection(Direction queuedDirection) {
        this.queuedDirection = queuedDirection;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    //</editor-fold>

    public void move(Tile[][] board) {
        if (energizeTimer.getSeconds() == 5) {
            energizeTimer.reset();
            isEnergized = false;
        }
        if (section == Global.PLAYER_TILE_SECTIONS / 2) {
            if (board[y][x] == Tile.TELEPORT) {
                switch (x) {
                    case 0 -> x = 27;
                    case 27 -> x = 0;
                }
            }
            if (direction != queuedDirection) {
                tryChangeDirection(board);
            }
            Tile t = tryGetTile(board, direction);
            if (t == Tile.WALL || t == null) {
                return;
            }
        } else if (DirectionHelp.oppositeDirections(direction, queuedDirection)) {
            tryChangeDirection(board);
        }
        if (board[y][x] == Tile.SMALL_PELLET) {
            consumeSmallPellet(board);
            return;
        } else if (board[y][x] == Tile.BIG_PELLET) {
            isEnergized = true;
            energizeTimer.start();
        }

        switch (direction) {
            case UP, RIGHT -> section++;
            case LEFT, DOWN -> section--;
        }

        if (section >= Global.PLAYER_TILE_SECTIONS || section < 0) {
            switch (direction) {
                case RIGHT -> x++;
                case LEFT -> x--;
                case UP -> y++;
                case DOWN -> y--;
            }
            section = section < 0 ? section + Global.PLAYER_TILE_SECTIONS : section % Global.PLAYER_TILE_SECTIONS;

        }
    }

    public void consumeSmallPellet(Tile[][] board) {
        consumedSmallPellets++;
        board[y][x] = Tile.STRAIGHT;
        System.out.println(consumedSmallPellets);
    }

    public int getConsumedSmallPellets() {
        return consumedSmallPellets;
    }

    private void tryChangeDirection(Tile[][] board) {
        Tile t = tryGetTile(board);
        if (t != Tile.WALL && t != null) {
            direction = queuedDirection;
        }
    }

    private Tile tryGetTile(Tile[][] board) {
        try {
            int[] offset = DirectionHelp.getOffsets(queuedDirection);
            return board[y + offset[0]][x + offset[1]];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    private Tile tryGetTile(Tile[][] board, Direction d) {
        try {
            int[] offset = DirectionHelp.getOffsets(d);
            return board[y + offset[0]][x + offset[1]];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public float[] getTileOffsets() {//{xOffset, yOffset}
        return switch (direction) {
            case DOWN, UP ->
                    new float[]{Global.TILE_SIZE / 2f, (float) section / Global.PLAYER_TILE_SECTIONS * Global.TILE_SIZE};
            case LEFT, RIGHT ->
                    new float[]{(float) section / Global.PLAYER_TILE_SECTIONS * Global.TILE_SIZE, Global.TILE_SIZE / 2f};
        };
    }

    public boolean hit() {
        numLives--;
        return numLives == 0;
    }
}
