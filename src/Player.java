public class Player {

    private int x;
    private int y;
    private Direction direction;
    private Direction queuedDirection;
    private int section;

    public Player(){
        x = 0;
        y = 0;
        direction = Direction.LEFT;
        queuedDirection = Direction.LEFT;
        section = Global.TILE_SECTIONS/2;
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
    //</editor-fold>

    public void move(Tile[][] board){
        if(section == Global.TILE_SECTIONS/2){
            tryChangeDirection(board);

        }

    }

    private int[] getOffsets(Direction d){
        //[rowOffset][colOffset]
        switch(d){
            case DOWN -> {
                return new int[] {-1,0};
            }
            case LEFT -> {
                return new int[] {0,-1};
            }
            case RIGHT -> {
                return new int[] {0,1};
            }
            default -> {
                return new int[] {1,0};
            }
        }
    }

    private void tryChangeDirection(Tile[][] board){
        if (direction != queuedDirection) {
            Tile t;
            try {
                int[] offset = getOffsets(queuedDirection);
                t = board[y + offset[0]][x + offset[1]];
            } catch (ArrayIndexOutOfBoundsException e) {
                return;
            }
            if(t != Tile.WALL && t!= null){
                direction = queuedDirection;
            }
        }
    }
}
