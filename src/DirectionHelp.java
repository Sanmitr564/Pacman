public class DirectionHelp {
    public static boolean oppositeDirections(Direction d1, Direction d2) {
        if (
                ((d1 == Direction.LEFT && d2 == Direction.RIGHT) || (d2 == Direction.LEFT && d1 == Direction.RIGHT)) ||
                        ((d1 == Direction.UP && d2 == Direction.DOWN) || (d2 == Direction.UP && d1 == Direction.DOWN))
        ){
            return true;
        }
        return false;
    }
}
