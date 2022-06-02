public enum Direction {
    UP,
    LEFT,
    DOWN,
    RIGHT;

    public static boolean oppositeDirections(Direction d1, Direction d2) {
        return d1 == getOppositeDirection(d2);
    }

    public int[] getOffsets() {//{yOffset, xOffset}
        switch (this) {
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

    public Direction getOppositeDirection(){
        switch (this){
            case UP -> {
                return Direction.DOWN;
            }
            case LEFT -> {
                return Direction.RIGHT;
            }
            case RIGHT -> {
                return Direction.LEFT;
            }
            default -> {
                return Direction.UP;
            }
        }
    }

    public static Direction getOppositeDirection(Direction d){
        switch (d){
            case UP -> {
                return Direction.DOWN;
            }
            case LEFT -> {
                return Direction.RIGHT;
            }
            case RIGHT -> {
                return Direction.LEFT;
            }
            default -> {
                return Direction.UP;
            }
        }
    }
    public boolean isVertical(){
        return this == Direction.UP || this == Direction.DOWN;
    }
    }