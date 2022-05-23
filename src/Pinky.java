public class Pinky extends Ghost{

    public Pinky(int x, int y, Direction direction, int section, int numSections){
        super(x, y, direction, section, numSections);
    }

    public void update(Tile[][] board, Player player){
        int[] offsets = DirectionHelp.getOffsets(player.getDirection());
        int targetRow = player.getY() + offsets[0] * 2;
        int targetCol = player.getX() + offsets[1] * 2;
        updateDirection(board, targetRow, targetCol);
        move();
    }
}
