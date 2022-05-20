import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public class Ghost {

    private int x;
    private int y;
    private Texture texture;
    private Direction direction;

    private int section;
    private int numSections;

    public Ghost(int x, int y, Direction direction, int section, int numSections) {
        this.x = x;
        this.y = y;
        //this.texture = texture;
        this.section = section;
        this.direction = direction;
        this.numSections = numSections;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void update(Tile[][] board, Player player) {
        updateDirection(board, player.getY(), player.getX());
        move();
    }

    protected void move() {
        switch (direction) {
            case UP, RIGHT -> section++;
            case LEFT, DOWN -> section--;
        }

        if (section >= numSections || section < 0) {
            switch (direction) {
                case RIGHT -> x++;
                case LEFT -> x--;
                case UP -> y++;
                case DOWN -> y--;
            }
            section = section < 0 ? section + numSections : section % numSections;
        }
    }

    protected void updateDirection(Tile[][] board, Player player) {
        if (section == numSections / 2) {
            if (board[y][x] == Tile.TELEPORT) {
                switch (x) {
                    case 0 -> x = 27;
                    case 27 -> x = 0;
                }
            } else if (board[y][x] == Tile.JUNCTION) {
                direction = getDirectionAtJunction(board, player);
            } else if (board[y][x] == Tile.SPECIAL_JUNCTION) {
                direction = getDirectionAtSpecialJunction(board, player);
            } else if (tryGetTile(board) == null || tryGetTile(board) == Tile.WALL) {
                direction = getValidDirections(board)[0];
            }
        }
    }

    protected void updateDirection(Tile[][] board, int targetRow, int targetCol){
        if (section == numSections / 2) {
            if (board[y][x] == Tile.TELEPORT) {
                switch (x) {
                    case 0 -> x = 27;
                    case 27 -> x = 0;
                }
            } else if (board[y][x] == Tile.JUNCTION) {
                direction = getDirectionAtJunction(board, targetRow, targetCol);
            } else if (board[y][x] == Tile.SPECIAL_JUNCTION) {
                direction = getDirectionAtSpecialJunction(board, targetRow, targetCol);
            } else if (tryGetTile(board) == null || tryGetTile(board) == Tile.WALL) {
                direction = getValidDirections(board)[0];
            }
        }
    }

    private Tile tryGetTile(Tile[][] board) {
        try {
            int[] offset = DirectionHelp.getOffsets(direction);
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

    private Direction[] getValidDirections(Tile[][] board) {
        List<Direction> directions = new ArrayList<>();
        for (int i = 0; i < Direction.values().length; i++) {
            if (Direction.values()[i] == DirectionHelp.getOppositeDirection(direction)) {
                continue;
            }
            Tile t = tryGetTile(board, Direction.values()[i]);
            if (t != Tile.WALL && t != null) {
                directions.add(Direction.values()[i]);
            }
        }
        Direction[] d = new Direction[directions.size()];
        directions.toArray(d);
        return d;
    }

    protected Direction getDirectionAtJunction(Tile[][] board, Player player) {
        Direction[] validDirections = getValidDirections(board);
        Direction d = validDirections[0];
        for (int i = 1; i < validDirections.length; i++) {
            if (getDistance(d, player) < getDistance(validDirections[i], player)) {
                d = validDirections[i];
            }
        }
        return d;
    }

    protected Direction getDirectionAtJunction(Tile[][] board, int targetRow, int targetCol) {
        Direction[] validDirections = getValidDirections(board);
        Direction d = validDirections[0];
        for (int i = 1; i < validDirections.length; i++) {
            if (getDistance(d, targetRow, targetCol) < getDistance(validDirections[i], targetRow, targetCol)) {
                d = validDirections[i];
            }
        }
        return d;
    }

    protected Direction getDirectionAtSpecialJunction(Tile[][] board, Player player) {
        Direction[] validDirections = getValidSpecialDirections(board);
        Direction d = validDirections[0];
        for (int i = 1; i < validDirections.length; i++) {
            if (getDistance(d, player) < getDistance(validDirections[i], player)) {
                d = validDirections[i];
            }
        }
        return d;
    }

    protected Direction getDirectionAtSpecialJunction(Tile[][] board, int targetRow, int targetCol) {
        Direction[] validDirections = getValidSpecialDirections(board);
        Direction d = validDirections[0];
        for (int i = 1; i < validDirections.length; i++) {
            if (getDistance(d, targetRow, targetCol) < getDistance(validDirections[i], targetRow, targetCol)) {
                d = validDirections[i];
            }
        }
        return d;
    }

    protected Direction[] getValidSpecialDirections(Tile[][] board) {
        List<Direction> directions = new ArrayList<>();
        for (int i = 0; i < Direction.values().length; i++) {
            if (
                    Direction.values()[i] == DirectionHelp.getOppositeDirection(direction) ||
                            Direction.values()[i] == Direction.UP
            ) {
                continue;
            }
            Tile t = tryGetTile(board, Direction.values()[i]);
            if (t != Tile.WALL && t != null) {
                directions.add(Direction.values()[i]);
            }
        }
        Direction[] d = new Direction[directions.size()];
        directions.toArray(d);
        return d;
    }

    protected int getDistance(Direction d, Player player) {
        int[] offsets = DirectionHelp.getOffsets(d);
        return ((player.getX() - x + offsets[1]) * (player.getX() - x + offsets[1])) + (player.getY() - y + offsets[0]) * (player.getY() - y + offsets[0]);
    }

    protected int getDistance(Direction d, int targetRow, int targetCol) {
        int[] offsets = DirectionHelp.getOffsets(d);
        return ((targetCol - x + offsets[1]) * (targetCol - x + offsets[1])) + (targetRow - y + offsets[0]) * (targetRow - y + offsets[0]);
    }

    public float[] getTileOffsets() {//{xOffset, yOffset}
        return switch (direction) {
            case DOWN, UP -> new float[]{Global.TILE_SIZE / 2f, (float) section / numSections * Global.TILE_SIZE};
            case LEFT, RIGHT -> new float[]{(float) section / numSections * Global.TILE_SIZE, Global.TILE_SIZE / 2f};
        };
    }
}
