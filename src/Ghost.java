import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

abstract public class Ghost {

    private int x;
    private int y;
    private Texture texture;
    private Direction direction;

    private int section;
    private boolean released;
    private int numSections;
    private boolean isTurnaroundQueued;
    private boolean isReleasing;
    private boolean isExiting;
    private int targetCol;
    private int targetRow;
    private MovementMode movementMode;
    public Ghost(int x, int y, Direction direction, int section, int numSections, boolean released, boolean isExiting) {
        this.x = x;
        this.y = y;
        //this.texture = texture;
        this.section = section;
        this.direction = direction;
        this.numSections = numSections;
        this.released = released;
        isTurnaroundQueued = false;
        this.isExiting = isExiting;
        movementMode = MovementMode.Scatter;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setMovementMode(MovementMode movementMode) {
        if(this.movementMode == MovementMode.Chase && movementMode != MovementMode.Chase){
            direction = DirectionHelp.getOppositeDirection(direction);
        }
        this.movementMode = movementMode;
    }

    protected void setTargetCol(int targetCol) {
        this.targetCol = targetCol;
    }

    protected void setTargetRow(int targetRow) {
        this.targetRow = targetRow;
    }

    protected abstract void setTargetPos(Player player, Blinky blinky);
    public boolean isReleased() {
        return released;
    }

    public void setNumSections(int numSections) {
        this.numSections = numSections;
    }

    public void queueTurnAround(){
        isTurnaroundQueued = true;
    }

    public final void update(Tile[][] board, Player player, Blinky blinky) {
        if(!isExiting && released) {
            if(movementMode == MovementMode.Chase){
                setTargetPos(player, blinky);
                updateDirection(board, targetRow, targetCol);
            }else if(movementMode == MovementMode.Scatter){
                if(this instanceof Blinky){
                    updateDirection(board, Global.GHOST_SCATTER_LOCATIONS[0][0], Global.GHOST_SCATTER_LOCATIONS[0][1]);
                }else if (this instanceof Pinky){
                    updateDirection(board, Global.GHOST_SCATTER_LOCATIONS[1][0], Global.GHOST_SCATTER_LOCATIONS[1][1]);
                }else if (this instanceof Inky){
                    updateDirection(board, Global.GHOST_SCATTER_LOCATIONS[2][0], Global.GHOST_SCATTER_LOCATIONS[2][1]);
                }else if(this instanceof Clyde){
                    updateDirection(board, Global.GHOST_SCATTER_LOCATIONS[3][0], Global.GHOST_SCATTER_LOCATIONS[3][1]);
                }
            }else if(movementMode == MovementMode.Frightened){

            }

        }else if(isExiting && !released){
            int[] offsets = DirectionHelp.getOffsets(direction);
            if(board[y][x+offsets[1]] != Tile.GHOST_AREA && board[y][x+offsets[1]] != Tile.GHOST_HOUSE_JUNCTION && board[y][x+offsets[1]] != Tile.GHOST_DOOR && board[y][x+offsets[1]] != Tile.STRAIGHT){
                direction = DirectionHelp.getOppositeDirection(direction);
            }
            if(section == numSections/2 && board[y][x] == Tile.GHOST_HOUSE_JUNCTION && DirectionHelp.isVertical(direction) && x != 14){
                if(this instanceof Inky){
                    direction = Direction.RIGHT;
                }else if(this instanceof Clyde){
                    direction = Direction.RIGHT;
                }
            } else if (section == 0 && x == 14 && y == 16) {
                direction = Direction.UP;
            }else if(section == numSections/2 && y == 19){
                direction = Direction.LEFT;
                isExiting = false;
                released = true;
                section = 0;
            }
        }else if(!isExiting){
            int[] offsets = DirectionHelp.getOffsets(direction);
            if(board[y][x+offsets[1]] != Tile.GHOST_AREA && board[y][x+offsets[1]] != Tile.GHOST_HOUSE_JUNCTION){
                direction = DirectionHelp.getOppositeDirection(direction);
            }
        }

        move();
    }

    protected void move() {
        if(isTurnaroundQueued){
            direction = DirectionHelp.getOppositeDirection(direction);
            isTurnaroundQueued = false;
        }
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

    private void updateDirection(Tile[][] board, int targetRow, int targetCol){
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

    private void updateRandomDirection(Tile[][] board){
        if (section == numSections / 2) {
            if (board[y][x] == Tile.TELEPORT) {
                switch (x) {
                    case 0 -> x = 27;
                    case 27 -> x = 0;
                }
            } else if (board[y][x] == Tile.JUNCTION || board[y][x] == Tile.SPECIAL_JUNCTION) {
                Direction[] directions = getValidDirections(board);
                int rand = (int)(Math.random() * directions.length);
                direction = directions[rand];
            } else if (tryGetTile(board) == null || tryGetTile(board) == Tile.WALL) {
                direction = getValidDirections(board)[0];
            }
        }
    }

    private void exitGhostHouse(){

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
            if (t == Tile.STRAIGHT) {
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
        if(!isExiting) {
            return switch (direction) {
                case DOWN, UP -> new float[]{Global.TILE_SIZE / 2f, (float) section / numSections * Global.TILE_SIZE};
                case LEFT, RIGHT -> new float[]{(float) section / numSections * Global.TILE_SIZE, Global.TILE_SIZE / 2f};
            };
        }else{
            return switch (direction) {
                case DOWN, UP -> new float[] {0, (float) section / numSections * Global.TILE_SIZE};
                case LEFT, RIGHT -> new float[] {(float) section / numSections * Global.TILE_SIZE, Global.TILE_SIZE / 2f};
            };
        }
    }

    public void release(){
        if(!released) {
            isExiting = true;
        }
    }
}
