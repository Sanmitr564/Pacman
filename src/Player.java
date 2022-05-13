public class Player {

    private int x;
    private int y;
    private Direction direction;
    private Direction queuedDirection;
    private int section;

    public Player() {
        x = 1;
        y = 1;
        direction = Direction.LEFT;
        queuedDirection = Direction.RIGHT;
        section = Global.PLAYER_TILE_SECTIONS / 2;
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
        if (section == Global.PLAYER_TILE_SECTIONS / 2) {
            if (direction != queuedDirection) {
                tryChangeDirection(board);
            }
            Tile t = tryGetTile(board, direction);
            if ( t == Tile.WALL || t == null) {
                return;
            }
        } else if (DirectionHelp.oppositeDirections(direction, queuedDirection)) {
            tryChangeDirection(board);
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

        System.out.println("Section: " + section);
        System.out.println("X: " + x);
        System.out.println("Y: " + y);

    }

    private int[] getOffsets(Direction d) {
        //[rowOffset][colOffset]
        switch (d) {
            case DOWN -> {
                return new int[]{-1, 0};
            }
            case LEFT -> {
                return new int[]{0, -1};
            }
            case RIGHT -> {
                return new int[]{0, 1};
            }
            default -> {
                return new int[]{1, 0};
            }
        }
    }

    private void tryChangeDirection(Tile[][] board) {
        Tile t = tryGetTile(board);
        if (t != Tile.WALL && t != null) {
            direction = queuedDirection;
        }
    }

    private Tile tryGetTile(Tile[][] board) {
        try {
            int[] offset = getOffsets(queuedDirection);
            return board[y + offset[0]][x + offset[1]];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    private Tile tryGetTile(Tile[][] board, Direction d) {
        try {
            int[] offset = getOffsets(d);
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
}
